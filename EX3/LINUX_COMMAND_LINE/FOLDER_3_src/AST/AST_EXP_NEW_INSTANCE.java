package AST; import AST.AST_EXP;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;


public class AST_EXP_NEW_INSTANCE extends AST_EXP {

	public String className;
	
	public AST_EXP_NEW_INSTANCE(String c) {
		this.className = c;
	}

	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		AST_TYPE type = SymbolTable.get(className);
		if ( type == null || !type.getClass().equals(AST_TYPE_CLASS.class)){
			throw new Exception("cannot create new instace of type " + ((AST_TYPE_CLASS) type).name);
		}
		return new IR_TYPE_WRAPPER(type, null) ;//TODO
	}

}
