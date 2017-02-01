package AST;

import java.util.ArrayList;
import java.util.List;

import IR.T_Exp;
import IR.T_Label;
import IR.T_Seq;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_PROGRAM extends AST_Node {
    public AST_CLASS_DECLARE first;
    public AST_PROGRAM rest;

    public AST_PROGRAM(AST_CLASS_DECLARE first, AST_PROGRAM rest) {
        this.first = first;
        this.rest = rest;
    }

    public IR_TYPE_WRAPPER isValid() throws Exception {
        T_Exp firstIR = first.isValid().IR;
        T_Exp restIR = rest != null ? rest.isValid().IR : null;
        return new IR_TYPE_WRAPPER(null, new T_Seq(firstIR,restIR));//TODO
    }

    /**
     * avoids multiple calls to "ensureOneMain"
     * called by Main
     */
    public void isValidProgram() throws Exception {
        T_Seq classes = (T_Seq) this.isValid().IR;
        T_Label mainLabel = ClassChecker.ensureOneMain();//throws if bad
        classes.gen();
        //TODO create label main and jump to our actual main
    }

}
