package AST; import AST.AST_EXP;
import src.ClassChecker;
import src.SymbolTable;


public class AST_EXP_NEW_ARRAY extends AST_EXP {

	public AST_TYPE type;
	public AST_EXP size;
	
	public AST_EXP_NEW_ARRAY(AST_TYPE t, AST_EXP e) {
		this.type = t;
		this.size = e;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		if (!size.isValid().equals(new AST_TYPE_TERM(TYPES.INT))) {
			throw new Exception("invalid array size type");
		}
		return new AST_TYPE_ARRAY(type);				
	}
	
}
