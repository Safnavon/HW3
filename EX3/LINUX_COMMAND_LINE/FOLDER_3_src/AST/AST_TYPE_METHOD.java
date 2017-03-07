package AST;

import src.IR_TYPE_WRAPPER;

public class AST_TYPE_METHOD extends AST_TYPE {

	AST_METHOD_DECLARE declaration;
	
	public AST_TYPE_METHOD(AST_METHOD_DECLARE declaration) {
		this.declaration = declaration;
	}
	
	@Override
	public boolean isExtending(AST_TYPE other) {
		throw new Error("shouldn't be used");
	}

	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		throw new Error("unimplemented");
	}

}
