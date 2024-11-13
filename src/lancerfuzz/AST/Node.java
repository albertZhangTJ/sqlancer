package lancerfuzz.AST;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import lancerfuzz.Utils;

@SuppressWarnings("unused")
public class Node {
    private static int nodes_count = 0;

    private int id;
    private String identifier;
    private List<Edge> outward_edges;
    private List<String> expected_errors = new ArrayList<>();
    public boolean walked; //for debugging purpose only
    public boolean is_rendered=false; //eaiser for the fuzzer renderer to follow
    public static List<String> rules;


    protected int min_depth=-1;

    public Node(){
        this.id = nodes_count;
        nodes_count++;
        this.outward_edges = new ArrayList<>();
        this.identifier = null;
        this.walked=false;
    }

    public Node(String identifier){
        this.id = nodes_count;
        nodes_count++;
        this.outward_edges = new ArrayList<>();
        this.identifier = identifier;
        this.walked=false;
    }

    public Node(String name, String label){
        this.id = nodes_count;
        nodes_count++;
        this.outward_edges = new ArrayList<>();
        if (label==null){
            this.identifier = name;
        }
        else {
            this.identifier = name + "_" + label;
        }
        this.walked=false;
    }

    public int get_id(){
        return this.id;
    }

    public static void init(){
        rules = new ArrayList<>();

        //built-in functions 
        //built-in attributes of variables are not handled here as they are not standalone
        register_rule("_e"); //built-in function for expected error
        register_rule("_r"); //built-in function for random 
        register_rule("query"); //built-in function for querying target DBMS
    }

    public static void register_rule(String name){
        if (rules.contains(name)){
            Utils.panic("Node::register_rule : redefinition of rule "+name);
        }
        rules.add(name);
    }

    public static boolean is_rule(String id){
        return rules.contains(id);
    }

    public void add_outward_edge(Edge edge){
        if (this.outward_edges.contains(edge)){
            //Utils.oops("Node::add_outward_edge : Edge "+edge.toString()+" already exists for node "+this.toString());
        }
        this.outward_edges.add(edge);
    }

    public List<Edge> get_outward_edges(){
        return Utils.copy_list(this.outward_edges);
    }

    public static int total_nodes(){
        return nodes_count;
    }

    public String get_identifier(){
        return this.identifier;
    }

    public void change_parent_to(Node new_parent){

    }

    public String toString(){
        return "\n[NODE]\n"+
                "    id: "+this.id+"\n"+
                "    identifier: "+this.identifier+"\n"+
                "    Expected_errors: "+this.expected_errors.toString();
    }

    public void add_expected_errors(String content){
        if (content!=null && content.length()>0){
            this.expected_errors.add(content);
        }
    }

    public List<String> get_expected_errors(){
        return Utils.copy_list(this.expected_errors);
    }

    public void set_min_depth(int depth){
        this.min_depth = depth;
    }

    public int get_min_depth(){
        return this.min_depth;
    }

    //This is just a placeholder for render function
    //Should not be executed in real life
    //Did not set to abstract to save some work for temporary IR nodes
    public String render(){
        Utils.panic("Node::render : Internal error, you should never have got here");
        return null;
    }
}
