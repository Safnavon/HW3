package IR;

public class T_Const implements T_Exp {
	public int value;

	public T_Const(int value){
		this.value=value;
	}

	@Override
	public T_Temp gen() {
		throw new Error("unimplemented");
	}
}