package IR;

public class T_Call implements T_Exp {
	public String name;
	public T_ExpList args;
	
	public T_Call(String name, T_ExpList args){
		this.name=name;
		this.args=args;
	}
	
}