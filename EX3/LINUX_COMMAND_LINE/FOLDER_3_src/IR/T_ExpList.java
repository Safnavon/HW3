package IR;

import src.CGen;

import java.util.ArrayList;

public class T_ExpList implements T_Exp {
	T_Exp head;
	T_ExpList tail;
	public int size = 0;

	public T_ExpList(T_Exp head, T_ExpList tail){
		this.head=head;
		this.tail=tail;
	}

	@Override
	public T_Temp gen() {
		if (head != null) {
			ArrayList<T_Exp> args = toArrayList();
			for (int i = 0 ; i < args.size() ; i++) {
				T_Temp expTemp = args.get(args.size()-1-i).gen();
				new T_Move(
						new T_Mem(
								new T_Binop(
										BINOPS.PLUS,
										new T_Temp("$sp"),
										new T_Const(-16 - i * 4)
								)
						),
						expTemp
				).gen();
			}
		}
		return null;
	}

	ArrayList<T_Exp> toArrayList() {
		ArrayList<T_Exp> arr = new ArrayList<>();
		T_Exp first = head;
		T_ExpList rest = tail;
		while (first != null) {
			arr.add(first);
			size++;
			first = rest != null ? rest.head : null;
			rest = rest != null ? rest.tail: null;
		}
		return arr;
	}
}