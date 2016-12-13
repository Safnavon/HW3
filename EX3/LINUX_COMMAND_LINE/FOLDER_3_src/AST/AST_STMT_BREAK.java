package AST;

public class AST_STMT_BREAK extends AST_STMT
{
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_BREAK(AST_EXP exp)
	{
		this.exp = exp;
	}
}