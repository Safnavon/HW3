package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_STMT_CALL extends AST_STMT
{
	public AST_METHOD_CALL call;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_CALL(AST_METHOD_CALL call)
	{
		this.call = call;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		return null;
	}
	
	public void isValid(AST_TYPE expectedReturnValue) throws Exception {
		call.isValid();
	}
}