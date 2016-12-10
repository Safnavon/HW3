package AST;

public abstract class AST_TYPE extends AST_Node
{
	public TYPES type;
	
	public AST_TYPE(TYPES type){
		this.type = type;
	}
}