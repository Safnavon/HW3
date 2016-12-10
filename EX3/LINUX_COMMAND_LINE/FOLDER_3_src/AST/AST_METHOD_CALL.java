package AST;

public class AST_METHOD_CALL extends AST_Node
{
	public AST_VAR var;
	public AST_EXP_LIST exps;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_METHOD_CALL(AST_VAR var,AST_EXP_LIST exps)
	{
		this.var = var;
		this.exps = exps;
	}
}