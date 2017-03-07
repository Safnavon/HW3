package AST;

import AST.AST_EXP;
import IR.*;
import IR.BINOPS;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;
import java.util.ArrayList;

public class AST_EXP_NEW_ARRAY extends AST_EXP {

    public AST_TYPE type;
    public AST_EXP size;

    public AST_EXP_NEW_ARRAY(AST_TYPE t, AST_EXP e) {
        this.type = t;
        this.size = e;
    }

    @Override
    public IR_TYPE_WRAPPER isValid() throws Exception {
        type.isValid();
        if (!size.isValid().equals(new AST_TYPE_TERM(TYPES.INT))) {
            throw new Exception("invalid array size type");
        }
        computedType = new AST_TYPE_ARRAY(type);
        return new IR_TYPE_WRAPPER(computedType, null); //TODO
    }

    public T_Exp buildIr(){



        ArrayList<T_Exp> resExpList = new ArrayList<>();
        T_Temp locationTemp =new T_Temp();
        T_Temp sizeTemp = (T_Temp)size.buildIr();
        ArrayList<T_Exp> calcSizeExps = new ArrayList<>();

        T_Binop op=new T_Binop(BINOPS.PLUS, sizeTemp, new T_Const(1));
        calcSizeExps.add( new T_Binop(BINOPS.TIMES,op,new T_Const(4)));
        T_ESeq size = new T_ESeq(calcSizeExps);
        T_Move resExp = new T_Move(locationTemp, new T_Malloc(size));
        resExpList.add(resExp);

        resExp = new T_Move(new T_Mem(new T_Binop(BINOPS.PLUS,locationTemp,new T_Const(0))), sizeTemp);
        resExpList.add(resExp);

        T_Temp count = new T_Temp();
        resExp = new T_Move(count, new T_Const(1));
        resExpList.add(resExp);
        T_Label loop = new T_Label("Loop", true);
        T_Label exit = new T_Label("Exit", true);
        T_CJump cJumpExit = new T_CJump(new T_Relop(RELOPS.GT, count, sizeTemp), exit);
        T_JumpLabel jumpLoop = new T_JumpLabel(loop);
        resExpList.add(loop);
        resExpList.add(cJumpExit);

        T_Binop address = new T_Binop(BINOPS.PLUS, locationTemp, new T_Binop(BINOPS.TIMES, count, new T_Const(4)));
        resExp = new T_Move(new T_Mem(address), new T_Const(0));
        resExpList.add(resExp);

        resExp = new T_Move(count, new T_Binop(BINOPS.PLUS, count, new T_Const(1)));
        resExpList.add(resExp);

        resExpList.add(jumpLoop);
        resExpList.add(exit);

        resExpList.add(locationTemp);
        return new T_ESeq(resExpList);

    }

}
