package IR;

public class T_ExpList implements T_Exp {
	T_Exp head;
	T_ExpList tail;

	public T_ExpList(T_Exp head, T_ExpList tail){
		this.head=head;
		this.tail=tail;
	}
}