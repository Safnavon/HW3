package AST; import IR.T_Exp;
import IR.T_Mem;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_EXP extends AST_Node
{
    public AST_TYPE computedType;
    public IR_TYPE_WRAPPER isValid() throws Exception {
       throw new Error("unimplemented");
    }

    public T_Mem eval() throws Exception {
        throw new Error("unimplemented");
    }

    public T_Exp buildIr() {
        throw new Error("unimplemented");
    }
}