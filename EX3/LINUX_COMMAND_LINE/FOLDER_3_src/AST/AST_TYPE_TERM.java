package AST; import SymbolTable; import ClassChecker;

public class AST_TYPE_TERM extends AST_TYPE
{
	public TYPES type;
	
	public AST_TYPE_TERM(TYPES type){
		this.type = type;
	}
}