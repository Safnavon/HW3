package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_EXP extends AST_Node
{

    public IR_TYPE_WRAPPER isValid() throws Exception {
        return null;
    }
}