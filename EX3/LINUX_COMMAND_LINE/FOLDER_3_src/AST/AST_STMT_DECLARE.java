package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_DECLARE extends AST_STMT
{
	public AST_TYPE type;
	public String name;
	public AST_EXP exp;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_DECLARE(AST_TYPE type, String name, AST_EXP exp)
	{
		this.type = type;
		this.name = name;
		this.exp = exp;
	}

	
	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		if (SymbolTable.isInCurrentScope(name)) {
			throw new Exception(name + " is already defined in this scope");
		}
		type.isValid(); // such type exists
		if (exp!=null){
			IR_TYPE_WRAPPER expWrapper = exp.isValid();
			if(exp instanceof AST_EXP_NULL){
				if(!type.getClass().equals(AST_TYPE_CLASS.class)) {
					if( !type.getClass().equals(AST_TYPE_TERM.class) || !( ((AST_TYPE_TERM)type).type.equals(TYPES.STRING) ))  {
						throw new Exception("null value can be assigned to strings and class instances only");
					}
				}
			}
			else if(!type.isExtending(expWrapper.type)) {
				throw new Exception("wrong value type assigned to variable '" + name + "'");
			}
		}
		SymbolTable.put(name, type);
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}
}
