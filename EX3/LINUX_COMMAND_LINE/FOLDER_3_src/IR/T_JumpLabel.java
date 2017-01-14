package IR;

public class T_JumpLabel implements T_Exp {
    T_Label label;

    public T_JumpLabel(T_Label label) {
        this.label = label;
    }
}