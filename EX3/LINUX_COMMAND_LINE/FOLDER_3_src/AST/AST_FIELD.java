package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_FIELD extends AST_CLASS_BODY_ITEM
{
	public AST_TYPE type;
	public String[] names;
	
	public AST_FIELD(AST_TYPE type, String[] names){
		this.type = type;
		this.names = names;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		for(int i=0; i< names.length; i++) {
			if ( SymbolTable.isInCurrentScope(names[i])) {
				throw new Exception(names[i] + " is already defined in this scope");				
			}
			SymbolTable.put(names[i], type);
		}
		return null;
	}
}