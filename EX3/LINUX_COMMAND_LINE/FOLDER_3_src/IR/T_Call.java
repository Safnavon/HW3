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
        // push args to stack
        if (args != null) {
        	args.gen();
        }
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