package AST; import SymbolTable; import ClassChecker;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP exp;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_EXP exp,String fieldName)
	{
		this.exp = exp;
		this.fieldName = fieldName;
	}
}