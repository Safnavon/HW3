package IR;

public class T_Call implements T_Exp {
    public T_Temp methodAddrReg;
    public T_ExpList args;

    public T_Call(T_Temp reg, T_ExpList args) {
        this.methodAddrReg = reg;
        this.args = args;
    }

    @Override
    public T_Temp gen() {
        throw new Error("unimplemented");
    }
}