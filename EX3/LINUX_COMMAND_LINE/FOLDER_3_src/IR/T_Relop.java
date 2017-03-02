package IR;

public class T_Relop implements T_Exp {

    public RELOPS op;

    public T_Exp left, right;

    public T_Relop(RELOPS op, T_Exp left, T_Exp right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public T_Temp gen() {
        throw new Error("unimplemented");
    }
}