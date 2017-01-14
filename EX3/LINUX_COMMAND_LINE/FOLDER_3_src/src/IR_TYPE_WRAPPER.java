package src;

import AST.AST_TYPE;
import IR.T_Exp;

/**
 * Created by nitzanh on 14/01/2017.
 */
public class IR_TYPE_WRAPPER {

    AST_TYPE type;
    T_Exp IR;

    IR_TYPE_WRAPPER(AST_TYPE type, T_Exp IR) {
        type = type;
        IR = IR;
    }
}
