package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_FORMAL_LIST extends AST_Node
{
	public AST_FORMAL first; 
	public AST_FORMAL_LIST rest;
	
	public AST_FORMAL_LIST(AST_FORMAL first, AST_FORMAL_LIST rest){
		this.first = first;
		this.rest = rest;
	}

	public AST_FORMAL_LIST(AST_FORMAL first) {
		this.first = first;
		this.rest = null;
	}
}