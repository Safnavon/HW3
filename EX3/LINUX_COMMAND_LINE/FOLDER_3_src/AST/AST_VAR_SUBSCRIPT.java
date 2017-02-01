package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
  public AST_EXP exp;
  public AST_EXP subscript;

  /******************/
  /* CONSTRUCTOR(S) */
  /******************/
  public AST_VAR_SUBSCRIPT(AST_EXP e1,AST_EXP subscript)
  {
    this.exp = e1;
    this.subscript = subscript;
  }

  public IR_TYPE_WRAPPER isValid() throws Exception {
		//lhs
    AST_TYPE expType = exp.isValid().type;
    if(!(expType instanceof AST_TYPE_ARRAY)) {
      throw new Exception("Trying to access non-array "+expType+" with subscript");
    }
    AST_TYPE indexType = subscript.isValid().type;
    if(!(indexType instanceof AST_TYPE_TERM) ||
       !((AST_TYPE_TERM)indexType).type.equals(TYPES.INT)) {
      throw new Exception("Trying to access array with non integer " + indexType);
    }
    AST_TYPE_ARRAY res = (AST_TYPE_ARRAY)expType;
    return new IR_TYPE_WRAPPER(res.type, null);//TODO
  }
}
