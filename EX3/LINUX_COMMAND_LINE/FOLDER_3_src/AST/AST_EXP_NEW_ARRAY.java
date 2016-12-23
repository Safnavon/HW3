package AST; import SymbolTable; import ClassChecker;

import AST.AST_EXP;


public class AST_EXP_NEW_ARRAY extends AST_EXP {

	public AST_TYPE type;
	public AST_EXP size;
	
	public AST_EXP_NEW_ARRAY(AST_TYPE t, AST_EXP e) {
		this.type = t;
		this.size = e;
	}

}
