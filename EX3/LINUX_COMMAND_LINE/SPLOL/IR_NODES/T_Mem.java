package IR_NODES;

public class T_Mem extends T_exp {
	T_exp child;
	boolean location;

	public T_Mem(T_exp exp) {
		child = exp;
		location=false;
	}
	
	public T_Mem(T_exp exp, boolean location) {
		child = exp;
		this.location=location;
	}
	
	public T_exp getChild(){
		return child;
	}
	
	public void setLocation(){
		location=true;
	}
	
	public boolean isLocation(){
		return location;
	}
	
}
