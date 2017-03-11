package AST;

import IR.*;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_EXP_BINOP extends AST_EXP {
    BINOPS OP;
    public AST_EXP left;
    public AST_EXP right;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_EXP_BINOP(AST_EXP left, AST_EXP right, BINOPS op) {
        this.left = left;
        this.right = right;
        this.OP = op;
    }

    private IR_TYPE_WRAPPER validateClassTypes(AST_TYPE leftClass, AST_TYPE rightClass) throws Exception {
        if(leftClass == null || rightClass == null){
            AST_TYPE maybeNull = rightClass != null ? rightClass : leftClass;
            if(maybeNull == null){
                return new IR_TYPE_WRAPPER(null,null);
            }
            else{
                if(maybeNull instanceof AST_TYPE_CLASS){
                    return new IR_TYPE_WRAPPER(maybeNull,null);
                }else{
                    throw new Exception("Tried to compare null with type "+maybeNull+", but expected class or null");
                }
            }
        }
        AST_TYPE_CLASS l, r;
        if (!(leftClass instanceof AST_TYPE_CLASS) || !(rightClass instanceof AST_TYPE_CLASS)) {
            throw new Exception("Expected both sides of op to be classes");
        }
        l = (AST_TYPE_CLASS) leftClass;
        r = (AST_TYPE_CLASS) rightClass;
        if (l.isExtending(r) || r.isExtending(l)) {
            computedType = new AST_TYPE_TERM(TYPES.INT);
            return new IR_TYPE_WRAPPER(computedType, null);
        } else {
            throw new Exception("Tried to compare type " + l.name + " with type " + r.name);
        }
    }

    @Override
    public IR_TYPE_WRAPPER isValid() throws Exception {
        AST_TYPE leftType = left.isValid().type;
        AST_TYPE rightType = right.isValid().type;
        AST_TYPE_TERM leftTypeTerm;
        AST_TYPE_TERM rightTypeTerm;

        if (leftType != null && leftType.getClass().equals(AST_TYPE_TERM.class))
            leftTypeTerm = (AST_TYPE_TERM) leftType;
        else {
            //computedType implemented inside
            return validateClassTypes(leftType, rightType); //TODO
        }
        if (rightType!= null && rightType.getClass().equals(AST_TYPE_TERM.class))
            rightTypeTerm = (AST_TYPE_TERM) rightType;
        else {
            throw (new Exception("right expression is not term"));

        }
        if (!leftTypeTerm.type.equals(rightTypeTerm.type)) {
            throw (new Exception("term types arent equal"));
        }
        if (!(leftTypeTerm.type.equals(TYPES.INT))) {
            if (!leftTypeTerm.type.equals(TYPES.STRING)) {
                throw (new Exception("term types arent int or string"));

            }
            if (!(OP.equals(BINOPS.EQUAL) || OP.equals(BINOPS.PLUS))) {

                throw (new Exception("only PLUS or EQUAL operands are supported for type string"));
            } else {
                computedType = new AST_TYPE_TERM(TYPES.INT);
                return new IR_TYPE_WRAPPER(computedType, null); //TODO
            }
        }
        computedType = leftTypeTerm;
        return new IR_TYPE_WRAPPER(leftTypeTerm, null); //TODO
    }

    public T_Exp buildIr() {
        if (left.computedType instanceof AST_TYPE_TERM) {
            AST_TYPE_TERM termLeft = (AST_TYPE_TERM) left.computedType;
            if (OP == BINOPS.PLUS && termLeft.type == TYPES.STRING) {
                return new T_Concat((T_Temp) left.buildIr(), (T_Temp) right.buildIr());
            }
        }
        if (OP == BINOPS.PLUS) {
            return new T_Binop(IR.BINOPS.PLUS, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.MINUS) {
            return new T_Binop(IR.BINOPS.MINUS, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.DIVIDE) {
            return new T_Binop(IR.BINOPS.DIVIDE, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.TIMES) {
            return new T_Binop(IR.BINOPS.TIMES, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.EQUAL) {
            return new T_Relop(RELOPS.EQUAL, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.NEQUAL) {
            return new T_Relop(RELOPS.NEQUAL, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.GT) {
            return new T_Relop(RELOPS.GT, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.GTE) {
            return new T_Relop(RELOPS.GTE, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.LT) {
            return new T_Relop(RELOPS.LT, left.buildIr(), right.buildIr());
        } else if (OP == BINOPS.LTE) {
            return new T_Relop(RELOPS.LTE, left.buildIr(), right.buildIr());
        }
        throw new Error(String.format("failed building AST_BINOP: %s %s %s",
                left.computedType.getClass(),
                OP,
                right.computedType.getClass()));
    }
}
