package AST;

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
}