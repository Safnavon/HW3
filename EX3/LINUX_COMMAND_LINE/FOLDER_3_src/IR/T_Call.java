package IR;

import src.CGen;

public class T_Call implements T_Exp {
    public T_Temp methodAddrReg;
    T_Temp thisTemp;
    public T_ExpList args;
    public T_Exp prologue, epilogue;

    public T_Call(T_Temp reg, T_ExpList args, T_Temp thisTemp, T_Exp prologue, T_Exp epilogue) {
        this.methodAddrReg = reg;
        this.args = args;
        this.thisTemp = thisTemp;
        this.prologue=prologue;
        this.epilogue=epilogue;
    }

    @Override
    public T_Temp gen() {
        // push args to stack
        args.gen();
        // add this as "first argument"
        CGen.append("\taddi $sp,$sp,-4\n");
        CGen.append("\tsw " + thisTemp + ",0($sp)\n");

        prologue.gen();

        CGen.append("\tjalr " + methodAddrReg + "\n");

        T_Temp resTemp = new T_Temp("return_value", true);
        CGen.append("\taddi " + resTemp + ",$a0,0\n");

        epilogue.gen();

        return resTemp; // holds the result
    }
}