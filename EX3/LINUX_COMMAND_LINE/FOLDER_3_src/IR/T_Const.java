package IR;

import src.CGen;

public class T_Const implements T_Exp {
	public int value;

	public T_Const(int value){
		this.value=value;
	}

	@Override
	public T_Temp gen() {
		T_Temp temp = new T_Temp();
        CGen.append(String.format(
                "\tli\t%1$s, %2$s%n",
                temp,value
        ));
        return temp;
	}
}