package IR;

import src.CGen;

public class T_Call implements T_Exp {
    T_Temp thisTemp;
    public T_ExpList args;
    public T_Exp prologue, epilogue;

    public T_Call(T_ExpList args, T_Temp thisTemp, T_Exp prologue, T_Exp epilogue) {
        this.args = args;
        this.thisTemp = thisTemp;
        this.prologue=prologue;
        this.epilogue=epilogue;
    }

    @Override
    public T_Temp gen() {
        // set ra on sp+4
        // set fp on sp+8
        // set sp on sp+12
        // set args on sp+16+(len - index) REVERSE ORDER
        if (args != null) {
            //assume fp and sp are old
        	args.gen();
        }
        // set "this" on sp + 16 + args.length + 4
        // set sp to sp + 16 + args.length + 4
        // set fp to sp
        // jalr
        // body (takes care of setting sp, fp and ra to previous values)
        // set sp to fp - args.length - 4
        // set ra to fp - args.length - 12
        // set fp to fp - args.length - 8
        // consume return value END




        // add this as "first argument"
        CGen.append(String.format("\taddi $sp,$sp,-4%n"));
        CGen.append(String.format("\tsw " + thisTemp + ",0($sp)%n"));

        prologue.gen();

        CGen.append(String.format("\tjalr $a1%n"));

        T_Temp resTemp = new T_Temp("return_value", true);
        CGen.append(String.format("\taddi " + resTemp + ",$a2,0%n"));

        epilogue.gen();

        return resTemp; // holds the result
    }
}