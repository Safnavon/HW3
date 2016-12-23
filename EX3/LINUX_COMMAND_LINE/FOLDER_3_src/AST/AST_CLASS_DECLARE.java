package AST; import SymbolTable; import ClassChecker;

public class AST_CLASS_DECLARE extends AST_Node
{
	public String name;
	public String extend;
	public AST_CLASS_BODY body;
	
	public AST_CLASS_DECLARE(String name, String extend, AST_CLASS_BODY body)
	{
		this.name = name;
		this.extend = extend;
		this.body = body;
	}

	@Override
	public AST_TYPE isValid() {
		

		body.isValid();
		
		return null;
	}
}