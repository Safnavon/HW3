package IR;

public class T_Function implements T_Exp{
	public T_Exp prologue, epilogue, body;
	public T_Label name;
	
	public T_Function(T_Exp prologue, T_Exp epilogue, T_Exp body, T_Label name){
		this.prologue=prologue;
		this.epilogue=epilogue;
		this.body=body;
		this.name=name;
	}

	@Override
	public T_Temp gen() {
		throw new Error("unimplemented");
	}
}