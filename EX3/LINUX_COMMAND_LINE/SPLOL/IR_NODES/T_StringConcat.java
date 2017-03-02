package IR_NODES;

public class T_StringConcat extends T_exp {
	T_exp left,right;
	
	public T_exp getLeft() {
		return left;
	}

	public T_exp getRight() {
		return right;
	}

	public T_StringConcat(T_exp left,T_exp right){
		this.left=left;
		this.right=right;
	}
}
