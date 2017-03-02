package IR_NODES;

public class T_Cjump extends T_exp {
	String op;
	T_exp left, right;
	Label jumpIfTrue, jumpIfFalse;

	public T_Cjump(String op, T_exp left, T_exp right, Label jumpIfTrue, Label jumpIfFalse) {
		this.op = op;
		this.left = left;
		this.right = right;
		this.jumpIfFalse = jumpIfFalse;
		this.jumpIfTrue = jumpIfTrue;
	}

	public String getOp() {
		return op;
	}

	public T_exp getLeft() {
		return left;
	}

	public T_exp getRight() {
		return right;
	}

	public Label getJumpIfTrue() {
		return jumpIfTrue;
	}

	public Label getJumpIfFalse() {
		return jumpIfFalse;
	}
}
