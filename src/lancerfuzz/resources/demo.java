import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.facebook.presto.jdbc.internal.okio.Buffer;

import java.lang.IllegalArgumentException;
import Math.random;
import lancerfuzz.resources.Rig.Context;
import lancerfuzz.resources.demo.DeadEndException;
import sqlancer.SQLConnection;
public class demo{
    public static class UnavailableException extends Exception {
        public boolean isUndefined; //the variable/attr requested does not exist
        public boolean isUninitialized; //the variable/attr requests exists but has no value associated
        public boolean isOut; //the variable/attr requested exists but is insufficient in numbers (no more unique ones exists)
        public UnavailableException(String message, boolean isUndefined, boolean isUninitialized, boolean isOut){
            super(message);
            this.isUndefined = isUndefined;
            this.isUninitialized = isUninitialized;
            this.isOut = isOut;
        }
    }

    public static class DeadEndException extends Exception {
        public DeadEndException(String message){
            super(message);
        }
    }

    public static class Buffer {
        private String content; //only for terminals
        //only for non-terminals, or temporary leaf-nodes whose children are not yet built
        private List<Buffer> children; 

        public Buffer(){
            this.content = null;
            this.children = new ArrayList<>();
        }

        public Buffer(String content){
            if (content==null){
                throw new IllegalArgumentException("ERROR : Buffer::Buffer : Terminal buffer nodes should not have null content. For a placeholder, use empty string \"\" instead");
            }
            this.content = content;
        }

        public void add(Buffer child){
            this.children.add(child);
        }

        public void add(Variable terminal){
            this.children.add(new Buffer(terminal.getValue()));
        }

        public void set(int index, Buffer child){
            this.children.set(index, child);
        }

        public String toString(){
            if (this.content!=null){
                return this.content;
            }
            String res = "";
            for (Buffer child : this.children){
                res = res + child.toString();
            }
            return res;
        }

    }

    public static class Context {
        private HashMap<String, Variable> globalSymbols;
        private HashMap<String, Variable> symbols;
        private List<HashMap<String, Variable>> symbolStack;
        private List<Variable> args;
        private List<String> errors;
        private Variable result;
        private SQLConnection conn;
        public static final List<String> OPERATORS = Collections.unmodifiableList(Arrays.asList("=", "+=", "+", "-", "==", "!=", ">", "<", ">=", "<="));
        private HashMap<String, Integer> idCount;

        public Context(SQLConnection conn){
            this.symbols = new HashMap<>();
            this.symbolStack = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.conn = conn;
            this.idCount = new HashMap<>();
        }

        public void addError(Variable v) throws IllegalArgumentException{
            try {
                this.errors.add(v.getValue());
            }
            catch (IllegalArgumentException e){
                throw new java.lang.IllegalArgumentException("ERROR: Fuzzer.Context.addError :: variable passed to expected error declaration is not single-valued, check your expected error declarations", e)
            }
        }

        public void push_args(List<Variable> args){
            this.args = args;
        }

        public void enter(List<String> arg_symbols, List<Variable> defaults) throws Exception{
            HashMap<String, Variable> newFrame = new HashMap<>();
            if (arg_symbols.size()!=defaults.size()){
                throw new IllegalArgumentException("ERROR: Fuzzer.Context.call :: internal error, argument list size does not match that of default list");
            }
            for (int i=0; i<arg_symbols.size(); i++){
                String symbol = arg_symbols.get(i);
                if (this.get_var(symbol)==null){
                    throw new UnavailableException("ERROR: Fuzzer.Context.call :: cannot find symbol "+symbol, true, false, false);
                }
                //TODO: complete the logic of selecting between pushed args and default, throw error if both unavailable
                newFrame.add(this.get_var(symbol));
            }

            //preserve current symbols
            this.symbolStack.add(this.symbols);
            this.symbols = newFrame;

            //set the return value to null to avoid confusion
            this.result = null;
        }


        public void ret(String returnSymbol){
            //move the return value into the cache slot
            if (returnSymbol!=null){
                this.result = this.getSymbol(returnSymbol);
            }

            //restore context for caller
            this.symbols = this.symbolStack.get(this.symbolStack.size()-1);
            this.symbolStack.remove(this.symbolStack.size()-1);
        }


        // the symbol here might be either a variable or a function
        // that is why the args arg this there, it won't even be looked at if it is actually a variable
        public Variable getSymbol(String symbol, List<Variable> args) throws IllegalArgumentException{
            if (symbol==null){
                throw new IllegalArgumentException("ERROR : Fuzzer.Context.getSymbol :: the symbol accessed is null, check your grammar");
            }
            if (symbol.equals("query")){
                return this.query(args);
            }
            if (symbol.equals("_r")){
                return this.random(args);
            }
            if (symbol.equals("_e")){
                return this.addExpectedError(args);
            }
            if (symbol.equals)
            if (this.symbols.get(symbol)==null && this.globalSymbols.get(symbol)==null){
                throw new IllegalArgumentException("ERROR : Fuzzer.Context.getSymbol :: the symbol \"" + symbol + "\" accessed does not exist or has not yet been initialized\n"+
                                                    "If you are using customized expansion order, please check your order specification\n"+
                                                    "If not, please make sure the symbol is there and consider specify the expansion ordering since the auto-scheduler is only best-effort");
            }
            return this.symbols.get(symbol)==null ? this.globalSymbols.get(symbol) : this.symbols.get(symbol);
        }

        //random generator 
        // handles the following cases
        //_r[fix_number, delimiter]
        //_r[min, delimiter, decay_spec]
        //_r[min, max, delimiter, decay_spec]
        // decay_spec should be an interger ranging 0 to 4, default 2
        // 0 for uniform distribution, uniform distribution without a max is not allowed
        // 1 for 0.25^x, 2 for 0.5^x, 3 for 0.75^x, 4 for 0.99^x
        public Variable random(List<Variable> args) throws IllegalArgumentException{
            try{
                if (args.size()==2){
                    int val = args.get(0).getNumerical();
                    String delimiter = args.get(1).getValue();
                    Variable res = Variable.factory(val);
                    res.setAttr("delimiter", Variable.factory(delimiter));
                    return res;
                }
                if (args.size()==3){
                    int min = args.get(0).getNumerical();
                    String delimiter = args.get(1).getValue();
                    int ds = args.get(2).getNumerical();
                    if (ds==0){
                        throw new IllegalArgumentException("ERROR : Fuzzer.Context.random :: random function cannot be called with no max AND uniform distribution");
                    }
                    if (ds>4 || ds<0){
                        throw new IllegalArgumentException("ERROR : Fuzzer.Context.random :: unrecognizable decay_spec, accepted value are integers in range [0,4]");
                    }
                    int val = min;
                    double dr = ds==1 ? 0.25 : ds==2 ? 0.5 : ds==3 ? 0.75 : 0.99;
                    while (true){
                        if (Math.random()<dr){
                            break;
                        }
                        val++;
                    }
                    Variable res = Variable.factory(val);
                    res.setAttr("delimiter", Variable.factory(delimiter));
                    return res;
                }
                if (args.size()==4){
                    int min = args.get(0).getNumerical();
                    int max = args.get(1).getNumerical();
                    String delimiter = args.get(2).getValue();
                    int ds = args.get(3).getNumerical();
                    if (ds>4 || ds<0){
                        throw new IllegalArgumentException("ERROR : Fuzzer.Context.random :: unrecognizable decay_spec, accepted value are integers in range [0,4]");
                    }
                    int val = min;
                    if (ds==0){
                        val += (int)((max-min+1)*Math.random());
                    }
                    else {
                        double dr = ds==1 ? 0.25 : ds==2 ? 0.5 : ds==3 ? 0.75 : 0.99;
                        while (true){
                            if (Math.random()<dr){
                                break;
                            }
                            val++;
                            //wrap around when overflow
                            if (val>max){
                                val = min;
                            }
                        }
                    }
                    Variable res = Variable.factory(val);
                    res.setAttr("delimiter", Variable.factory(delimiter));
                    return res;
                }
                throw new IllegalArgumentException("ERROR : Fuzzer.Context.random :: Expecting 2, 3, or 4 arguments, got "+args.size());
                return null;
            }
            catch (IllegalArgumentException e){
                throw new IllegalArgumentException("ERROR : Fuzzer.Context.random :: arguments passed are not recognizable, the recognized formats are\n"+
                                                    "_r[fix_number, delimiter]\n" + 
                                                    "_r[min, delimiter, decay_spec]\n" + 
                                                    "_r[min, max, delimiter, decay_spec]\n" + 
                                                    "decay_spec should be an interger ranging 0 to 4, default 2\n" + 
                                                    "0 for uniform distribution, uniform distribution without a max is not allowed\n" + 
                                                    "1 for 0.25^x, 2 for 0.5^x, 3 for 0.75^x, 4 for 0.99^x", e);
            }
        }

        //returning a variable object containing an empty string since
        //all calls to a function is expected to return something
        //so we are using this as a placeholder
        public Variable addExpectedError(List<Variable> args){
            for (Variable arg : args){
                this.errors.add(arg.getValue());
            }
        }

        //
        public Variable eval(Variable a, String operator, Variable b) throws IllegalArgumentException{
            if (operator==null){
                throw new IllegalArgumentException("ERROR : Variable.eval :: operator cannot be null");
            }
            //this is for the author's own use
            //if an operator is illegal, it shouldn't be recognized by the parser in the first place
            //this almost for sure means there is some kinda implementation error
            if (!OPERATORS.contains(operator)){
                throw new IllegalArgumentException("ERROR : Context.eval :: "+operator+" is not a recognized operator. Internal error, you shouldn't have reached here.");
            }
            if (operator.equals("=")){
                a.clone(b);
                return a;
            }
            if (operator.equals("+=")){
                a.addEntry(b);
                return a;
            }
            if (operator.equals("+")){
                if (a.isNumerical() && b.isNumerical()){
                    return Variable.factory(a.getNumerical() + b.getNumerical());
                }
                return Variable.factory(a.getValue()+b.getValue());
            }
            if (operator.equals("-")){
                if (a.isNumerical() && b.isNumerical()){
                    return Variable.factory(a.getNumerical() - b.getNumerical());
                }
                throw new IllegalArgumentException("ERROR : Context.eval :: the operator \"-\" is not applicable when either side of the operation is not numerical");
            }
            return Variable.factory(a.compare(operator, b));
        }


        public void setSymbol(String symbol, Variable v){
            if (symbol==null || symbol.size()==0 || !((symbol.charAt(0)>=65 && symbol.charAt(0)<=90) || (symbol.charAt(0)>=97 && symbol.charAt(0)<=122))){
                throw new IllegalArgumentException("ERROR : Fuzzer.Context.setSymbol :: symbol must be non-empty and start with an ASCII letter");
            }
            // starts with a capital letter
            if (symbol.charAt(0)<92){
                this.globalSymbols.put(symbol, v);
            }
            else {
                this.symbols.put(symbol, v);
            }
        }
        //requires 2 or more arguments
        //the 
        public Variable query(List<Variable> args){
            if (args.size()<2){
                throw new IllegalArgumentException("ERROR : Fuzzer.Context.query :: a call to the query function must contain at least 2 arguments: query and column name");
            }
            String query = args.get(0).getValue();
            String col = args.get(1).getValue();
            Variable v = Variable.of();
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                Variable r = Variable.of(rs.getString(col));
                for (int i=2; i<args.size(); i++){
                    Variable pair = args.get(i);
                    String attrCol = pair.getEntry(0);
                    String attr = pair.getEntry(1);
                    r.setAttr(attr, Variable.of(rs.getString(attrCol)));
                }
                v.addEntry(r);
            }
            return v;
        }
        //for the "new" built-in function 
        public Variable new_id(List<Variable> args){
            if (args.size()!=1){
                throw new IllegalArgumentException("ERROR : Fuzzer.Context.new_id :: a call to the new function expects exactly 1 argument: the prefix of the id to be generated");
            }
            String prefix = args.get(0).getValue();
            if (this.idCount.containsKey(prefix)){
                this.idCount.put(prefix, this.idCount.get(prefix)+1);
            }
            else {
                this.idCount.put(prefix, 1);
            }
            return new Variable(prefix+this.idCount.get(prefix));
        }
    }

    public static class Variable {
        public boolean isSingleValued;
        private String value;
        private int numerical;
        private boolean bool;
        private boolean containsNumerical;
        private boolean containsBoolean;
        private List<Variable> entries;
        private List<Integer> uniqueUsageCount;
        private HashMap<String, Variable> attributes;
        private int cursor; //non-decreasing, modulus entries.size() will be used for extracting index 

        public static final List<String> RESERVED_ATTR = Collections.unmodifiableList(Arrays.asList("new", "any", "next", "len", "unique_any", "query", "filter", "cur"));
        
        public Variable(){
            this.isSingleValued = false;
            this.containsNumerical = false;
            this.containsBoolean = false;
            this.entries = new ArrayList<>();
            this.uniqueUsageCount = new ArrayList<>();
            this.attributes = new HashMap<>();
            this.cursor = 0;
        }
        public Variable(String value){
            this.values = value;
            this.isSingleValued = true;
            this.containsNumerical = false;
            this.containsBoolean = false;
            this.entries = new ArrayList<>();
            this.uniqueUsageCount = new ArrayList<>();
            this.attributes = new HashMap<>();
            this.cursor = 0;
        }
        public Variable(int numerical){
            this.value = ""+numerical;
            this.numerical = numerical;
            this.isSingleValued = true;
            this.containsNumerical = true;
            this.containsBoolean = false;
            this.entries = new ArrayList<>();
            this.uniqueUsageCount = new ArrayList<>();
            this.attributes = new HashMap<>();
            this.cursor = 0;
        }
        public Variable(boolean bool){
            this.value = ""+bool;
            this.bool = bool;
            this.isSingleValued = true;
            this.containsNumerical = false;
            this.containsBoolean = true;
            this.entries = new ArrayList<>();
            this.uniqueUsageCount = new ArrayList<>();
            this.attributes = new HashMap<>();
            this.cursor = 0;
        }
        //by returning this at the end of the setter, we can use this class in an FP way
        public Variable setAttr(String attr_name, Variable value) throws IllegalArgumentException{
            if (RESERVED_ATTR.contains(attr_name)){
                throw new IllegalArgumentException("ERROR: "+attr_name+" is a reserved attribute name, please use another name instead");
            }
            this.attributes.put(attr_name, value);
            return this;
        }

        public static Variable factory(){
            return new Variable();
        }

        public static Variable factory(String value){
            return new Variable(value);
        }
        public static Variable factory(int numerical){
            return new Variable(numerical);
        }
        public static Variable factory(boolean bool){
            return new Variable(bool);
        }
        
        //make the current variable a shallow-copy of other
        //the cursor will be set to 0
        //mainly used for assignment (i.e. a=b will be context.getSymbol("a").clone(context.getSymbol("b")))
        public void clone(Variable other){
            this.isSingleValued = other.isSingleValued;
            this.value = other.value;
            this.numerical = other.numerical;
            this.bool = other.bool;
            this.containsNumerical = other.containsNumerical;
            this.containsBoolean = other.containsBoolean;
            this.entries = copy_list(other.entries);
            this.uniqueUsageCount = ArrayList<>();
            for (Variable v: this.entries){
                this.uniqueUsageCount.add(0);
            }
            this.attributes = copy_map(other.attributes);
            this.cursor = 0;
        }
        
        // new, query, getColumn, withColumnAsAttr will not be handled as those are not attributes but functions
        // those will be implemented in the Context class
        public Variable getAttr(String name, List<Variable> args) throws Exception{
            if (name.equals("any")){
                if (this.isSingleValued){
                    throw new IllegalArgumentException("Fuzzer.Variable.getAttr :: attribute any is not applicable to single-valued variables");
                }
                if (this.entries.size()==0){
                    throw new UnavailableException("", false, true, false);
                }
                return random_from_list(entries);
            }
            else if (name.equals("unique_any")){
                if (this.isSingleValued){
                    throw new IllegalArgumentException("Fuzzer.Variable.getAttr :: attribute unique_any is not applicable to single-valued variables");
                }
                if (this.entries.size()==0){
                    throw new UnavailableException("", false, true, false);
                }
                List<Integer> avail_idx = new ArrayList<>();
                for (int i=0; i<this.uniqueUsageCount.size(); i++){
                    if (this.uniqueUsageCount.get(i)==0){
                        avail_idx.add(i);
                    }
                }
                if (avail_idx.size()==0){
                    throw new UnavailableException("", false, false, true);
                }
                int idx = random_from_list(avail_idx);
                this.uniqueUsageCount.set(idx, 1);
                return this.entries.get(idx);
            }
            else if (name.equals("len")){
                if (this.isSingleValued){
                    return new Variable(1);
                }
                return new Variable(this.entries.size());
            }
            else if (name.equals("next")){
                if (this.isSingleValued){
                    throw new IllegalArgumentException("Fuzzer.Variable.getAttr :: attribute next is not applicable to single-valued variables");
                }
                if (this.entries.size()==0){
                    throw new UnavailableException("", false, true, false);
                }
                this.cursor += 1;
                return this.entries.get((this.cursor-1)%this.entries.size());
            }
            else if (name.equals("cur")){
                if (this.isSingleValued){
                    throw new IllegalArgumentException("Fuzzer.Variable.getAttr :: attribute cur is not applicable to single-valued variables");
                }
                if (this.entries.size()==0){
                    throw new UnavailableException("", false, true, false);
                }
                return this.entries.get(this.cursor%this.entries.size());
            }
            else if (name.equals("filter")){
                if (args.size()!=3){
                    throw new IllegalArgumentException("Fuzzer.Variable.getAttr :: filter function expects 3 arguments: attribute to be filtered, comparator, and a pivot value. "+args.size()+" are given");
                }
                return this.filter(args.get(0).getValue(), args.get(1).getValue(), args.get(2));
            }

        }
        private Variable filter(String attr, String comparator, Variable target) throws Exception{
            if (this.isSingleValued){
                throw new IllegalArgumentException("Fuzzer.Variable.filter :: filter operation is not allowed on single-valued variables");
            }
            Variable result = new Variable();
            for (Variable v : this.entries){
                if (v.getAttr(attr).compare(comparator, target)){
                    result.addEntry(v);
                }
            }
            return result;
        }
        public boolean compare(String comparator, Variable other) throws Exception{
            if (comparator.equals("==")){
                return this.equals(other);
            }
            if (comparator.equals("!=")){
                return !this.equals(other);
            }

            if (comparator.equals(">=")){
                try {
                    Integer operand_a = Integer.valueOf(this.value);
                    Integer operand_b = Integer.valueOf(other.value);
                    return operand_a.intValue()>=operand_b.intValue();
                }
                catch (NumberFormatException e){
                    throw new NumberFormatException(
                        "Fuzzer.Variable.compare :: >= comparator expects both variables to contain numerical value\n"+e.getMessage(), 
                        e
                    );
                }
            }
            if (comparator.equals("<=")){
                try {
                    Integer operand_a = Integer.valueOf(this.value);
                    Integer operand_b = Integer.valueOf(other.value);
                    return operand_a.intValue()<=operand_b.intValue();
                }
                catch (NumberFormatException e){
                    throw new NumberFormatException(
                        "Fuzzer.Variable.compare :: <= comparator expects both variables to contain numerical value\n"+e.getMessage(), 
                        e
                    );
                }
            }
            if (comparator.equals(">")){
                try {
                    Integer operand_a = Integer.valueOf(this.value);
                    Integer operand_b = Integer.valueOf(other.value);
                    return operand_a.intValue()>operand_b.intValue();
                }
                catch (NumberFormatException e){
                    throw new NumberFormatException(
                        "Fuzzer.Variable.compare :: > comparator expects both variables to contain numerical value\n"+e.getMessage(), 
                        e
                    );
                }
            }
            if (comparator.equals("<")){
                try {
                    Integer operand_a = Integer.valueOf(this.value);
                    Integer operand_b = Integer.valueOf(other.value);
                    return operand_a.intValue()<operand_b.intValue();
                }
                catch (NumberFormatException e){
                    throw new NumberFormatException(
                        "Fuzzer.Variable.compare :: < comparator expects both variables to contain numerical value\n"+e.getMessage(), 
                        e
                    );
                }
            }
            throw new IllegalArgumentException("Fuzzer.Variable.compare :: comparator "+comparator+" is not recognizable");
        }
        public String getValue() throws IllegalArgumentException{
            if (!this.isSingleValued){
                throw new IllegalArgumentException("Fuzzer.Variable.getValue :: getValue is not applicable to multi-valued variable");
            }
            return this.value;
        }
        public boolean isNumerical(){
            return this.containsNumerical;
        }

        public int getNumerical(){
            return this.numerical;
        }
        public boolean isBoolean(){
            return this.containsBoolean;
        }
        public boolean getBoolean(){
            if (!this.containsBoolean){
                throw new IllegalArgumentException("Fuzzer.Variable.getBoolean :: the current variable is not a boolean one");
            }
            return this.bool;
        }

        //by returning this at the end of the setter, we can use this class in an FP way
        public Variable addEntry(Variable v) throws IllegalArgumentException{
            if (this.isSingleValued){
                throw new IllegalArgumentException("Fuzzer.Variable.addEntry :: addEntry is not applicable to single-valued variable");
            }
            this.entries.add(v);
            this.uniqueUsageCount.add(0);
            return this;
        }
        public Variable getEntry(int idx)throws IllegalArgumentException{
            if (this.isSingleValued){
                throw new IllegalArgumentException("Fuzzer.Variable.addEntry :: addEntry is not applicable to single-valued variable");
            }
            return this.entries.get(idx);
        }
        // making sure this.value and this.entries are the same
        // does not check for reference counters and cursor (differences in those will be considered as same variable in different state)
        @Override
        public boolean equals(Object another){
            if (another instanceof Variable){
                Variable other = (Variable)another;
                if (this.value==other.value || (this.value!=null && this.value.equals(other.value))){
                    if (this.entries.size()==other.entries.size()){
                        for (int i=0; i<this.entries.size(); i++){
                            if (!this.entries.get(i).equals(other.entries.get(i))){
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        public static <T> List<T> copy_list(List<T> ori){
            if (ori==null){
                return null;
            }
            List<T> res = new ArrayList<>();
            for (T item: ori){
                res.add(item);
            }
            return res;
        }
    
        public static <T, U> HashMap<T, U> copy_map(HashMap<T, U> ori){
            if (ori==null){
                return null;
            }
            HashMap<T, U> res = new HashMap<>();
            for (HashMap.Entry<T, U> entry: ori.entrySet()){
                res.put(entry.getKey(), entry.getValue());
            }
            return res;
        }
    }

    public static class Options {
        private List<Integer> indices;
        private List<Double> weights;
        public Options(){
            this.indices = new ArrayList<>();
            this.weights = new ArrayList<>();
        }
        public void addOption(int index, double weight){
            this.indices.add(index);
            this.weights.add(weight);
        }

        public int randomly() throws DeadEndException{
            double total = 0;
            if (indices.size()==0){
                throw new DeadEndException("Options::randomly : no available options");
            }
            for (double w: this.weights){
                total = total + w;
            }
            for (int i=0; i<this.indices.size(); i++){
                if (total<this.weights.get(i)){
                    return this.indices.get(i);
                }
                total = total - this.weights.get(i);
            }
            return this.indices.get(this.indices.size()-1);
        }


    }

    // this is the entry point
    // at compile time, each standalone rule (without the fragment modifier)
    // will register itself here
    public String fuzz(SQLConnection conn, String rule){
        Context ctx = new Context(conn);
        Buffer buf = null;
        if (rule.equals("createTable")){
            buf = createTable(ctx);
        }
        return buf.toString();
    }

    // createTable :
    //     temp=$0
    //     rep=$_r(1, 6, ', ', 0) CREATE 
    //     _e('A BLOB field is not allowed in partition function') 
    //     ( TEMPORARY temp=$1 )?
    //     TABLE new[table]
    //     LB (new['column'] columnDefinition )**rep RB
    //     (
    //         '' |
    //         <temp==0> 'PARTITION BY' ...
    //     ) SC

    // fragment -> inline
    // context stack frame is callee established
    public static Buffer createTable(Context ctx){
        Buffer buf = new Buffer();
        ctx.enter(null, null); // create new stack frame, load arguments into callee frame
        ctx.eval(ctx.getSymbol("temp", null), "=", Variable.factory(0));
        ctx.eval(ctx.getSymbol("rep", null), "=", ctx.getSymbol("_r", List.of(Variable.factory(1), Variable.factory(6), Variable.factory(", "), Variable.factory(0))));
        buf.add(CREATE(ctx));
        ctx.addError(Variable.factory("A BLOB field is not allowed in partition function"));
        buf.add(node6(ctx));
        buf.add(TABLE(ctx));
        buf.add(ctx.eval("new", List.of(Variable.factory("table")))); //built-in function for generating new name
        buf.add(LB(ctx));
        buf.add(node30(ctx));
        buf.add(RB(ctx));
        buf.add(node56(ctx));
        buf.add(SC(ctx));
        ctx.ret(null);
    }

    public static Buffer node30(Context ctx){
        Buffer buf = new Buffer();
        Variable arg = ctx.getSymbol("rep", null);
        int r = arg.getNumerical();
        String delimiter = "";
        if (arg.getAttr("delimiter", null)!=null){
            delimiter = arg.getAttr("delimiter", null).getValue();
        }
        for (int i=0; i<r; i++){
            buf.add(ctx.eval("new", List.of(Variable.factory("column"))));
            buf.add(columnDefinition(ctx));
            buf.add(Variable.factory(delimiter));
        }
        buf.remove(buf.size()-1); //remove the last delimiter
        return buf;
    }

    //option class: 
    public static Buffer node56(Context ctx){
        Buffer buf = new Buffer();
        List<Integer> options = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        
        if (ctx.eval(ctx.getSymbol("temp", null), "==", Variable.factory(0)).getBoolean()){
            options.add(1);
            weights.add(0.5);
        }
        options.add(0);
        weights.add(0.5);
        if (options.size()==0){
            throw new DeadEndException("ERROR : No candidate available");
        }
        int index = randomlyFrom(options, weights);
        //first branch with 90% weightage
        if (index == 0){
            buf.add(node66(ctx));
        }
        if (index == 1){
            buf.add(node76(ctx));
        }
        return buf;
    }

    public static Buffer node66(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory(""));
        return buf;
    }

    public static Buffer node76(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("PARTITION BY"));
        return buf;
    }

    // this is a rule declared with the "fragment" keyword
    // conceptually, it is simply a fragment of rule than can be replaced into the caller
    // therefore it shares namespace with the caller
    // think macros in C
    // assume 90% weight for first branch
    public static Buffer columnDefinition(Context ctx) throws DeadEndException {
        Buffer buf = new Buffer();
        buf.add(node12(ctx));
        return buf;
    }

    // this is how an alternation node is rendered
    public static Buffer node12(Context ctx){
        Buffer buf = new Buffer();
        List<Integer> options = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        // all branches here doesn't have a predicate
        // otherwise, the corresponding lines needs to be wrapped in an if
        options.add(0);
        weights.add(0.9)
        options.add(1);
        weights.add(0.05);
        options.add(2);
        weights.add(0.05);
        if (options.size()==0){
            throw new DeadEndException("ERROR : No candidate available");
        }
        int index = randomlyFrom(options, weights);
        //first branch with 90% weightage
        if (index == 0){
            buf.add(node16(ctx));
        }
        if (index == 1){
            buf.add(node26(ctx));
        }
        if (index == 2){
            buf.add(node36(ctx));
        }
        return buf;
    }

    // This is an alternative node (a specific branch of alternation node)
    // in this specific case, since there is only one literal in the branch
    // it looks similar to the keyword definitions
    public static Buffer node16(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("INT"));
        return buf;
    }
    public static Buffer node26(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("FLOAT"));
        return buf;
    }
    public static Buffer node36(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("TEXT"));
        return buf;
    }


    // since we do not differentiate a terminal string literal in ANTLR
    // and a string in the variable system
    // we treat everything as a variable
    // Instantiating a variable will have the natural side-effect of 
    // printing to buffer, which is what we expect for a terminal string 
    // literal in ANTLR
    public static Buffer CREATE(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("CREATE"));
        return buf;
    }

    public static Buffer TABLE(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("TABLE"));
        return buf;
    }

    public static Buffer TEMPORARY(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("TEMPORARY"));
        return buf;
    }

    public static Buffer LB(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory("("));
        return buf;
    }

    public static Buffer RB(Context ctx){
        Buffer buf = new Buffer();
        buf.add(Variable.factory(")"));
        return buf;
    }

    // (temp=$1 TEMPORARY)?
    // silencing is done at compile time
    public static Buffer node6(Context ctx){
        Buffer buf = new Buffer();
        int r = random(0, 1); //short hand random number generator, avoid triggering the more complicated one from Context
        for (int i=0; i<r; i++){
            buf.add(TEMPORARY(ctx));
            ctx.eval(ctx.getSymbol("temp", null), "=", Variable.factory(1));
        }
        return buf;
    }
}