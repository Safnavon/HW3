package AST;

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
        T_Exp vfTable = new T_Mem(new T_Binop(BINOPS.PLUS, thisInst, new T_Const(0)));
        T_Temp methodReg = new T_Temp();
        T_Move moveAddress = new T_Move(methodReg, new T_Mem(new T_Binop(BINOPS.PLUS, vfTable, new T_Const(methodOffset))));

        T_ExpList expList = (T_ExpList) exps.buildIr();

        return new T_Seq(moveAddress, new T_Call(methodReg, expList));
    }
}