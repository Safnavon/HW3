package AST;

import java.util.LinkedList;
import java.util.ListIterator;
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
	public AST_TYPE isValid() throws Exception {
		LinkedList<AST_EXP> expressions = new LinkedList<AST_EXP>();
		LinkedList<AST_TYPE> types=new LinkedList<AST_TYPE>();
		AST_EXP a;
		AST_FORMAL_LIST d=null;
		AST_FORMAL c;
		
		for(AST_EXP_LIST b=exps; b!= null; b = b.rest) {
			expressions.add(b.first);
		}
		ListIterator expIterator = expressions.listIterator();
		while(expIterator.hasNext()){
			a=(AST_EXP) expIterator.next();
			types.add(a.isValid());
		}
		if(this.var.getClass().equals(AST_VAR_SIMPLE.class) ){
			AST_TYPE varType =SymbolTable.get(((AST_VAR_SIMPLE) var).name);
			if(varType==null || !varType.getClass().equals(AST_TYPE_METHOD.class)){
				throw(new Exception("no method found for name"+this.var.getClass().getName()));
			}
			AST_METHOD_DECLARE m = ((AST_TYPE_METHOD) varType).declaration;
			if (m.formals == null) {
				if (types.size() != 0) {
					throw(new Exception("illegal number of parameters to method "+ m.name));
				} else {
					return m.type;
				}
			} else {
				LinkedList<AST_TYPE> formalTypes=new LinkedList<AST_TYPE>();			
				do{
					c=m.formals.first;
					d=m.formals.rest;
					formalTypes.add(c.type);
				}
				while(d!=null);
				
				AST_TYPE[] t1 = (AST_TYPE[]) formalTypes.toArray();
				AST_TYPE[] t2 = (AST_TYPE[]) types.toArray();
				if ( t1.length != t2.length) {
					throw(new Exception("illegal number of parameters to method "+ m.name));
				}
				for(int i =0; i <t1.length; i++) {
					if (!t1[i].isExtending(t2[i])) { //TODO verify non polymorphism
						throw(new Exception("illegal type of parameters to method "+ m.name));
					}
				}
				return 	m.type;
			}
			
		}//TODO add return values equal to call return value
		
		if(this.var.getClass().equals(AST_VAR_FIELD.class) ){
			AST_VAR_FIELD varField =(AST_VAR_FIELD) var;
			AST_TYPE varType = varField.exp.isValid();
			return ClassChecker.isValidMethod(varType, varField.fieldName, types);
		}
		
		throw(new Exception("invalid METHOD_CALL node"));
	}
}