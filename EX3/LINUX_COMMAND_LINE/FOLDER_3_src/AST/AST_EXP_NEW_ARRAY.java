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

        // Allocating the array:
        ArrayList<T_Exp> expList = new ArrayList<>();
        T_Temp locationTemp =new T_Temp();
        T_Temp sizeTemp = (T_Temp)size.buildIr();
        ArrayList<T_Exp> expList2 = new ArrayList<>();
        //expList2.add(sizeTemp);
        T_Binop bin=new T_Binop(BINOPS.PLUS, sizeTemp, new T_Const(1));
        expList2.add( new T_Binop(BINOPS.TIMES,bin,new T_Const(4)));
        T_ESeq size = new T_ESeq(expList2);
        T_Move node = new T_Move(locationTemp, new T_Malloc(size));
        expList.add(node);

        // Putting the size of the array in the first spot.
        node = new T_Move(new T_Mem(new T_Binop(BINOPS.PLUS,locationTemp,new T_Const(0))), sizeTemp);
        expList.add(node);

        // initializing the array:
        T_Temp count = new T_Temp();
        node = new T_Move(count, new T_Const(1));
        expList.add(node);
        T_Label loop = new T_Label("Loop", true);
        T_Label exit = new T_Label("Exit", true);
        T_CJump cJumpExit = new T_CJump(new T_Relop(RELOPS.GT, count, sizeTemp), exit);
        T_JumpLabel jumpLoop = new T_JumpLabel(loop);
        expList.add(loop);
        expList.add(cJumpExit);

        T_Binop address = new T_Binop(BINOPS.PLUS, locationTemp, new T_Binop(BINOPS.PLUS, count, new T_Const(4)));
        node = new T_Move(new T_Mem(address), new T_Const(0));
        expList.add(node);

        node = new T_Move(count, new T_Binop(BINOPS.PLUS, count, new T_Const(1)));
        expList.add(node);

        expList.add(jumpLoop);
        expList.add(exit);

        expList.add(locationTemp);
        return new T_ESeq(expList);

    }

}
