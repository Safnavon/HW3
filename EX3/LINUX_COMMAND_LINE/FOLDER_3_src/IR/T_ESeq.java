package IR;

import java.util.ArrayList;

/* returns the temp of the last expression in the sequence */
public class T_ESeq implements T_Exp {

    public T_Exp left, right;

    public T_ESeq(T_Exp left, T_Exp right) {
        this.left = left;
        this.right = right;
    }

    public T_ESeq(ArrayList<T_Exp> exps) {
        if (exps.size() < 1) {
            throw new RuntimeException("cannot construct sequence with empty list");
        }
        this.left = exps.get(0);
        T_ESeq rightSeq = null;
        for (int i = exps.size()-1; i > 0; i--) {
            rightSeq = new T_ESeq(exps.get(i), rightSeq);
        }
        this.right = rightSeq;
    }

    @Override
    public T_Temp gen() {
        if (this.right!=null) {
            this.left.gen();
            return this.right.gen();
        }
        else{
            return  left.gen();
        }
    }
}
