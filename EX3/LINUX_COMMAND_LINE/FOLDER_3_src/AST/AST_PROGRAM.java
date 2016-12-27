package AST;
import java.util.ArrayList;
import java.util.List;

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
		ClassChecker.ensureOneMain();//throws if bad
		return null;
	}
}
