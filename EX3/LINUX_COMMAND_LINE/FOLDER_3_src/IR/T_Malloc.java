package IR;

import src.CGen;

public class T_Malloc implements T_Exp {

    int size;

    public T_Malloc(int size) {
        this.size = size;
    }

    /**
     *
     * @return temp containing address in memory of the malloced class
     */
    @Override
    public T_Temp gen() {
        T_Temp res = new T_Temp();
        CGen.append(String.format(
                "\tli\t$v0, 9%n"+
                "\tli\t$a0, %1$d%n"+
                "\t\tsyscall%n"+
                "\taddi\t%2$s, $v0, 0%n",
                this.size, res
        ));
        return res;
    }
}
