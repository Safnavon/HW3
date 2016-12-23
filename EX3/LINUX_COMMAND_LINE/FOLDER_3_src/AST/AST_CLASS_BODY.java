package AST;

public class AST_CLASS_BODY extends AST_Node
{
	public AST_CLASS_BODY_ITEM first;
	public AST_CLASS_BODY rest;
	
	public AST_CLASS_BODY(AST_CLASS_BODY_ITEM first, AST_CLASS_BODY rest){
		this.first = first;
		this.rest = rest;
	}

	@Override
	public AST_TYPE isValid() {
		if (this.first!=null){
			first.isValid();
			rest.isValid();
			
		}
		return null;
		
		
	}
	
	
}