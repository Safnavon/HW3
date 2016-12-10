package AST;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_EXP exp;
	public AST_EXP subscript;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(AST_EXP e1,AST_EXP subscript)
	{
		this.exp = e1;
		this.subscript = subscript;
	}
}