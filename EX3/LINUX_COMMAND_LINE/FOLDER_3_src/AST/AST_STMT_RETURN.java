package AST; import src.ClassChecker;
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

	public void isValid(AST_TYPE expectedReturnValue) throws Exception {
		if (exp == null) {
			if (expectedReturnValue != null) {
				throw new Exception("invalid return value");
			}			
		} else {
			AST_TYPE expType = exp.isValid();
			if (!expType.equals(expectedReturnValue)) {
				throw new Exception("invalid return value");
			}
		}		
	}
}