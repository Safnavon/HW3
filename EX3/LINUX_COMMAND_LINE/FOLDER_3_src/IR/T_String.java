package IR;

public class T_String implements T_Exp{
    T_Temp address;

    public T_String(T_Temp address) {
        this.address = address;
    }

    @Override
    public T_Temp gen() {
        return null;
    }
}
