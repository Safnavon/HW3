package IR;

public class T_Call implements T_Exp {
    public T_Label name;
    public T_ExpList args;

    public T_Call(T_Label name, T_ExpList args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public T_Temp gen() {
        throw new Error("unimplemented");
    }
}