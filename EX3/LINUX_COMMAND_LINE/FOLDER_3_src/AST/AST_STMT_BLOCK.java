package AST; import IR.T_Exp;
import IR.T_Seq;
import src.ClassChecker;
import src.IRUtils;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_BLOCK extends AST_STMT
{
	public AST_STMT_LIST stmts;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_BLOCK(AST_STMT_LIST stmts)
	{
		this.stmts = stmts;
	}

	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		SymbolTable.openScope();
        T_Exp ir = stmts.isValid(expectedReturnValue).IR;
        SymbolTable.closeScope();
		return new IR_TYPE_WRAPPER(null,ir);
	}

	public T_Seq buildIr(){
		IRUtils.openScope();
		T_Seq seq = stmts.buildIr();
		IRUtils.closeScope();
		return seq;
	}

}