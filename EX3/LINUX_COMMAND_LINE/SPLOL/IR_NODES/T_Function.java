package IR_NODES;

public class T_Function extends T_exp {
	T_exp body;
	Label name;

	public T_Function( T_exp body, Label name) {
		this.body = body;
		this.name = name;
	}

	public T_exp getBody() {
		return body;
	}

	public Label getName() {
		return name;
	}
}
