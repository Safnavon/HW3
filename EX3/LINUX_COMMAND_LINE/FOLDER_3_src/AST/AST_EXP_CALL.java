package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_EXP_CALL extends AST_EXP
{
	public AST_METHOD_CALL call;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_EXP_CALL(AST_METHOD_CALL call)
	{
		this.call = call;
	}

	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		
		return call.isValid() ; 
		
	}
}