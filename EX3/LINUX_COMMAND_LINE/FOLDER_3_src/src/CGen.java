package src;

import IR.*;

/**
 * abstraction for writing mips code
 */
public class CGen {

    /**
     * will write the string at the end of the mips
     * @param code to be appended to the mips
     */
    public static void append(String code){
        throw new Error("unimplemented");
    }

    /**
     * call when done writing (can return the code or close file...)
     */
    public static void done(){


        throw new Error("unimplemented");
    }

    public static void gen(T_Seq program){
        String nl = String.format("%n");
        append(".data"+nl);
        append(ClassChecker.generateAllVFTables() + nl);
        //TODO strings
        append(".text"+nl);
        program.gen();//calls append

    }




//    public static T_Temp gen(T_Exp exp){
//        throw new Error("unimplemented");
//    }
//
//    public static T_Temp gen(T_Label label){
//        throw new Error("unimplemented");
//    }
//
//    public static T_Temp gen(T_Binop binop){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_Call call){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_CJump cJump){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_Const constant){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_ExpList expList){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_Function function){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_JumpLabel jumpLabel){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_JumpRegister jumpRegister){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_Mem mem){
//        //will read contents to temp (according to oren)
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_Move move){
//
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_Relop relop){
//        throw new Error("unimplemented");
//    }
//    public static T_Temp gen(T_Seq seq){
//        throw new Error("unimplemented");
//    }
////    public static void gen(T_Temp temp){
////        throw new Error("unimplemented");
////    }

}


