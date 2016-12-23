package AST; import SymbolTable; import ClassChecker;

public abstract class AST_TYPE extends AST_Node
{
	public abstract boolean isExtending(AST_TYPE other);
}