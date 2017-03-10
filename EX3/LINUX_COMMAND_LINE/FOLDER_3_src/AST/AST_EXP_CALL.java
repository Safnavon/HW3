package AST;

import IR.T_Exp;
import src.IR_TYPE_WRAPPER;

public class AST_EXP_CALL extends AST_EXP {
    public AST_METHOD_CALL call;

    /*******************/
    /*  CONSTRUCTOR(S) */

    /*******************/
    public AST_EXP_CALL(AST_METHOD_CALL call) {
        this.call = call;
    }

    @Override
    public IR_TYPE_WRAPPER isValid() throws Exception {
        IR_TYPE_WRAPPER wrapper = call.isValid();
        computedType = wrapper.type;
        return wrapper;
    }

    @Override
    public T_Exp buildIr() {
        return call.buildIr();
    }
}