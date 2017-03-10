package IR;

import src.CGen;

/**
 * Created by nitzanh on 07/03/2017.
 */
public class T_PrintInt implements  T_Exp {
    T_Temp temp;

    public T_PrintInt (T_Temp temp){
        this.temp=temp;
    }


    @Override
    public T_Temp gen() {

        CGen.append(String.format(
                "\taddi\t$a0,%s,0%n",
                temp
        ));
        CGen.append(String.format(
                "\tli\t$v0,1%n"

        ));
        CGen.append(String.format(
                "\tsyscall%n"
        ));

        return temp;
    }
}
