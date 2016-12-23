package AST;

public class AST_EXP_BINOP extends AST_EXP
{
	BINOPS OP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,BINOPS op)
	{
		this.left = left;
		this.right = right;
		this.OP = op;
	}

	@Override
	public AST_TYPE isValid() {
		left.isValid();
		right.isValid();
		return null;
	}
}