package src;

import IR.*;

import java.io.PrintWriter;

/**
 * abstraction for writing mips code
 */
public class CGen {

    private static PrintWriter writer;
    /**
     * will write the string at the end of the mips
     * @param code to be appended to the mips
     */
    public static void append(String code){
        writer.print(code);
    }

    /**
     * call when done writing (can return the code or close file...)
     */
    public static void done(){


        throw new Error("unimplemented");
    }

    public static void gen(T_Seq program, PrintWriter printWriter){
        writer = printWriter;
        String nl = String.format("%n");
        append(".data"+nl);
        append(ClassChecker.generateAllVFTables() + nl);
        append(".text"+nl);
        program.gen();//calls append
    }

}


