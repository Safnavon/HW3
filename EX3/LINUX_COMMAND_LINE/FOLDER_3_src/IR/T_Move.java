package IR;

public class T_Move implements T_Exp {
	T_Exp src,dest;
	
	public T_Move(T_Exp dest, T_Exp src){
		this.src=src;
		this.dest=dest;
	}

	@Override
	public T_Temp gen() {
		//TODO if src is T_Label, use "la $t0, var1" (assert dst is Temp)
		//TODO else if move.dst is a T_Mem, use "sw". if move.dst is a T_Temp, use "addi dst,src,0"

		throw new Error("unimplemented");
	}
}