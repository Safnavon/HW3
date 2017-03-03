package AST;

import src.IR_TYPE_WRAPPER;

public class AST_EXP_NULL extends AST_EXP{

	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		computedType = null;
		return new IR_TYPE_WRAPPER(null, null); //TODO
	}

}
