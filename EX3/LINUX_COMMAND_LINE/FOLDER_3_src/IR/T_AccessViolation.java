package IR;

import src.CGen;

/**
 * Created by nitzanh on 11/03/2017.
 */
public class T_AccessViolation implements T_Exp {


    @Override
    public T_Temp gen() {
        CGen.append(String.format("\tLabel_0_access_violation:%n"));

        // print 666
        CGen.append(String.format("\tli $a0,666"));
        CGen.append(String.format("\tli $v0,1%n"));
        CGen.append(String.format("\tsyscall%n"));

        // exit
        CGen.append(String.format("\tli $v0,10%n"));
        CGen.append(String.format("\tsyscall%n"));
        return null;
    }
}
