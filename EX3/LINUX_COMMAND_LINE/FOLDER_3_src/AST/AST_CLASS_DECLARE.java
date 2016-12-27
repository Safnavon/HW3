package AST; import src.ClassChecker;
import src.SymbolTable;

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
	public AST_TYPE isValid() throws Exception {
		
		if(SymbolTable.get(name)!=null){
			throw(new Exception("Duplicate class declaration"));
		}
		if(extend != null && SymbolTable.get(extend) == null) {
			throw new Exception("class " + extend + " has not been declared yet");
		}
		
		SymbolTable.put(name, new AST_TYPE_CLASS(name));
		SymbolTable.openScope();
		ClassChecker.newClass(this);
		body.isValid(name);
		SymbolTable.closeScope();
		
		return new AST_TYPE_CLASS(name);
	}
}