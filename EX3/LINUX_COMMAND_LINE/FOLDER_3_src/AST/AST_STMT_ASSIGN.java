package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
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


	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		AST_TYPE varType = var.isValid().type;
		AST_TYPE expType = exp.isValid().type;
		if (!varType.equals(expType)) {
			throw new Exception("cannot assign value of type " + exp.getClass() + " to a variable of type " + varType.getClass());
		}
		return new IR_TYPE_WRAPPER(null, null);
	}
}