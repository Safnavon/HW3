package AST;

import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_CLASS_BODY_ITEM extends AST_Node {
	String className;

	public IR_TYPE_WRAPPER isValid() throws Exception {
		throw (new Exception("isValid is not defined for AST_CLASS_BODY_ITEM"));
		
	}
}