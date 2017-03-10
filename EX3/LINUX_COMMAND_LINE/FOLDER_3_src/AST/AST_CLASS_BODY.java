package AST;

import IR.T_Exp;
import src.IR_TYPE_WRAPPER;


public class AST_CLASS_BODY extends AST_Node
{ 
	public AST_CLASS_BODY_ITEM first;

	public AST_CLASS_BODY rest;

	public AST_CLASS_BODY(AST_CLASS_BODY_ITEM first, AST_CLASS_BODY rest){
		
		this.first = first;
		this.rest = rest;
	}

	public IR_TYPE_WRAPPER isValid(String className) throws Exception {
		if (this.first!=null){
			first.className = className;
			first.isValid();
			if ( rest != null ) {
				rest.isValid(className);
			}
		}
		return new IR_TYPE_WRAPPER(null,null);//todo
	}
	
	public IR_TYPE_WRAPPER isValid() throws Exception {
		throw new Error("unimplemented");
	}

	//only CLASS_DECLARE uses CLASS_BODY and buildIR called directly from there.
	public T_Exp buildIr() throws Exception{

		throw (new Error("buildIr is not defined for AST_CLASS_BODY"));
		//return new T_Seq(this.first.buildIr(),this.rest.buildIr());
	}


}
