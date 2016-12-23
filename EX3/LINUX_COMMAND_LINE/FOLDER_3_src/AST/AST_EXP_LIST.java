package AST;

import src.ClassChecker;
import src.SymbolTable;

public class AST_EXP_LIST extends AST_Node {
	public AST_EXP first;
	public AST_EXP_LIST rest;

	public AST_EXP_LIST(AST_EXP first, AST_EXP_LIST rest) {
		this.first = first;
		this.rest = rest;
	}
}