package IR;

import src.CGen;

public class T_Malloc implements T_Exp {

    T_Exp size;

    public T_Malloc(int size) {
        this.size = new T_Const(size);
    }

    public T_Malloc(T_Exp size) {
        this.size = size;
    }

    /**
     *
     * @return temp containing address in memory of the malloced class
     */
    @Override
    public T_Temp gen() {
        T_Temp res = new T_Temp();
        T_Temp size = this.size.gen();
        //TODO
        CGen.append(String.format(
                "\taddi\t$a0, %1$s, 0%n"+
                "\tli\t$v0,9%n"+
                "\t\tsyscall%n"+
                "\taddi\t%2$s, $v0, 0%n",
                size, res
        ));
        return res;
    }
}
