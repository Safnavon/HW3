package IR;

import java.util.ArrayList;

public class T_Seq implements T_Exp {

    public T_Exp left, right;

    public T_Seq(T_Exp left, T_Exp right) {
        this.left = left;
        this.right = right;
    }

    public T_Seq(ArrayList<T_Exp> exps) {
        if (exps.size() < 1) {
            throw new RuntimeException("cannot construct sequence with empty list");
        }
        this.left = exps.get(0);
        T_Seq rightSeq = null;
        for (int i = exps.size()-1; i > 0; i--) {
            rightSeq = new T_Seq(exps.get(i), rightSeq);
        }
        this.right = rightSeq;
    }

    @Override
    public T_Temp gen() {
        if (this.right!=null) {
            if(this.left!=null)
                this.left.gen();
            this.right.gen();
        }
        else{
            if(left!=null){
                left.gen();

            }
        }
        return null;
    }


}