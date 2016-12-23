package AST; import SymbolTable; import ClassChecker;

import AST.AST_EXP;


public class AST_EXP_NEW_INSTANCE extends AST_EXP {

	public String className;
	
	public AST_EXP_NEW_INSTANCE(String c) {
		this.className = c;
	}

}
