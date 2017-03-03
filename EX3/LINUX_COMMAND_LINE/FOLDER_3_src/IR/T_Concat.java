package IR;

/**
 * Created by User on 03/03/2017.
 */
public class T_Concat implements T_Exp {
    T_String s1,s2;

    public T_Concat(T_String s1, T_String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public T_Temp gen() {
        throw new Error("TODO");
    }
}
