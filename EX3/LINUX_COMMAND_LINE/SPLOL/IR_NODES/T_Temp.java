package IR_NODES;

public class T_Temp extends T_exp {
	Temp temp;

	public Temp getTemp() {
		return temp;
	}

	public T_Temp(Temp t) {
		temp = t;
	}
}
