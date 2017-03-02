package IR;

public class T_Move implements T_Exp {
	T_Exp src,dest;
	
	public T_Move (T_Exp src, T_Exp dest){
		this.src=src;
		this.dest=dest;
	}

	@Override
	public T_Temp gen() {
		//TODO if move.dst is a T_Mem, use "sw". if move.dst is a T_Temp, use "addi dst,src,0"
		throw new Error("unimplemented");
	}
}