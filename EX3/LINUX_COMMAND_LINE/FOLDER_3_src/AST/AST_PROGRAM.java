package AST;

import java.util.ArrayList;
import java.util.List;

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
        IR_TYPE_WRAPPER firstWrapper = first.isValid();
        IR_TYPE_WRAPPER restWrapper = rest != null ? rest.isValid() : null;
        return new IR_TYPE_WRAPPER(null, null);//TODO
    }

    /**
     * avoids multiple calls to "ensureOneMain"
     */
    public void isValidProgram() throws Exception {
        IR_TYPE_WRAPPER wrapper = this.isValid();
        ClassChecker.ensureOneMain();//throws if bad
        //TODO use ensure main to get label of program entry point
    }

}
