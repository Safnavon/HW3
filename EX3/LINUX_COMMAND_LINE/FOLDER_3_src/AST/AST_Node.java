package AST; import src.ClassChecker;
import src.SymbolTable;


public abstract class AST_Node 
{

	public abstract AST_TYPE isValid() throws Exception;
	
}