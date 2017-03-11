package IR;

import src.CGen;

/**
 * Created by User on 11/03/2017.
 */
public class T_Raw implements T_Exp {
    private final String stringToWrite;

    public T_Raw(String appendedString){
        this.stringToWrite = appendedString;
    }
    @Override
    public T_Temp gen() {
        CGen.append(this.stringToWrite);
        return null;
    }
}
