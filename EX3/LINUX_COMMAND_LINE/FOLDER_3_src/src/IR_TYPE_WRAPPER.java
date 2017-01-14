package src;

import AST.AST_TYPE;
import IR.T_Exp;

/**
 * Created by nitzanh on 14/01/2017.
 */
public class IR_TYPE_WRAPPER {

    public AST_TYPE type;
    public T_Exp IR;

    public IR_TYPE_WRAPPER(AST_TYPE type, T_Exp IR) {
        this.type = type;
        this.IR = IR;
    }
}
