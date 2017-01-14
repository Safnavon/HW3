package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;


public abstract class AST_Node 
{

	public abstract IR_TYPE_WRAPPER isValid() throws Exception;
	
}