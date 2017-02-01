package IR;

public class T_Seq implements T_Exp {

    public T_Exp left, right;

    public T_Seq(T_Exp left, T_Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public T_Temp gen() {
        this.left.gen();
        this.right.gen();
        return null;
    }
}