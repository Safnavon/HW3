package AST;

import IR.T_Exp;
import IR.T_Seq;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_CLASS_BODY_ITEM extends AST_Node {
	String className;

	public IR_TYPE_WRAPPER isValid() throws Exception {
		throw (new Error("isValid is not defined for AST_CLASS_BODY_ITEM"));
		
	}

	public T_Exp buildIr()throws Exception {
		throw (new Error("isValid is not defined for AST_CLASS_BODY_ITEM"));

	}
}