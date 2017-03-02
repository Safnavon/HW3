package IR_NODES;

import java.util.ArrayList;

public class T_Call extends T_exp {
	T_exp object;
	int offset;
	public T_exp getObject() {
		return object;
	}

	public int getOffset() {
		return offset;
	}

	public ArrayList<T_exp> getArgs() {
		return args;
	}

	ArrayList<T_exp> args;

	public T_Call(T_exp object, int offset, ArrayList<T_exp> args) {
		this.object = object;
		this.offset = offset;
		this.args = args;
	}

}
