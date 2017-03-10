package IR;

import src.CGen;

public class T_CJump implements T_Exp {
    public T_Exp cond;
    public T_Label jumpToHereIfTrue;


    public T_CJump(T_Exp cond, T_Label jumpToHereIfTrue) {
        this.cond = cond;
        this.jumpToHereIfTrue = jumpToHereIfTrue;
    }

    @Override
    public T_Temp gen() {

        T_Temp condGen = cond.gen();

        T_Temp zero = new T_Temp();

        CGen.append(String.format(
                "\tli\t%1$s, %2$s%n",
                zero,0
        ));

        CGen.append(String.format(
                "\tbne\t%s,%s,%s%n",
                condGen,zero, jumpToHereIfTrue.getName()
        ));

        return condGen;

    }
}