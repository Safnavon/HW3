package IR;

public class T_Move implements T_Exp {
	T_Exp src,dest;
	
	public T_Move (T_Exp src, T_Exp dest){
		this.src=src;
		this.dest=dest;
	}
}