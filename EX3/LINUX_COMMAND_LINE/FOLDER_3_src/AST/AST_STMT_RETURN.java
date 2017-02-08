package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP exp)
	{
		this.exp = exp;
	}

	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		if (exp == null) {
			if (expectedReturnValue != null) {
				throw new Exception("invalid return value");
			}			
		} else if(exp instanceof AST_EXP_NULL) {
			if (!expectedReturnValue.getClass().equals(AST_TYPE_CLASS.class)) {
				if (!expectedReturnValue.getClass().equals(AST_TYPE_TERM.class) || !(((AST_TYPE_TERM) expectedReturnValue).type.equals(TYPES.STRING))) {
					throw new Exception("invalid return value");
				}
			}
		} else {
			IR_TYPE_WRAPPER validData = exp.isValid();
			AST_TYPE expType = validData.type;
			if (!expType.isExtending(expectedReturnValue)) {
				throw new Exception("invalid return value");
			}
		}
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}
}