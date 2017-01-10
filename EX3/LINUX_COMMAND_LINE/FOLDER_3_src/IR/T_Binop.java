package IR;

public class T_Binop implements T_Exp {
	
	public BINOPS op;
	
	public T_Exp left,right;
	
	public T_Binop(BINOPS op, T_Exp left, T_Exp right){
		this.op =op;
		this.left=left;
		this.right=right;
	}
}