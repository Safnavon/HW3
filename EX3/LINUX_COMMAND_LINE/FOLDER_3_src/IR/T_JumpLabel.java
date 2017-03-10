package IR;

import src.CGen;

public class T_JumpLabel implements T_Exp {
    T_Label label;

    public T_JumpLabel(T_Label label) {
        this.label = label;
    }

    @Override
    public T_Temp gen() {

        CGen.append(String.format(
                "\tj\t%s%n",
                label.getName()
        ));


        return null;
    }
}