package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP exp;
	public String fieldName;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_EXP exp,String fieldName)
	{
		this.exp = exp;
		this.fieldName = fieldName;
	}

	public IR_TYPE_WRAPPER isValid() throws Exception {
		AST_TYPE expType = exp.isValid().type;
		if(!(expType instanceof AST_TYPE_CLASS)){
			throw new Exception("Cant access property of non-class type " + expType);
		}
		AST_TYPE_CLASS expClass = (AST_TYPE_CLASS) expType;
		return new IR_TYPE_WRAPPER(ClassChecker.isValidField(expClass.name, this.fieldName), null);
	}

}