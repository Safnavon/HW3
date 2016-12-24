package AST; import AST.AST_EXP;
import src.ClassChecker;
import src.SymbolTable;


public class AST_EXP_STRING extends AST_EXP {
	
	public String value;
	
	public AST_EXP_STRING(String q) {
		this.value = q;
	}
	
	@Override
	public AST_TYPE isValid() throws Exception {
		return new AST_TYPE_TERM(TYPES.STRING);
	}
}
