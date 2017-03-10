package AST; import IR.T_Exp;
import IR.T_Seq;
import IR.T_Temp;
import src.*;

public class AST_FIELD extends AST_CLASS_BODY_ITEM
{
	public AST_TYPE type;
	public String[] names;
	
	public AST_FIELD(AST_TYPE type, String[] names){
		this.type = type;
		this.names = names;
	}

	public IR_TYPE_WRAPPER isValid() throws Exception {
		type.isValid();
		for(int i=0; i< names.length; i++) {
			if (SymbolTable.isInCurrentScope(names[i])) {
				throw new Exception(names[i] + " is already defined in this scope");				
			}
			SymbolTable.put(names[i], type);
		}
		ClassChecker.addFields(className, this);
		return new IR_TYPE_WRAPPER(null, null); //TODO
	}

	@Override
	public T_Exp buildIr()  {

		for (String name : names) {
				IRUtils.pushVar(name,this.type, SCOPE_TYPE.FIELD);
		}

		return null;
	}
}