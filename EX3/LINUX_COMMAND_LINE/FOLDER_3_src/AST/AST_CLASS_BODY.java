package AST;

public class AST_CLASS_BODY extends AST_Node
{
	public AST_CLASS_BODY_ITEM first;

	public AST_CLASS_BODY rest;

	public AST_CLASS_BODY(AST_CLASS_BODY_ITEM first, AST_CLASS_BODY rest){
		
		this.first = first;
		this.rest = rest;
	}

	public AST_TYPE isValid(String className) throws Exception {
		if (this.first!=null){
			first.className = className;
			first.isValid();
			if ( rest != null ) {
				rest.isValid();
			}
		}
		return null;
	}
	
	@Override
	public AST_TYPE isValid() throws Exception {
		return null;
	}


}
