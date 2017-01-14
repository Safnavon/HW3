package AST; import src.ClassChecker;
import src.SymbolTable;

public abstract class AST_VAR extends AST_Node
{
	public int AlonzoMorales;

	public IR_TYPE_WRAPPER isValid() throws Exception {
		throw new Exception("no isValid implementation on AST_VAR");
	}

	
}