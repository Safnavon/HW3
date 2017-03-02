package IR;

import src.CGen;

public class T_Mem implements T_Exp {
    T_Binop location;

    public T_Mem(T_Binop location) {
        this.location = location;
    }

    /**
     *
     * @return temp containing data from memory
     */
    @Override
    public T_Temp gen() {
        T_Temp src = this.location.gen();
        T_Temp dst = new T_Temp();

        CGen.append(String.format(
                "\tlw\t%s, (%s)",
                dst,
                src
        ));
        return dst;
    }
}