package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_STMT_BLOCK extends AST_STMT
{
	public AST_STMT_LIST list;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_BLOCK(AST_STMT_LIST list)
	{
		this.list = list;
	}
}