package AST; import SymbolTable; import ClassChecker;

public class AST_FIELD extends AST_CLASS_BODY_ITEM
{
	public AST_TYPE type;
	public String[] names;
	
	public AST_FIELD(AST_TYPE type, String[] names){
		this.type = type;
		this.names = names;
	}
}