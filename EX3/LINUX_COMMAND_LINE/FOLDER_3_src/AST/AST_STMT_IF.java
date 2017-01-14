package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond,AST_STMT body)
	{
		this.cond = cond;
		this.body = body;
	}
	
	
	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		IR_TYPE_WRAPPER wrapper = cond.isValid();
		if (! wrapper.type.equals(new AST_TYPE_TERM(TYPES.INT))) {
			throw new Exception("if statement condition must be an integer");
		}
		SymbolTable.openScope();
		body.isValid(expectedReturnValue);
		SymbolTable.closeScope();
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}
}