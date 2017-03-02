package IR_NODES;

public class T_String extends T_exp {
	Stringlit str;
	
	public Stringlit getStr() {
		return str;
	}

	public T_String(Stringlit str){
		this.str=str;
	}
}
