package AST;

import AST.AST_EXP;
import IR.T_Temp;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;


public class AST_EXP_NEW_ARRAY extends AST_EXP {

    public AST_TYPE type;
    public AST_EXP size;

    public AST_EXP_NEW_ARRAY(AST_TYPE t, AST_EXP e) {
        this.type = t;
        this.size = e;
    }

    @Override
    public IR_TYPE_WRAPPER isValid() throws Exception {
        type.isValid();
        if (!size.isValid().equals(new AST_TYPE_TERM(TYPES.INT))) {
            throw new Exception("invalid array size type");
        }
        computedType = new AST_TYPE_ARRAY(type);
        return new IR_TYPE_WRAPPER(computedType, null); //TODO
    }


}
