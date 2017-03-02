package IR_NODES;

public class T_JumpRegister extends T_exp {
	Temp temp;

	public Temp getTemp() {
		return temp;
	}

	public T_JumpRegister(Temp t) {
		temp = t;
	}
}
