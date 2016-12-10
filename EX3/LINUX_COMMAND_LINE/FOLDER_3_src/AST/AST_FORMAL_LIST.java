package AST;

public class AST_FORMAL_LIST extends AST_Node
{
	public AST_TYPE first; 
	public AST_FORMAL_LIST rest;
	
	public AST_FORMAL_LIST(AST_TYPE first, AST_FORMAL_LIST rest){
		this.first = first;
		this.rest = rest;
	}
}