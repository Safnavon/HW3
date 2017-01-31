package IR;

public class T_CJump implements T_Exp {
	public RELOPS op;
	public T_Exp left,right;
	public String jumpToHereIfTrue, jumpToHereIfFalse;
	//DROR: this doesn't make sense to me. i think CJump should have an expression + label. (if exp != 0, go to label).
	//		we can also add a IR node called T_Relop, that accepts RELOPS op, T_Exp left, T_Exp right.
	public T_CJump(RELOPS op, T_Exp left, T_Exp right, String  jumpToHereIfTrue, String jumpToHereIfFalse){
		this.op=op;
		this.left=left;
		this.right=right;
		this.jumpToHereIfFalse=jumpToHereIfFalse;
		this.jumpToHereIfTrue=jumpToHereIfTrue;
	}
}