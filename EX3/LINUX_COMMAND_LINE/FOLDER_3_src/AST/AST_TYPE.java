package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_TYPE extends AST_Node
{
	public abstract boolean isExtending(AST_TYPE other) throws Exception;


	public abstract IR_TYPE_WRAPPER isValid() throws Exception;
}