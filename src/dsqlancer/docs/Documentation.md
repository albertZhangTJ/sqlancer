# Documentation for DSQLancer

DSQLancer is a grammar-based fuzzing framework for SQLancer. 
It takes in a grammar file in ANTLR syntax, a configuration file and outputs a fuzzer than can be used by SQLancer to test DBMSs.

This project is still work in progress at relatively early stage.
Features described might still be un-implemented or buggy.

This work is inspired by [Grammarinator](https://github.com/renatahodovan/grammarinator) for AST generation and [StringTemplate](https://github.com/antlr/stringtemplate4) for fuzzer rendering.


## Quick Start

TODO



## Grammar File

DSQLancer takes in a grammar file in ANTLR syntax.
For more details about ANTLR, see [ANTLRv4 Documentation](https://github.com/antlr/antlr4/blob/master/doc/index.md).
For list of ANTLR grammar files for different SQL dialects, see [ANTLRv4 GitHub repo](https://github.com/antlr/grammars-v4/tree/master/sql).

In order to improve semantic validity of the generated test cases, DSQLancer needs more than mere plain grammar files.

The extra semantic information shall be provided in [ANTLR Action](https://github.com/antlr/antlr4/blob/master/doc/actions.md). Details described below.


### Schema References

In order to generate SQL statements with correct identifiers (e.g. column names), DSQLancer needs to know how to query the target DBMS for list of available identifiers, as well as other information such as relationship between identifiers.

Schema references shall be declared as parser rules, with a special list of parameters.
A rule declared for schema references must have the parameter `boolean is_schema` set to `true`. Parameter `String query` must be set to a SQL query that allows DSQLancer to query the target DBMS for list of available identifiers. A related parameter `String attribute_name` must also be provided for DSQLancer to get the correct column out of the `ResultSet` returned by the execution of the previous statement.

When the current schema reference belongs to some parent reference (e.g. a column belongs to a table), the name of the parent reference can be accessed using `$parent_name$` in the `query` parameter.

Below is an example of declaring table name references for SQLite.

<pre><code>
table_name locals <strong>[boolean is_schema=true, 
    String query="
        SELECT name, type as category, sql
        FROM sqlite_master 
        UNION
        SELECT name, 'temp_table' as category, sql 
        FROM sqlite_temp_master
        WHERE type='table'
        UNION
        SELECT name, 'view' as category, sql
        FROM sqlite_temp_master
        WHERE type='view' GROUP BY name;",
    String attribute_name="name"]</strong> : K_STUB;
</code></pre>

When such a schema reference rule is referred to in other rules, several parameters needs to be provided. `boolean is_new` must be specified to indicate whether the identifier shall be generated (e.g. table name for create table statement) or queried from target DBMS (e.g. table name for insert statement). Another two parameters `String sup` and `String sub` are used to specify the relationship between different identifiers within the same rule. `sup` is used to declare the current reference as parent of other references. `sub` is used to declare the current reference as child of other references. For all references to schema rules, these two parameters needs to be explicitly set to null when they are not used.

Do notice that multiple parent SQL identifiers can be mapped to a single grammar identifier (declared using `sub`). When this happens, the parent will be randomly evaluated to one of the mapped SQL identifiers at runtime.

Below is an example of referring to schema reference rules.

<pre><code>
insert_stmt : with_clause?  
    ( K_INSERT 
                | K_REPLACE
                | K_INSERT K_OR K_IGNORE ) 
    K_INTO
    table_name<strong>[boolean is_new=false, 
            String sup=null, 
            String sub="t"]</strong>
    ( '(' 
        column_name<strong>[boolean is_new=false, 
                    String sup="t", 
                    String sub=null] </strong>
        ( ',' 
        column_name<strong>[boolean is_new=false, 
                    String sup="t", 
                    String sub=null] </strong>
        )* 
    ')' )?
    ( K_VALUES '(' expr ( ',' expr )* ')' 
        ( ',' '(' expr ( ',' expr )* ')' )* 
        | K_DEFAULT K_VALUES 
    );
</code></pre>

In this example `"t"` is to specify `table_name` as parent of `column_name`. 

### Branch Weights

Some parts of the AST might need to be generated more often than others. To specify this kind of weighted branching, the tester can use a reserved function in ANTLR Action `BRANCH_W(double)`. If a branch has no weight specified, the weight will be set to default value 1.

At runtime, the possibility of going down a branch is $$\frac{current\_ weight}{total\_ weight}$$. Therefore, the larger the weight is, the higher the possibility that the branch is walked.

Below is an example of specifying branch weights.

<pre><code>
insert_stmt : with_clause?  
    ( K_INSERT <strong>{ BRANCH_W(10); }</strong>
                | K_REPLACE <strong>{ BRANCH_W(0.5); }</strong>
                | K_INSERT K_OR K_IGNORE ) 
    K_INTO
    table_name[boolean is_new=false, 
            String sup=null, 
            String sub="t"]
    ( '(' 
        column_name[boolean is_new=false, 
                    String sup="t", 
                    String sub=null] 
        ( ',' 
        column_name[boolean is_new=false, 
                    String sup="t", 
                    String sub=null] 
        )* 
    ')' )?
    ( K_VALUES '(' expr ( ',' expr )* ')' 
        ( ',' '(' expr ( ',' expr )* ')' )* 
        | K_DEFAULT K_VALUES 
    );
</code></pre>

### Expected Errors

Despite our effort to reduce invalid test cases and improve the semantic validity of generated statements, DSQLancer is far from semantically secure. 

In order to separate error messages that are known to be triggered by invalid test cases from error messages that potentially indicate bugs, the tester needs to specify a list of error messages fragments that are known to be triggered by invalid test cases. Any error messages that contains fragments in this list will be ignored.

Below is an example of specifying expected error.

<pre><code>
insert_stmt : with_clause?  
    <strong>{ E_ERR("Unique constraint violation"); }</strong>
    ( K_INSERT 
                | K_REPLACE
                | K_INSERT K_OR K_IGNORE ) 
    K_INTO
    table_name[boolean is_new=false, 
            String sup=null, 
            String sub="t"]
    ( '(' 
        column_name[boolean is_new=false, 
                    String sup="t", 
                    String sub=null] 
        ( ',' 
        column_name[boolean is_new=false, 
                    String sup="t", 
                    String sub=null] 
        )* 
    ')' )?
    ( K_VALUES '(' expr ( ',' expr )* ')' 
        ( ',' '(' expr ( ',' expr )* ')' )* 
        | K_DEFAULT K_VALUES 
    );
</code></pre>

### Quantifier Nodes

TODO



## Configuration File

An extra configuration file is needed for DSQLancer to acquire knowledge about other non-syntactical information such as DBMS default port or order in which statements shall be executed.

The file shall be in JSON format.

### DBMS-Specific Options

Six optional parameters, `host`, `port`, `db_prefix`, `username`, `password`, and `conn_str` can be set in this JSON file.
The user can also provide any additional parameters for use with their own ANTLR Actions. 
These parameters will be stored as key-value pairs of Strings and can be accessed by calling `get_dbms_option(String key)`.

Alternatively, the user can also provide these parameters at fuzzer runtime as CLI parameters.

- `host` should be a string to the location the target DBMS, default value is `"localhost"`
- `port` should be string representation of an integer, indicating which port the target DBMS is running on
- `db_prefix` is the prefix used in JDBC connection string (e.g. `"mysql"` in `"jdbc:mysql://localhost:3306"`)
- `conn_str` is the JDBC connection string. The user can use tags in the format of `($param_name$)` (e.g. `($username$)` for `username`) in the connection string. These tags will be match-and-replaced with real parameter values at fuzzer runtime

An example of the configuration file can be found below.

### Testing Stages

SQL statements cannot be generated in random orders (e.g. you have to create a table before inserting into it).
The tester will have to specify the order in which different rules are fuzzed.

This information shall be specified as a JSON array.
Each entry in the array shall contain the following attributes.

- `name` is the name of the stage. This is simply for bookkeeping purposes and can be set to anything at the tester's will
- `rules` is a JSON array of strings containing the name of rules to be generated in this stage. Each of these rule names must be defined as a parser rule in the grammar file provided to DSQLancer
- `min` is an integer representing the minimum number of statements need to be generated in this stage 
- `max` is an integer representing the maximum number of statements need to be generated in this stage 

Below is an example of configuration file.

<pre><code>
{   
    "options" : [
        {
            "name" : "host", 
            "default" : "localhost"
        },
        {
            "name" : "port", 
            "default" : "3306"
        },
        {
            "name" : "username", 
            "default" : "Username"
        },
        {
            "name" : "password", 
            "default" : "Password"
        },
        {
            "name" : "db_prefix", 
            "default" : "mysql"
        },
        {
            "name" : "conn_str", 
            "default" : "jdbc:($db_prefix$)://($host$):($port$)"
        }
    ],
    "stages" : [
    	{
            "name" : "create",
            "rules" : [
                "create_table_stmt"
            ],
            "min" : 1,
            "max" : 3
        },
   		{
            "name" : "insert",
            "rules" : [
                "insert_stmt"
            ],
            "min" : 5,
            "max" : 7
        },
        {
            "name" : "update",
            "rules" : [
                "update_stmt"
            ],
            "min" : 1,
            "max" : 5
        },
        {
            "name" : "select",
            "rules" : [
                "select_core"
            ],
            "min" : 1,
            "max" : 5
        }
    ]
}
</code></pre>