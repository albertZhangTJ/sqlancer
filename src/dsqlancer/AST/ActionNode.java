package dsqlancer.AST;

public class ActionNode extends Node{
    private String src;

    public ActionNode(String src){
        this.src = src;
    }

    public String get_src(){
        return this.src;
    }
    
}