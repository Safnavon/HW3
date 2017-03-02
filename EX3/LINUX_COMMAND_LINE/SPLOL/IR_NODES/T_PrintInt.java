package IR_NODES;

public class T_PrintInt extends T_exp{
	T_exp value;
	
	public T_PrintInt(T_exp value){
		this.value=value;
	}
	public T_exp getValue() {
		return value;
	}
}
