package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;


public abstract class AST_VAR extends AST_Node
{
	public int AlonzoMorales;

	public IR_TYPE_WRAPPER isValid() throws Exception {
		throw new Error("no isValid implementation on AST_VAR");
	}

	
}