package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_STMT extends AST_Node
{

	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		throw new Exception("isValid(AST_TYPE expectedReturnValue) is not implemented for this type of AST_STMT");
	}

	

}