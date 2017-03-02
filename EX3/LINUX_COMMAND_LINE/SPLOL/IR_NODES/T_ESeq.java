package IR_NODES;

import java.util.ArrayList;

public class T_ESeq extends T_exp {
	ArrayList<T_exp> expList;
	T_exp value;

	public T_ESeq(ArrayList<T_exp> expList, T_exp value) {
		this.expList = expList;
		this.value = value;
	}

	public ArrayList<T_exp> getExpList() {
		return expList;
	}

	public T_exp getValue() {
		return value;
	}
}
