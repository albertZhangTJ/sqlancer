grammar MiniSQLite;

create_table_stmt
 : K_CREATE ( K_TEMP | K_TEMPORARY )? K_TABLE ( K_IF K_NOT K_EXISTS )?
  	table_name[boolean is_new=true, String sup=null, String sub=null, String iid=null]
   ' (' column_name[boolean is_new=true, String sup=null, String sub=null, String iid=null] ( ', ' column_name[boolean is_new=true, String sup=null, String sub=null, String iid=null] { RP_LIMIT(5,8); } )*  ');'
   
 ; 

update_stmt
 : K_UPDATE { E_ERR("[SQLITE_CONSTRAINT]"); } 
   ( K_OR K_ROLLBACK
     | K_OR K_ABORT 
     | K_OR K_FAIL { BRANCH_W(0.1); }
     | K_OR K_IGNORE 
    )? table_name[boolean is_new=false, String sup=null, String sub="t", String iid=null]
   K_SET column_name[boolean is_new=false, String sup="t", String sub=null, String iid="s"] '=' expr 
   ( ',' column_name[boolean is_new=false, String sup="t", String sub=null, String iid="id"] '=' expr )? 
   ( K_WHERE expr )? ';'
 ;
 
insert_stmt :
	{ E_ERR("Type mismatch");}
	( K_INSERT { BRANCH_W(10); }
      | K_REPLACE { BRANCH_W(5); }
      | K_INSERT K_OR K_IGNORE 
	)
	K_INTO
	table_name[boolean is_new=false, String sup=null, String sub="t", String iid=null]
	'(' 
		column_name[boolean is_new=false, String sup="t", String sub=null, String iid="id"] 
		( ',' column_name[boolean is_new=false, String sup="t", String sub=null, String iid="id"] {RP_LIMIT(2, 4); RP_ID("s");} )* 
	')'
	K_VALUES '(' expr ( ',' expr {RP_LIMIT(2, 4); RP_ID("s");} )* ');'  
	;
	

table_name locals [boolean is_schema=true, String query="SELECT name FROM sqlite_master UNION SELECT name FROM sqlite_temp_master WHERE type='table' UNION SELECT name FROM sqlite_temp_master WHERE type='view' GROUP BY name;", String attribute_name="name"] : expr ;
    
column_name locals [boolean is_schema=true, String query="SELECT name FROM pragma_table_info('$parent_name$');", String attribute_name="name"] : expr ;
	

expr : ( DIGIT { RP_LIMIT(1, 6, false, 0.3); } )+;


K_ABORT : SPACE A B O R T SPACE;
K_FAIL : SPACE F A I L SPACE;
K_IGNORE : SPACE I G N O R E SPACE;
K_OR : SPACE O R SPACE;
K_ROLLBACK : SPACE R O L L B A C K SPACE;
K_SET : SPACE S E T SPACE;
K_UPDATE : SPACE U P D A T E SPACE;
K_WHERE : SPACE W H E R E SPACE;
K_DEFAULT : SPACE D E F A U L T SPACE;
K_VALUES : SPACE V A L U E S SPACE;
K_INSERT : SPACE I N S E R T SPACE;
K_REPLACE : SPACE R E P L A C E SPACE;
K_INTO : SPACE I N T O SPACE;
K_CREATE : SPACE C R E A T E SPACE;
K_TABLE : SPACE T A B L E SPACE;
K_TEMP : SPACE T E M P SPACE;
K_TEMPORARY : SPACE T E M P O R A R Y SPACE;
K_IF : SPACE I F SPACE;
K_NOT : SPACE N O T SPACE;
K_EXISTS : SPACE E X I S T S SPACE;
K_SELECT : SPACE S E L E C T SPACE;
K_DISTINCT : SPACE D I S T I N C T SPACE;
K_ALL : SPACE A L L SPACE;
K_FROM : SPACE F R O M SPACE;





fragment DIGIT : [0-9];
fragment SPACE : [\u0020];
fragment NL : [\n];
fragment A : [A];
fragment B : [B];
fragment C : [C];
fragment D : [D];
fragment E : [E];
fragment F : [F];
fragment G : [G];
fragment H : [H];
fragment I : [I];
fragment J : [J];
fragment K : [K];
fragment L : [L];
fragment M : [M];
fragment N : [N];
fragment O : [O];
fragment P : [P];
fragment Q : [Q];
fragment R : [R];
fragment S : [S];
fragment T : [T];
fragment U : [U];
fragment V : [V];
fragment W : [W];
fragment X : [X];
fragment Y : [Y];
fragment Z : [Z];
