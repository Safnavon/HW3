package AST;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_VAR_SIMPLE extends AST_VAR
{
  public String name;

  public AST_VAR_SIMPLE(String name)
  {
    this.name = name;
  }

  public IR_TYPE_WRAPPER isValid() throws Exception {
		AST_TYPE varType = SymbolTable.get(this.name);
		if(varType == null){
			throw new Exception("Cant find symbol "+this.name);
		}
		return new IR_TYPE_WRAPPER(varType, null);//TODO
  }
}
