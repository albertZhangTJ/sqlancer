package lancerfuzz;

// import java.io.FileInputStream;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.Writer;
// import java.nio.file.Files;
// import java.text.DateFormat;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.ServiceLoader;
// import java.util.Set;
// import java.util.HashSet;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.ScheduledExecutorService;
// import java.util.concurrent.TimeUnit;
// import java.util.concurrent.atomic.AtomicBoolean;
// import java.util.concurrent.atomic.AtomicLong;0

import com.beust.jcommander.JCommander;
import com.beust.jcommander.JCommander.Builder;


public class Main {

    

    public static void main(String[] args){

        Options options=new Options();
        Builder commandBuilder = JCommander.newBuilder().addObject(options);
        JCommander jc = commandBuilder.programName("lancerfuzz").build();
        jc.parse(args);
        
        if (jc.getParameters().isEmpty()) {
            Utils.oops("Main::main : parameters needed for running lancerfuzz, see usage below");
            jc.usage();
            return;
        }

        if (options.help){
            jc.usage();
            return;
        }
        
        if (options.grammarRules.size()==0){
            Utils.panic("Main::main : need one or more grammar files for parser rules");
        }

        if (options.config.equals("")){
            Utils.panic("Main::main : need one configuration file");
        }

        // if (options.stagesConfig.equals("")){
        //     System.out.println("ERROR: need one JSON file for stages of fuzzing");
        //     System.exit(-1);
        // }

        Processor.generate_fuzzer(options);
    }
}