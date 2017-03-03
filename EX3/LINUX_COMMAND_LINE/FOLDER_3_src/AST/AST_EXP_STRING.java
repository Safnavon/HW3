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
		//TODO in the mips simulator, string labels must be prefixed with String_
		computedType = new AST_TYPE_TERM(TYPES.STRING);
		return new IR_TYPE_WRAPPER(computedType, null); //TODO
	}
}
