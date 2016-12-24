package AST;

public class AST_TYPE_METHOD extends AST_TYPE {

	AST_METHOD_DECLARE declaration;
	
	public AST_TYPE_METHOD(AST_METHOD_DECLARE declaration) {
		this.declaration = declaration;
	}
	
	@Override
	public boolean isExtending(AST_TYPE other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		// TODO Auto-generated method stub
		return null;
	} 

}
