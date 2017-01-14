package AST; import AST.AST_EXP;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;


public class AST_EXP_STRING extends AST_EXP {
	
	public String value;
	
	public AST_EXP_STRING(String q) {
		this.value = q;
	}
	
	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		return new IR_TYPE_WRAPPER(new AST_TYPE_TERM(TYPES.STRING), null); //TODO
	}
}
