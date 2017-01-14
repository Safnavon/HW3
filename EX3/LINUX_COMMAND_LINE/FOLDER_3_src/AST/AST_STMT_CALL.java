package AST;

import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_CALL extends AST_STMT {
    public AST_METHOD_CALL call;

    /*******************/
    /*  CONSTRUCTOR(S) */

    /*******************/
    public AST_STMT_CALL(AST_METHOD_CALL call) {
        this.call = call;
    }


    public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
        if (call != null) {
            call.isValid();
        }
        return new IR_TYPE_WRAPPER(null,null);//TODO
    }
}