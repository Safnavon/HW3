package AST; import src.ClassChecker;
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

	@Override
	public void isValid(AST_TYPE expectedReturnValue) throws Exception {		
		SymbolTable.openScope();
		stmts.isValid();
		SymbolTable.closeScope();
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}