package AST; import IR.T_Exp;
import IR.T_Mem;
import IR.T_Move;
import IR.T_Temp;
import src.ClassChecker;
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
		IR_TYPE_WRAPPER varData = var.isValid();
		IR_TYPE_WRAPPER expData = exp.isValid();

		// validate types
		AST_TYPE varType = varData.type;
		AST_TYPE expType = expData.type;

		if (!varType.equals(expType)) {
			throw new Exception("cannot assign value of type " + exp.getClass() + " to a variable of type " + varType.getClass());
		}

		// create IR nodes
		T_Mem varMem = (T_Mem) varData.IR;
		T_Exp expNode = expData.IR;
		T_Move moveNode = new T_Move(expNode, varMem);
		return new IR_TYPE_WRAPPER(null, moveNode);
	}
}