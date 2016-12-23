package AST; import SymbolTable; import ClassChecker;

public class AST_STMT_CALL extends AST_STMT
{
	public AST_METHOD_CALL call;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_CALL(AST_METHOD_CALL call)
	{
		this.call = call;
	}
}