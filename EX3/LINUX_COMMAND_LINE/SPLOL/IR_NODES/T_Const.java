package IR_NODES;

public class T_Const extends T_exp {
	int value;
	
	public T_Const(int val){
		value=val;
	}
	
	public int getValue(){
		return this.value;
	}
}
