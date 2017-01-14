package AST;

import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_EXP_LIST extends AST_Node {
	public AST_EXP first;
	public AST_EXP_LIST rest;

	public AST_EXP_LIST(AST_EXP first, AST_EXP_LIST rest) {
		this.first = first;
		this.rest = rest;
	}

	public IR_TYPE_WRAPPER isValid() throws Exception {
		first.isValid();
		rest.isValid();
		return new IR_TYPE_WRAPPER(null, null); //TODO
	}
}