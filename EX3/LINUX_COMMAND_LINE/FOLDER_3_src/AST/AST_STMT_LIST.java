package AST;

import IR.T_Exp;
import IR.T_Seq;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_LIST extends AST_STMT {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_STMT head;
    public AST_STMT_LIST tail;

    /******************/
	/* CONSTRUCTOR(S) */

    /******************/
    public AST_STMT_LIST(AST_STMT head, AST_STMT_LIST tail) {
        this.head = head;
        this.tail = tail;
    }

    public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
        T_Exp headIr = head.isValid(expectedReturnValue).IR;
        T_Exp tailIr = tail != null ? tail.isValid(expectedReturnValue).IR : null;
        T_Seq seq = new T_Seq(headIr,tailIr);
        return new IR_TYPE_WRAPPER(null, seq);
    }

    public T_Seq buildIr(){
        return new T_Seq(
                head.buildIr(),
                tail == null ? null : tail.buildIr()
        );
    }
}