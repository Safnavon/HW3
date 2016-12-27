package AST;
import src.ClassChecker;
import src.SymbolTable;

public class AST_PROGRAM extends AST_Node
{
	public AST_CLASS_DECLARE first;
	public AST_PROGRAM rest;

	public AST_PROGRAM(AST_CLASS_DECLARE first, AST_PROGRAM	 rest){
		this.first = first;
		this.rest = rest;
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		first.isValid();
		if(rest!= null) rest.isValid();
		List<AST_TYPE_ARRAY> args = new ArrayList<AST_TYPE_ARRAY>();
		args.add(new AST_TYPE_ARRAY(new AST_TYPE_TERM(TYPES.STRING)));
		AST_TYPE_CLASS mainClass = new AST_TYPE_CLASS("Main");
		return ClassChecker.isValidMethod(mainClass, "main", args);
	}
}
