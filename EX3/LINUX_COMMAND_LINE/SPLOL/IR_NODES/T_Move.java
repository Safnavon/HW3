package IR_NODES;

public class T_Move extends T_exp {
	T_exp dst, src;

	public T_Move(T_exp d, T_exp s) {
		dst = d;
		src = s;
	}
	
	public T_exp getDst(){
		return dst;
	}
	
	public T_exp getSrc(){
		return src;
	}
}
