package AST;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import src.ClassChecker;
import src.SymbolTable;

public class AST_METHOD_CALL extends AST_Node
{
	public AST_VAR var;
	public AST_EXP_LIST exps;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_METHOD_CALL(AST_VAR var,AST_EXP_LIST exps)
	{
		this.var = var;
		this.exps = exps;
	}

	@Override
	public AST_TYPE isValid() {
		LinkedList<AST_EXP> expressions = new LinkedList<AST_EXP>();
		LinkedList<AST_TYPE> types=new LinkedList<AST_TYPE>();
		AST_EXP_LIST b=null;
		AST_EXP a;
		
		do{
			a=exps.first;
			b=exps.rest;
			expressions.add(a);
		}

		while(b!=null);
		
		while((a=expressions.iterator().next())!=null){
			types.add(a.isValid());
		}
		if(this.var.getClass().equals(AST_VAR_SIMPLE.class) ){
			AST_TYPE x =SymbolTable.get(var.getClass().getName());
			if(x==null || x.getClass()!=AST_TYPE_METHOD.class){
				throw(new Exception("no method found for name"+this.var.getClass().getName()));
			}
			AST_METHOD_DECLARE m = ((AST_TYPE_METHOD) x).declaration;
			
			do{
				a=declaration.formals.first;
				b=exps.rest;
				expressions.add(a);
			}

			while(b!=null);
			
			AST_TYPE c;	
			while((c =types.iterator().next())!=null){
				if (!c.getClass().equals(m.declaration.formals))
			}
			return 	m.declaration.type;
		}//TODO add return values equal to call return value
		
		if(this.var.getClass().equals(AST_VAR_FIELD.class) ){
			AST_VAR_FIELD varField =(AST_VAR_FIELD) var;
			return ClassChecker.isValidMethod(varField.fieldName,varField);
		}
		
		return null;
	}
}