package IR;

public class T_CJump implements T_Exp {
    public T_Exp cond;
    public T_JumpLabel jumpToHereIfTrue, jumpToHereIfFalse;
    //TODO
    //DROR: this doesn't make sense to me. i think CJump should have an expression + label. (if exp != 0, go to label).
    //		we can also use the IR node called T_Relop, that accepts RELOPS op, T_Exp left, T_Exp right.
//	public T_CJump(RELOPS op, T_Exp left, T_Exp right, String  jumpToHereIfTrue, String jumpToHereIfFalse){
//		this.op=op;
//		this.left=left;
//		this.right=right;
//		this.jumpToHereIfFalse=jumpToHereIfFalse;
//		this.jumpToHereIfTrue=jumpToHereIfTrue;
//	}
    public T_CJump(T_Exp cond, T_JumpLabel jumpToHereIfTrue, T_JumpLabel jumpToHereIfFalse) {
        this.cond = cond;
        this.jumpToHereIfFalse = jumpToHereIfFalse;
        this.jumpToHereIfTrue = jumpToHereIfTrue;
    }

    @Override
    public T_Temp gen() {
        throw new Error("unimplemented");
    }
}