package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP exp)
	{
		this.exp = exp;
	}
}