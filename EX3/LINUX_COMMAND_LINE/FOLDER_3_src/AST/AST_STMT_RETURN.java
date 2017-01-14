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

	@Override
	public AST_TYPE isValid() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		if (exp == null) {
			if (expectedReturnValue != null) {
				throw new Exception("invalid return value");
			}			
		} else {
			IR_TYPE_WRAPPER expType = exp.isValid();
			if (!expType.type.equals(expectedReturnValue)) {
				throw new Exception("invalid return value");
			}
		}
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}
}