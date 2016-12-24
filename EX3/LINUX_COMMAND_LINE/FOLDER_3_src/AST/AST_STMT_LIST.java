package AST; import src.ClassChecker;
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

	@Override
	public AST_TYPE isValid() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void isValid(AST_TYPE expectedReturnValue) throws Exception {
		head.isValid(expectedReturnValue);
		if(tail != null) {
			tail.isValid(expectedReturnValue);
		}
	}
}