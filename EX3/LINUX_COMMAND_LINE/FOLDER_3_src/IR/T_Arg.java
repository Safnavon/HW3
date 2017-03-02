package IR;

/**
 * used for expressing "give me a temp containing an argument passed to the current function
 */
public class T_Arg implements T_Exp {
    int argIndex;

    public T_Arg(int argIndex) {
        this.argIndex = argIndex;
    }

    @Override
    public T_Temp gen() {
        throw new Error("unimplemented");
    }
}
