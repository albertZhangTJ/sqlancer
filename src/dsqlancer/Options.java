package dsqlancer;


import java.util.List;
import java.util.ArrayList;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Options applicable to all DBMS")
public class Options {

    public static final Options DEFAULT_OPTIONS = new Options();

    @Parameter(names = { "--help", "-h" }, description = "Display help message", help = true)
    public boolean help = false;

    @Parameter(names = {"--parser_rule", "-p"}, description="Path to parser grammar file", variableArity=true)
    public List<String> parserRules = new ArrayList<>();

    @Parameter(names = {"--lexer_rule", "-l"}, description="Path to lexer grammar files", variableArity=true)
    public List<String> lexerRules = new ArrayList<>();

    @Parameter(names = {"--output", "-o"}, description="Path to output folder", arity=1)
    public String outputLocation = "./";
    
    @Parameter(names = {"--stages", "-s"}, description="Path to JSON file containing the execution stages", arity=1)
    public String stagesConfig = "";

    @Parameter(names = {"--weights", "-w"}, description="Path to a JSON file containing the weights for branches of the AST", arity=1)
    public String weightsConfig = "";
}
