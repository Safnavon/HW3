package AST; import IR.*;
import IR.BINOPS;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

import java.util.ArrayList;

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

  public T_Exp buildIr() {

    ArrayList<T_Exp> resList = new ArrayList<>();
    T_Temp expTemp = new T_Temp();
    T_Move initExp = new T_Move(expTemp,exp.buildIr());
    resList.add(initExp);

    T_Temp subscriptTemp = new T_Temp();
    T_Move initSubscript = new T_Move(subscriptTemp,subscript.buildIr());
    resList.add(initSubscript);

    T_Label accessViolationLabel = new T_Label("access_violation",false);
    T_CJump nullCheckJump = new T_CJump(new T_Relop(RELOPS.EQUAL,expTemp,new T_Const(0)),accessViolationLabel);
    resList.add(nullCheckJump);

    T_CJump boundsCheckJump = new T_CJump(new T_Relop(RELOPS.GTE,subscriptTemp,new T_Mem(new T_Binop(BINOPS.PLUS,expTemp,new T_Const(0)))),accessViolationLabel);
    resList.add(boundsCheckJump);

    T_Binop offsetOp = new T_Binop(BINOPS.TIMES, new T_Binop(BINOPS.PLUS,subscriptTemp,new T_Const(1)),new T_Const(4));
    T_Binop locationOp =new T_Binop(BINOPS.PLUS,expTemp,offsetOp);


    resList.add(locationOp);

    return new T_Mem(new T_Binop(BINOPS.PLUS,new T_ESeq(resList),new T_Const(0)));
  }
}
