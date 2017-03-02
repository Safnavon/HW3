package IR_NODES;

public class T_Binop extends T_exp {
	String op;
	T_exp left, right;
	
	public String getOp() {
		return op;
	}

	public T_exp getLeft() {
		return left;
	}

	public T_exp getRight() {
		return right;
	}

	public T_Binop(String op, T_exp left, T_exp right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
}
