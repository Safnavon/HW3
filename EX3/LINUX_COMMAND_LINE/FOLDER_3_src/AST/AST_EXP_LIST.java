package AST;

import IR.T_ExpList;
import IR.T_Seq;
import src.IR_TYPE_WRAPPER;

public class AST_EXP_LIST extends AST_Node {
	public AST_EXP first;
	public AST_EXP_LIST rest;

	public AST_EXP_LIST(AST_EXP first, AST_EXP_LIST rest) {
		this.first = first;
		this.rest = rest;
	}

	public IR_TYPE_WRAPPER isValid() throws Exception {
		IR_TYPE_WRAPPER firstIR=first.isValid();
		IR_TYPE_WRAPPER restIR=rest.isValid();


		return new IR_TYPE_WRAPPER(null, new T_ExpList(firstIR.IR,(T_ExpList) restIR.IR));
	}

	public T_Seq buildIr(){
		return new T_Seq(first.buildIr(),rest != null ? rest.buildIr() : null);
	}
}