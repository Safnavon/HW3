package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_FORMAL extends AST_Node
{
	public AST_TYPE type;
	public String name;
	
	public AST_FORMAL(AST_TYPE type, String name){
		this.type = type;
		this.name = name;
	}

	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		if (SymbolTable.isInCurrentScope(name)) {
			throw new Exception("input parameter  " + name + " is already declared");				
		}
		SymbolTable.put(name, type);
		return new IR_TYPE_WRAPPER(null, null); //TODO
	}
}