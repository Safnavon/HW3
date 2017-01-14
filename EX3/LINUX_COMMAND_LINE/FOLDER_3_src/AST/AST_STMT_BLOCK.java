package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_BLOCK extends AST_STMT
{
	public AST_STMT_LIST stmts;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_BLOCK(AST_STMT_LIST stmts)
	{
		this.stmts = stmts;
	}

	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		SymbolTable.openScope();
		stmts.isValid(expectedReturnValue);
		SymbolTable.closeScope();
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}

}