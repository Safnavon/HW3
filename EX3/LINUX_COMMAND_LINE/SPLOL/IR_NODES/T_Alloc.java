package IR_NODES;

public class T_Alloc extends T_exp {
	T_exp size;
	public T_Alloc(T_exp size){
		this.size=size;
	}
	public T_exp getSize() {
		return size;
	}

}
