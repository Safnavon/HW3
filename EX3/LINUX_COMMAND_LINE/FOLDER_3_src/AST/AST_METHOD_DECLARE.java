package AST;

public class AST_METHOD_DECLARE extends AST_CLASS_BODY_ITEM
{
	public TYPE type; // if null if means the return value should be void
	public String name;
	public AST_FORMAL_LIST formals;
	public AST_EXP_LIST exps;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(TYPE type,String name,AST_FORMAL_LIST formals,AST_EXP_LIST exps)
	{
		this.type = type;// if null if means the return value should be void
		this.name = name;
		this.formals = formals;
		this.exps = exps;
	}
}