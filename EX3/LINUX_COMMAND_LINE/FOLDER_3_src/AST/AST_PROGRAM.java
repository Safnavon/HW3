package AST;

import IR.T_Exp;
import IR.T_JumpLabel;
import IR.T_Label;
import IR.T_Seq;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;

public class AST_PROGRAM extends AST_Node {
    public AST_CLASS_DECLARE first;
    public AST_PROGRAM rest;
    private T_Label programMain;

    public AST_PROGRAM(AST_CLASS_DECLARE first, AST_PROGRAM rest) {
        this.first = first;
        this.rest = rest;
    }

    public IR_TYPE_WRAPPER isValid() throws Exception {
        T_Exp firstIR = first.isValid().IR;
        T_Exp restIR = rest != null ? rest.isValid().IR : null;
        return new IR_TYPE_WRAPPER(null, new T_Seq(firstIR, restIR));//TODO
    }

    /**
     * avoids multiple calls to "ensureOneMain"
     * called by Main
     */
    public void isValidProgram() throws Exception {
        first.isValid();
        if (rest != null) {
            rest.isValid();
        }
        programMain = ClassChecker.ensureOneMain();//throws if bad
    }

    public T_Seq buildIr() {
        return new T_Seq(this.first.buildIr(), this.rest != null ? this.rest.buildIr() : null);
    }

    /**
     * called by Main
     *
     * @return IR Node for the entire program
     */
    public T_Seq buildProgram() {
        T_Seq classes = this.buildIr();
        //don't generator vftables here.
        return new T_Seq(
                classes,
                new T_Seq(
                        new T_Label("main"),
                        new T_JumpLabel(this.programMain)
                )
        );
    }

}
