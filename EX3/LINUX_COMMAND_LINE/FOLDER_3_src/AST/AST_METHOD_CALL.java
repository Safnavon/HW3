package AST;

import java.util.ArrayList;
import java.util.LinkedList;

import IR.*;
import IR.BINOPS;
import src.ClassChecker;
import src.IRUtils;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_METHOD_CALL extends AST_Node {
    public AST_VAR var;
    public AST_EXP_LIST exps;

    /*******************/
    /*  CONSTRUCTOR(S) */

    /*******************/
    public AST_METHOD_CALL(AST_VAR var, AST_EXP_LIST exps) {
        this.var = var;
        this.exps = exps;
    }

    public IR_TYPE_WRAPPER isValid() throws Exception {
        LinkedList<AST_EXP> expressions = new LinkedList<AST_EXP>();
        LinkedList<AST_TYPE> types = new LinkedList<AST_TYPE>();
        LinkedList<T_Exp> irs = new LinkedList<T_Exp>();
        AST_FORMAL_LIST d = null;
        AST_FORMAL c;
        AST_TYPE returnType;
        T_Exp instanceAddress;
        T_Exp methodAddress;

        //call isValids of expressions passed to func
        for (AST_EXP_LIST b = exps; b != null; b = b.rest) {
            expressions.add(b.first);
        }
        for (AST_EXP exp : expressions) {
            IR_TYPE_WRAPPER irTypeWrapper = exp.isValid();
            types.add(irTypeWrapper.type);
            irs.add(irTypeWrapper.IR);
        }

        if (this.var.getClass().equals(AST_VAR_SIMPLE.class)) {
            AST_TYPE varType = SymbolTable.get(((AST_VAR_SIMPLE) var).name);
            if (varType == null || !varType.getClass().equals(AST_TYPE_METHOD.class)) {
                throw (new Exception("no method found for name" + this.var.getClass().getName()));
            }
            AST_METHOD_DECLARE m = ((AST_TYPE_METHOD) varType).declaration;
            if (m.formals != null) {
                LinkedList<AST_TYPE> formalTypes = new LinkedList<AST_TYPE>();
                do {
                    c = m.formals.first;
                    assert c != null;
                    assert c.type != null;
                    d = m.formals.rest;
                    formalTypes.add(c.type);
                }
                while (d != null);

                AST_TYPE[] t1 = (AST_TYPE[]) formalTypes.toArray();
                AST_TYPE[] t2 = (AST_TYPE[]) types.toArray();
                if (t1.length != t2.length) {
                    throw (new Exception("illegal number of parameters to method " + m.name));
                }
                for (int i = 0; i < t1.length; i++) {
                    if (!t1[i].isExtending(t2[i])) {
                        throw (new Exception("illegal type of parameters to method " + m.name));
                    }
                }
            } else {
                if (types.size() != 0) {
                    throw (new Exception("illegal number of parameters to method " + m.name));
                }
            }
            returnType = m.type;
            instanceAddress = new T_Arg(0);
        }
        else if (this.var.getClass().equals(AST_VAR_FIELD.class)) {
            AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
            IR_TYPE_WRAPPER varFieldWrapper = varField.exp.isValid();
            AST_TYPE classType = varFieldWrapper.type;
            instanceAddress = varFieldWrapper.IR;
            returnType = ClassChecker.isValidMethod(classType, varField.fieldName, types);
        }
        else {
            throw (new Exception("invalid METHOD_CALL node"));
        }
        return new IR_TYPE_WRAPPER(returnType, null);//TODO

    }

    public T_Exp buildIr() {
        T_Exp thisInst;
        int methodOffset;
        String thisClass, method;

        if (this.var.getClass().equals(AST_VAR_SIMPLE.class)) {
            thisInst = new T_Mem(new T_Binop(BINOPS.PLUS, new T_Temp("$fp"), new T_Const(0)));
            thisClass = IRUtils.currentClass;
            method = ((AST_VAR_SIMPLE) var).name;
        } else if (this.var.getClass().equals(AST_VAR_FIELD.class)) {
            AST_VAR_FIELD thisVar = ((AST_VAR_FIELD) var);
            thisInst = thisVar.exp.buildIr();
            thisClass = ((AST_TYPE_CLASS) thisVar.exp.computedType).name;
            method = thisVar.fieldName;
        } else {
            throw new Error("invalid METHOD_CALL node");
        }
        try {
            methodOffset = ClassChecker.getFunctionOffset(thisClass, method);
        } catch (Exception e) {
            throw new Error("Error: no such method");
        }

        // PROLOGUE
        // get "this" and method address
        T_Temp thisTemp = new T_Temp("for_passing_this", true);
        T_Move putThisInTemp = new T_Move(thisTemp, thisInst);
        T_Exp vfTable = new T_Mem(new T_Binop(BINOPS.PLUS, thisTemp, new T_Const(0)));
        T_Temp methodReg = new T_Temp();
        T_Move getMethodAddr = new T_Move(methodReg, new T_Mem(new T_Binop(BINOPS.PLUS, vfTable, new T_Const(methodOffset))));

        ArrayList<T_Exp> prologueSeq = new ArrayList<T_Exp>();
        T_Exp push_$fp = pushReg("$fp");
        T_Exp push_$ra = pushReg("$ra");
        T_Exp set_$fp = new T_Move(new T_Temp("$fp"), new T_Temp("$sp"));
        prologueSeq.add(push_$fp);
        prologueSeq.add(push_$ra);
        prologueSeq.add(set_$fp);
        T_Exp prologue = new T_Seq(prologueSeq);

        // EPILOGUE
        ArrayList<T_Exp> epilogueSeq = new ArrayList<T_Exp>();
        epilogueSeq.add(popToReg("$ra"));
        epilogueSeq.add(popToReg("$fp"));
        T_Exp epilogue = new T_Seq(prologueSeq);

        // RESULT IR NODE
        ArrayList<T_Exp> seq = new ArrayList<>();
        seq.add(putThisInTemp);
        seq.add(getMethodAddr);
        T_ExpList expList = exps == null ? null : exps.buildIr();
        seq.add(new T_Call(methodReg, expList, thisTemp, prologue, epilogue));
        return new T_ESeq(seq);
    }

    private T_Exp pushReg(String specialReg) {
        T_Exp $SP = new T_Temp("$sp");
        T_Exp decrementSP = new T_Move($SP, new T_Binop(BINOPS.PLUS, new T_Temp("$sp"), new T_Const(-4)));
        T_Exp push = new T_Move(new T_Mem(new T_Binop(BINOPS.PLUS, $SP, new T_Const(0))) ,new T_Temp(specialReg));
        return new T_Seq(decrementSP, push);
    }

    private T_Exp popToReg(String specialReg) {
        T_Exp $SP = new T_Temp("$sp");
        T_Exp pop = new T_Move(new T_Temp(specialReg), new T_Mem(new T_Binop(BINOPS.PLUS, $SP, new T_Const(0))));
        T_Exp incrementSP = new T_Move($SP, new T_Binop(BINOPS.PLUS, $SP, new T_Const(4)));
        return new T_Seq(pop, incrementSP);
    }
}