package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_EXP_VAR extends AST_EXP
{
	public AST_VAR var;
	
	public AST_EXP_VAR(AST_VAR var)
	{
		this.var = var;
	}
	
	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		return var.isValid();
	}
}