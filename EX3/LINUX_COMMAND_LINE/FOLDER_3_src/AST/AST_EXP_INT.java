package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_EXP_INT extends AST_EXP
{
	public int value;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_INT(int value)
	{
		this.value = value;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		return new AST_TYPE_TERM(TYPES.INT);
	}
}