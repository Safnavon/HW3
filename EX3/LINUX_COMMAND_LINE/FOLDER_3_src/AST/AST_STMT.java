package AST; import IR.T_Exp;
import IR.T_Seq;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public abstract class AST_STMT extends AST_Node
{
	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		throw new Error("isValid(AST_TYPE expectedReturnValue) is not implemented for this type of AST_STMT");
	}

	public T_Exp buildIr() throws Exception{
		throw new Error("AST.AST_STMT.buildIr not implemented for this type of AST_STMT");
	}
}