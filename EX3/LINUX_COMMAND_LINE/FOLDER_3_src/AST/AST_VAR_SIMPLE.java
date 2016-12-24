package AST;
import src.ClassChecker;
import src.SymbolTable;

public class AST_VAR_SIMPLE extends AST_VAR
{
  public String name;

  public AST_VAR_SIMPLE(String name)
  {
    this.name = name;
  }
  @Override
  public AST_TYPE isValid() throws Exception {
		AST_TYPE varType = SymbolTable.get(this.name);
		if(varType == null){
			throw new Exception("Cant find symbol "+this.name);
		}
		return varType;
  }
}
