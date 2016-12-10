package AST;

public class AST_PROGRAM extends AST_Node
{
	public AST_CLASS_DECLARE first;
	public AST_PROGRAM rest;
	
	public AST_PROGRAM(AST_CLASS_DECLARE first, AST_PROGRAM	 rest){
		this.first = first;
		this.rest = rest;
	}
}