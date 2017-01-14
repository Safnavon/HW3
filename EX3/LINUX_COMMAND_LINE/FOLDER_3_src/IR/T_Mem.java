package IR;

public class T_Mem implements T_Exp {
    T_Binop location;

    public T_Mem(T_Binop location) {
        this.location = location;
    }
}