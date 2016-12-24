package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_STMT_ASSIGN extends AST_STMT
{
	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp)
	{
		this.var = var;
		this.exp = exp;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		return null;
	}

	public void isValid(AST_TYPE expectedReturnValue) throws Exception {
		AST_TYPE varType = var.isValid();
		AST_TYPE expType = exp.isValid();
		if (!varType.equals(expType)) {
			throw new Exception("cannot assign value of type " + exp.getClass() + " to a variable of type " + varType.getClass());
		}
	}
}