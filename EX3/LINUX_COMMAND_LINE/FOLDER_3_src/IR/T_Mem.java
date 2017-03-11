package IR;

import src.CGen;

public class T_Mem implements T_Exp {
    T_Binop location;

    public T_Mem(T_Binop location) {
        this.location = location;
    }
    private T_CJump accessViolation(T_Temp temp) {
        T_Relop relop = new T_Relop(RELOPS.EQUAL, temp, new T_Const(0));
        return new T_CJump(relop, new T_Label("access_violation"));
    }
    /**
     *
     * @return temp containing data from memory
     */
    @Override
    public T_Temp gen() {
        T_Temp src = this.location.gen();
        T_Temp dst = new T_Temp();
        accessViolation(src).gen();
        CGen.append(String.format(
                "\tlw\t%s, 0(%s)%n",
                dst,
                src
        ));
        return dst;
    }
}