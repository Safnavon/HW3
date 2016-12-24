package AST;

import src.SymbolTable;

public class AST_METHOD_DECLARE extends AST_CLASS_BODY_ITEM
{
	public AST_TYPE type; // if null if means the return value should be void
	public String name;
	public AST_FORMAL_LIST formals;
	public AST_STMT_LIST stmts;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_METHOD_DECLARE(AST_TYPE type,String name,AST_FORMAL_LIST formals,AST_STMT_LIST exps)
	{
		this.type = type;// if null if means the return value should be void
		this.name = name;
		this.formals = formals;
		this.stmts = exps;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		SymbolTable.openScope();
		ClassChecker.
		return null;
	}
}