package AST; import src.ClassChecker;
import src.SymbolTable;

public abstract class AST_STMT extends AST_Node
{

	public void isValid(AST_TYPE expectedReturnValue) throws Exception {
		throw new Exception("isValid(AST_TYPE expectedReturnValue) is not imlepemnted for this type of AST_STMT");
	}

	

}