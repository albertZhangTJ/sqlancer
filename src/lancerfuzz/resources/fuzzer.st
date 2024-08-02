//FUZZER
package sqlancer.any;
//SINCE THIS CODE IS AUTOMATICALLY GENERATED, IT IS HARD TO DO SOME RESETTING
//THUS EACH OBJECT OF THIS CLASS IS "ONE-TIME"
//IE EACH OBJECT CAN ONLY GENERATE ONE TEST CASE
//FOR GETTING MULTIPLE TEST CASES, NEW MULTIPLE INSTANCES OF THIS CLASS
import java.util.*;
import java.sql.*;
import sqlancer.SQLConnection;
@SuppressWarnings("unused")
public class <graph_name/>Fuzzer {
    private SQLConnection con;
    private List<String> expected_error_buffer;
    private List<String> expected_error_buffer_snapshot;

    private List<String> rules_call_series;
    private int rules_call_index;

    //depth limit, passed in by the tester at fuzzing time
    private int fuzzing_depth;
    private int loop_threshold;

    //Rule-wise identifier cache, must be resetted at the beginning of each rule call
    private List<HashMap<String, List<List<String>>>> identifier_cache_stack;
    private int identifier_cache_stack_pointer;
    private HashMap<String, List<List<String>>> identifier_cache;
    private HashMap<String, List<List<String>>> identifier_cache_snapshot;
    
    private HashMap<String, Integer> identifier_cache_cursor;
    private List<HashMap<String, Integer>> identifier_cache_cursor_stack;
    private int identifier_cache_cursor_stack_pointer;
    private HashMap<String, Integer> identifier_cache_cursor_snapshot;

    private List<HashMap<String, Integer>> rpid_stack;
    private int rpid_stack_pointer;
    private HashMap<String, Integer> rpid_cache;
    private HashMap<String, Integer> rpid_cache_snapshot;

    private List<HashMap<String, List<String>>> used_identifier_stack;
    private int used_identifier_stack_pointer;
    private HashMap<String, List<String>> used_identifier; 
    private HashMap<String, List<String>> used_identifier_snapshot;

    private static HashMap<String, String> static_var_ref;
    private HashMap<String, String> member_var_ref;
    private HashMap<String, String> var_ref;
    private List<HashMap<String, String>> var_ref_stack;

    //A flag that can be inserted into the output to indicate an error has occured
    //Usually due to an identifier has yet to be initialized
    //This pattern is extremely unlikely to appear in a normal output
    public static final String ERROR_FLAG = "PbEMuNhfsedBcVpUrBb2ZkWzpQkCkM5jTOCnUCTrYE9LgUpR2nZU45UgGHb3OS9NOrAUnWgrfvwUfGGmE0wdagXkOjv6psM4U9QnMWu75iFkgR5icHxm6xHtlpvQTy1OscMVNISBbaFLsNmYqzcHlpovwFtLJOlx0t7paPhOyTXQWuFjoGBgiQhWENvt116uaXQK9P0YO8ZkIVYOUcb1GzDh9G5Arns5guX8cqdTbDJdzWfvCalMevPYVXB7bAtwH8ZcyCNfabjt1ot8Wpj6xuCsn6u9WFuYao9enmV2tevt2DCqGlKfzA4ZZGuFev9M"; 

    private static HashMap<String, String> dbms_options = new HashMap<>();

    private String crash_log;


    public <graph_name/>Fuzzer(SQLConnection con, int fuzzing_depth, int loop_threshold){
        this.con = con;
        this.expected_error_buffer = new ArrayList<>();
        this.rules_call_series = new ArrayList<>();
        this.rules_call_index = 0;
        this.fuzzing_depth = fuzzing_depth;
        this.loop_threshold = loop_threshold;

        this.identifier_cache_stack = new ArrayList<>();
        this.identifier_cache_stack_pointer = 0;
        this.identifier_cache = new HashMap<>();

        this.identifier_cache_cursor = new HashMap<>();
        this.identifier_cache_cursor_stack = new ArrayList<>();
        this.identifier_cache_cursor_stack_pointer = 0;
        
        this.used_identifier_stack = new ArrayList<>();
        this.used_identifier_stack_pointer = 0;
        this.used_identifier = new HashMap<>();

        this.rpid_stack = new ArrayList<>();
        this.rpid_stack_pointer = 0;
        this.rpid_cache = new HashMap<>();

        this.member_var_ref = new HashMap<>();
        this.var_ref = new HashMap<>();
        this.var_ref_stack = new ArrayList<>();
    }

    public String get_crash_log(){
        return this.crash_log;
    }

    private void log(Object entry){
        this.crash_log = this.crash_log + entry.toString() +"\n";
    }

    public static void init(){
        static_var_ref = new HashMap<>();
<DBMS_OPTIONS/>
    }


    public String generate_rule(String rule_name) throws Exception{
        //this.log("Translating: "+rule_name);
<CALL_RULE_NAME/>
        this.log("ERROR: rule name "+rule_name+" has no corresponding function call generated");
        throw new Exception();
    }

    public String get_next_statement() throws Exception{
        if (this.rules_call_index<this.rules_call_series.size()){
            this.expected_error_buffer = new ArrayList<>();
            this.crash_log = "";
            try {
                String res = this.generate_rule(this.rules_call_series.get(this.rules_call_index));
                this.rules_call_index++;
                return res;
            }
            catch (Exception e){
                //this.log(crash_log);
                throw e;
            }
        }
        return ERROR_FLAG;
    }

    public String regenerate_last_statement() throws Exception{
        this.rules_call_index--;
        this.expected_error_buffer = new ArrayList<>();
        String res = this.generate_rule(this.rules_call_series.get(this.rules_call_index));
        this.rules_call_index++;
        return res;
    }

    public static String get_dbms_option(String key){
        return dbms_options.get(key);
    }

    private ResultSet query_dbms(String query) throws Exception{
        try {
            this.log("Queried DBMS with: "+query);
            ResultSet ans = this.con.createStatement().executeQuery(query);
            //this.log("Got ans: "+ans.toString());
            return ans;
        }
        catch (Exception e){
            this.log("Error encountered when executing query: "+query);
            throw e;
        }
    }

    public void generate(){
        int count = -1;
<STAGE/>
    }

    public List<String> get_expected_errors(){
        return this.expected_error_buffer;
    }


    private String get_parent_name(String sup){
        List<List<String>> id_val = this.identifier_cache.get(sup);
        if (id_val==null || id_val.size()==0){
            return null;
        }
        List<String> parent_hier = random_from_list(id_val);
        return parent_hier.get(parent_hier.size()-1);
    }

    private List<String> get_parent_hierarchy(String sup){
        List<List<String>> id_val = this.identifier_cache.get(sup);
        if (id_val==null || id_val.size()==0){
            return null;
        }
        return copy_list(random_from_list(id_val));
    }

    private int get_parent_hierarchy_cursor(String sup){
        return this.identifier_cache_cursor.get(sup);
    }
    
    private void set_parent_hierarchy_cursor(String sup, int new_val){
        this.identifier_cache_cursor.put(sup, new_val);
    }

    private List<String> get_next_parent_hierarchy(String sup){
        List<List<String>> id_val = this.identifier_cache.get(sup);
        if (id_val==null || id_val.size()==0){
            return null;
        }
        
        List<String> ans = copy_list(id_val.get(this.identifier_cache_cursor.get(sup)%id_val.size()));
        this.log("get_next_parent_hierarchy: sup="+sup+"  index="+this.identifier_cache_cursor.get(sup)+"  ans="+ans.toString());
        this.identifier_cache_cursor.put(sup, this.identifier_cache_cursor.get(sup)+1);
        this.log("Identifier index updated to: "+this.identifier_cache_cursor.get(sup));
        return ans;
    }

    private void set_parent(String sub, List<String> hierarchy){
        List<List<String>> id_val = this.identifier_cache.get(sub);
        if (id_val==null){
            List<List<String>> id_val_n = new ArrayList<>();
            id_val_n.add(hierarchy);
            this.identifier_cache.put(sub, id_val_n);
            this.identifier_cache_cursor.put(sub, 0);
        }
        else {
            id_val.add(hierarchy);
        }
    }

    private void push_identifier_cache(){
        this.identifier_cache = new HashMap<>();
        this.identifier_cache_stack.add(this.identifier_cache_stack_pointer, this.identifier_cache);
        this.identifier_cache_stack_pointer++;
        
        this.identifier_cache_cursor = new HashMap<>();
        this.identifier_cache_cursor_stack.add(this.identifier_cache_cursor_stack_pointer, this.identifier_cache_cursor);
        this.identifier_cache_cursor_stack_pointer++;
    }

    private void restore_identifier_cache(){
        this.identifier_cache = this.identifier_cache_stack.get(this.identifier_cache_stack_pointer);
        this.identifier_cache_stack_pointer++;

        this.identifier_cache_cursor = this.identifier_cache_cursor_stack.get(this.identifier_cache_cursor_stack_pointer);
        this.identifier_cache_cursor_stack_pointer++;
    }

    private void pop_identifier_cache() throws Exception{
        if (this.identifier_cache_stack.size()==0){
            this.log("ERROR: identifier stack underflow");
            throw new Exception("ERROR: identifier stack underflow");
        }
        this.identifier_cache_stack_pointer--;
        if (this.identifier_cache_stack_pointer==0){
            this.identifier_cache = new HashMap<>();
        }
        else {
            this.identifier_cache = this.identifier_cache_stack.get(this.identifier_cache_stack_pointer-1);
        }

        if (this.identifier_cache_cursor_stack.size()==0){
            this.log("ERROR: identifier cursor stack underflow");
        }
        this.identifier_cache_cursor_stack_pointer--;
        if (this.identifier_cache_cursor_stack_pointer==0){
            this.identifier_cache_cursor = new HashMap<>();
        }
        else {
            this.identifier_cache_cursor = this.identifier_cache_cursor_stack.get(this.identifier_cache_cursor_stack_pointer-1);
        }
    }

    private void push_rpid(){
        //once we try to push something, all the potentially restorable stack frames should be deemed invalid
        while(this.rpid_stack.size()>this.rpid_stack_pointer){
            this.rpid_stack.remove(this.rpid_stack.size()-1);
        }
        this.rpid_cache = new HashMap<>();
        this.rpid_stack.add(this.rpid_stack_pointer, this.rpid_cache);
        this.rpid_stack_pointer++;
    }

    //this is like push, but without invalidating all further stack frames
    //also the cache will be given a on-stack frame instead of a new frame
    private void restore_rpid(){
        this.rpid_cache = this.rpid_stack.get(this.rpid_stack_pointer);
        this.rpid_stack_pointer++;
    }

    private void pop_rpid() throws Exception{
        if (this.rpid_stack_pointer==0){
            this.log("ERROR: rpid stack underflow");
            throw new Exception();
        }
        this.rpid_stack_pointer--;
        if (this.rpid_stack_pointer==0){
            this.rpid_cache = new HashMap<>();
        }
        else {
            this.rpid_cache = this.rpid_stack.get(this.rpid_stack_pointer-1);
        }
    }

    private void push_used_identifier(){
        //once we try to push something, all the potentially restorable stack frames should be deemed invalid
        while(this.used_identifier_stack.size()>this.used_identifier_stack_pointer){
            this.used_identifier_stack.remove(this.used_identifier_stack.size()-1);
        }
        this.used_identifier = new HashMap<>();
        this.used_identifier_stack.add(this.used_identifier_stack_pointer, this.used_identifier);
        this.used_identifier_stack_pointer++;
    }

    //this is like push, but without invalidating all further stack frames
    //also the cache will be given a on-stack frame instead of a new frame
    private void restore_used_identifier(){
        this.used_identifier = this.used_identifier_stack.get(this.used_identifier_stack_pointer);
        this.used_identifier_stack_pointer++;
    }
    private void pop_used_identifier() throws Exception{
        if (this.used_identifier_stack_pointer==0){
            this.log("ERROR: used identifier stack underflow");
            throw new Exception();
        }
        this.used_identifier_stack_pointer--;
        if (this.used_identifier_stack_pointer==0){
            this.used_identifier = new HashMap<>();
        }
        else{
            this.used_identifier = this.used_identifier_stack.get(this.used_identifier_stack_pointer-1);
        }
    }

    //it is the callee's responsibility to create new context for itself at entry
    //and restore context for caller when exiting
    private void push_context(){
        this.push_identifier_cache();
        this.push_rpid();
        this.push_used_identifier();
    }
    private void restore_context(){
        //check if all stacks have something to restore
        //these stacks SHOULD have the same size, but just to be safe we will check every one
        if (this.identifier_cache_stack.size()>this.identifier_cache_stack_pointer &&
                this.identifier_cache_cursor_stack.size()>this.identifier_cache_cursor_stack_pointer &&
                this.rpid_stack.size()>this.rpid_stack_pointer &&
                this.used_identifier_stack.size()>this.used_identifier_stack_pointer){
            this.log("Restoring stack frame");
            this.restore_identifier_cache();
            this.restore_rpid();
            this.restore_used_identifier();
        }
    }
    private void pop_context() throws Exception{
        this.pop_used_identifier();
        this.pop_rpid();
        this.pop_identifier_cache();
    }





    private boolean is_identifier_available(String iid, String identifier){
        if (this.used_identifier.get(iid)==null || !this.used_identifier.get(iid).contains(identifier)){
            return true;
        }
        return false;
    }

    private void mark_identifier_used(String iid, String identifier){
        if (this.used_identifier.get(iid)==null){
            this.used_identifier.put(iid, new ArrayList<>());
        }
        this.used_identifier.get(iid).add(identifier);
    }

    private void snapshot_context(){
        // snapshot relationship identifiers
        this.identifier_cache_snapshot = new HashMap<>();
        for (HashMap.Entry<String, List<List<String>>> entry: this.identifier_cache.entrySet()){
            this.identifier_cache_snapshot.put(entry.getKey(), copy_list(entry.getValue()));
        }

        //snapshot the cursor for the identifiers above
        this.identifier_cache_cursor_snapshot = new HashMap<>();
        for (HashMap.Entry<String, Integer> entry: this.identifier_cache_cursor.entrySet()){
            this.identifier_cache_cursor_snapshot.put(entry.getKey(), entry.getValue());
        }

        // snapshot used identifiers
        this.used_identifier_snapshot = new HashMap<>();
        for (HashMap.Entry<String, List<String>> entry: this.used_identifier.entrySet()){
            this.used_identifier_snapshot.put(entry.getKey(), copy_list(entry.getValue()));
        }

        // snapshot rpid cache
        this.rpid_cache_snapshot = new HashMap<>();
        for (HashMap.Entry<String, Integer> entry: this.rpid_cache.entrySet()){
            this.rpid_cache_snapshot.put(entry.getKey(), entry.getValue());
        }

        // snapshot expected errors
        this.expected_error_buffer_snapshot = copy_list(this.expected_error_buffer);
    }

    private void rollback_context(){
        //this.log("Rolling back");
        this.identifier_cache = this.identifier_cache_snapshot;
        this.identifier_cache_cursor = this.identifier_cache_cursor_snapshot;
        this.used_identifier = this.used_identifier_snapshot;
        this.rpid_cache = this.rpid_cache_snapshot;
        this.expected_error_buffer = this.expected_error_buffer_snapshot;

        //for the contexts that resides on the context stacks
        //update the pointers on the context stacks as well
        this.identifier_cache_stack.set(this.identifier_cache_stack_pointer-1, this.identifier_cache_snapshot);
        this.identifier_cache_cursor_stack.set(this.identifier_cache_cursor_stack_pointer-1, this.identifier_cache_cursor_snapshot);
        this.rpid_stack.set(this.rpid_stack_pointer-1, this.rpid_cache_snapshot);
        this.used_identifier_stack.set(this.used_identifier_stack_pointer-1, this.used_identifier_snapshot);
        //this.log("Rolled back");
    }

    private String get_variable(String key){
        return this.var_ref.get(key);
    }

    private String get_member_variable(String key){
        return this.member_var_ref.get(key);
    }

    public static String get_static_variable(String key){
        return static_var_ref.get(key);
    }

    private void set_variable(String key, String value){
        this.var_ref.put(key, value);
    }

    private void set_member_variable(String key, String value){
        member_var_ref.put(key, value);
    }

    public static void set_static_variable(String key, String value){
        static_var_ref.put(key, value);
    }

    

    private int random(int min, int max){
        return (int)(Math.random()*(max-min))+min;
    }

    private int exp_decay_random(int min, int max){
        return this.exp_decay_random(min, max, 0.75);
    }

    private int exp_decay_random(int min, int max, double decay_rate){
        int i=min;
        while (i<max){
            if (Math.random()<decay_rate){
                return i;
            }
            i = i + 1;
        }
        return max-1;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> copy_list(List<T> ori){
        if (ori==null){
            return null;
        }
        List<T> res = new ArrayList<>();
        for (T item: ori){
            if (item instanceof List){
                res.add((T)copy_list((List<?>)item));
            }
            else{
                res.add(item);
            }
        }
        return res;
    }

    private <T> T random_from_list(List<T> l){
        return l.get(random(0, l.size()));
    }

    

//This works as a flag to indicate where to insert the method for regular rule nodes
//The flag here will be removed during post-processing
<RULE/>
}
