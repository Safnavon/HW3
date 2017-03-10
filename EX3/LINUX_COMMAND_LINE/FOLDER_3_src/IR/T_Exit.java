package IR;

import src.CGen;

/**
 * Created by nitzanh on 07/03/2017.
 */
public class T_Exit implements T_Exp {

    @Override
    public T_Temp gen() {
        CGen.append(String.format("\tli $v0,10%n"));
        CGen.append(String.format("\tsyscall%n"));
        return null;
    }
}
