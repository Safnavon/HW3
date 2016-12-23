package AST; import SymbolTable; import ClassChecker;

public class AST_STMT_DECLARE extends AST_STMT
{
	public AST_TYPE type;
	public String name;
	public AST_EXP exp;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_DECLARE(AST_TYPE type, String name, AST_EXP exp)
	{
		this.type = type;
		this.name = name;
		this.exp = exp;
	}
}


int x; x = a.foo();
y=8;
z= x+y;