package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_TYPE extends AST_Node
{
	public abstract boolean isExtending(AST_TYPE other);

	public IR_TYPE_WRAPPER isValid() throws Exception {
		return new IR_TYPE_WRAPPER(null, null);
	}
}