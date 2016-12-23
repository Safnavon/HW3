package AST; import SymbolTable; import ClassChecker;

public class AST_FORMAL extends AST_Node
{
	public AST_TYPE type;
	public String name;
	
	public AST_FORMAL(AST_TYPE type, String name){
		this.type = type;
		this.name = name;
	}
}