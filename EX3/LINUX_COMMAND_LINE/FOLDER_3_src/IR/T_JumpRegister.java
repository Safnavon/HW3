package IR;

public class T_JumpRegister implements T_Exp {
    T_Temp temp;

    public T_JumpRegister(T_Temp temp) {
        this.temp = temp;
    }

    @Override
    public T_Temp gen() {
        throw new Error("unimplemented");
    }
}