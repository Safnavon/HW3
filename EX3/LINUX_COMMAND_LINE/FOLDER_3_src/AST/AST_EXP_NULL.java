package AST;

import IR.T_Const;
import src.IR_TYPE_WRAPPER;

public class AST_EXP_NULL extends AST_EXP{

	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		computedType = null;
		return new IR_TYPE_WRAPPER(null, null); //TODO
	}

	public T_Const buildIr(){
		return new T_Const(0);
	}
}
