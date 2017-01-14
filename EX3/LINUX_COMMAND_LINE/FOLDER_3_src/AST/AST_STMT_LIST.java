package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_STMT_LIST extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_STMT head;
	public AST_STMT_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		head.isValid(expectedReturnValue);
		if(tail != null) {
			tail.isValid(expectedReturnValue);
		}
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}
}