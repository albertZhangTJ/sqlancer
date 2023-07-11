package dsqlancer.AST;

import java.util.List;

import dsqlancer.Utils;

public class AlternationNode extends Node {
    private int rule_id;
    private int index;
    private List<String> conditions;
    private int min_depth;

    public AlternationNode(int rule_id, int index, List<String> conditions){
        this.rule_id = rule_id;
        this.index = index;
        this.conditions = conditions;
        this.min_depth = -1;
    }

    public int get_rule_id(){
        return this.rule_id;
    }

    public int get_index(){
        return this.index;
    }

    public List<String> get_conditions(){
        return Utils.copy_list(this.conditions);
    }

    public int get_min_depth(){
        return this.min_depth;
    }
    // TODO: translate simple_alternatives 
    // 
}
