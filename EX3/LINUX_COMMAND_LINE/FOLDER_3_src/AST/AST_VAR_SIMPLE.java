package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_VAR_SIMPLE extends AST_VAR
{
	public String name;
	
	public AST_VAR_SIMPLE(String name)
	{
		this.name = name;
	}
}