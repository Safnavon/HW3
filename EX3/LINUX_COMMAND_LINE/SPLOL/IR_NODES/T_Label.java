package IR_NODES;

public class T_Label extends T_exp {
	Label label;

	public T_Label(Label l) {
		label = l;
		
	}
	
	public int getLabelIndex(){
		return label.getIndex();
	}
	
	public Label getLabel(){
		return label;
	}
}
