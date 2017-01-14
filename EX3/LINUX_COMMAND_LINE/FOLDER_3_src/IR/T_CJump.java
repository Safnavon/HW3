package IR;

public class T_CJump implements T_Exp {
	public T_Binop op;
	public T_Exp left,right;
	public String jumpToHereIfTrue, jumpToHereIfFalse;
	
	public T_CJump(T_Binop op, T_Exp left, T_Exp right, String  jumpToHereIfTrue, String jumpToHereIfFalse){
		this.op=op;
		this.left=left;
		this.right=right;
		this.jumpToHereIfFalse=jumpToHereIfFalse;
		this.jumpToHereIfTrue=jumpToHereIfTrue;
	}
}