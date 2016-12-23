package AST; import src.ClassChecker;
import src.SymbolTable;

public abstract class AST_TYPE extends AST_Node
{
	public abstract boolean isExtending(AST_TYPE other);
}