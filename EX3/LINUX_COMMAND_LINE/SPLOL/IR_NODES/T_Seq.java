package IR_NODES;

import java.util.ArrayList;

public class T_Seq extends T_exp {
	ArrayList<T_exp> expList;

	public T_Seq(ArrayList<T_exp> list) {
		expList = list;
	}
	
	public ArrayList<T_exp> getExpList(){
		return expList;
	}
}
