package IR;

import src.CGen;

public class T_ExpList implements T_Exp {
	T_Exp head;
	T_ExpList tail;

	public T_ExpList(T_Exp head, T_ExpList tail){
		this.head=head;
		this.tail=tail;
	}

	@Override
	public T_Temp gen() {
		if (head != null) {
			CGen.append(String.format("\taddi $sp,$sp,-4%n"));
			T_Temp expTemp = head.gen();
			CGen.append(String.format("\tsw " + expTemp + ",0($sp)%n"));
			if (tail != null) {
				tail.gen();
			}
		}
		return null;
	}
}