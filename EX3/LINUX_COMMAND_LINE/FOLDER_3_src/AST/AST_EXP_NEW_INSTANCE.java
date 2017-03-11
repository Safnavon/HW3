package AST;

import IR.*;
import IR.BINOPS;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

import java.util.ArrayList;


public class AST_EXP_NEW_INSTANCE extends AST_EXP {

    public String className;

    public AST_EXP_NEW_INSTANCE(String c) {
        this.className = c;
    }

    @Override
    public IR_TYPE_WRAPPER isValid() throws Exception {
        computedType = SymbolTable.get(className);
        if (computedType == null || !computedType.getClass().equals(AST_TYPE_CLASS.class)) {
            throw new Exception("cannot create new instance of type " + className);
        }
        return new IR_TYPE_WRAPPER(computedType, null);//TODO
    }

    public T_ESeq buildIr() {
        ArrayList<T_Exp> exps = new ArrayList<T_Exp>();
        //prepare leaves
        T_Temp result = new T_Temp("new" + this.className, true);
        T_Malloc malloc = new T_Malloc(ClassChecker.sizeOf(this.className));
        T_Label vftLabel = new T_VFTableLabel("VFTable_" + this.className);
        T_Const zero = new T_Const(0);
        //

        exps.add(new T_Move(result, malloc));
        exps.add(new T_Move(new T_Mem(new T_Binop(BINOPS.PLUS, result, zero)), vftLabel));
        for (int i = 1; i < ClassChecker.sizeOf(this.className); i++) {
            exps.add(new T_Move(new T_Mem(new T_Binop(BINOPS.PLUS, result, new T_Const(i * 4))), zero));
        }
        //throw new Error("WIP");
        exps.add(result);
        return new T_ESeq(exps);
    }

}
