package AST;
import IR.*;
import IR.BINOPS;
import src.*;

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

    @Override
    public T_Exp buildIr() {
        Var var = IRUtils.getVar(name);
        switch (var.scope) {
            case FIELD:
                T_Mem m = new T_Mem(new T_Binop(BINOPS.PLUS, new T_Temp("$fp"), new T_Const(0)));
                return new T_Mem(new T_Binop(BINOPS.PLUS, m, new T_Const(var.offset * 4)));
            case PARAMETER:
                return new T_Mem(new T_Binop(BINOPS.PLUS, new T_Temp("$fp"), new T_Const((var.offset) * 4)));
            case LOCAL:
                return new T_Mem(new T_Binop(BINOPS.PLUS, new T_Temp("$fp"), new T_Const(var.offset * (-4))));
            default:
                return null;
        }
    }
}
