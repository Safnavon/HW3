package IR;

import src.CGen;

public class T_Function implements T_Exp{
	public T_Exp body, exit;
	public T_Label name;
	
	public T_Function(T_Exp body, T_Label name, T_Exp exit){
		this.body=body;
		this.name=name;
		this.exit=exit;
	}

	@Override
	public T_Temp gen() {
		name.gen();
		body.gen();
		exit.gen();
		return null;
	}
}