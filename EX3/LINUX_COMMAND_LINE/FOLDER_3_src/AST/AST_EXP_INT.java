package AST; import IR.T_Const;
import IR.T_Exp;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
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
	public IR_TYPE_WRAPPER isValid() throws Exception {
		computedType = new AST_TYPE_TERM(TYPES.INT);
		return new IR_TYPE_WRAPPER(computedType, new T_Const(value)) ;
	}

	public T_Exp buildIr(){
		return new T_Const(value);
	}
}