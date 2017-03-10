package AST; import IR.T_Exp;
import IR.T_Seq;
import src.ClassChecker;
import src.IRUtils;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

import java.util.ArrayList;

public class AST_CLASS_DECLARE extends AST_Node
{
	public String name;
	public String extend;
	public AST_CLASS_BODY body;
	
	public AST_CLASS_DECLARE(String name, String extend, AST_CLASS_BODY body)
	{
		this.name = name;
		this.extend = extend;
		this.body = body;
	}

	public IR_TYPE_WRAPPER isValid() throws Exception {
		
		if(SymbolTable.get(name)!=null){
			throw(new Exception("Duplicate class declaration"));
		}
		if(extend != null && SymbolTable.get(extend) == null) {
			throw new Exception("class " + extend + " has not been declared yet");
		}
		
		SymbolTable.put(name, new AST_TYPE_CLASS(name));
		SymbolTable.openScope();
		ClassChecker.newClass(this);
		if (body != null) { // ic syntax doesn't require a non empty class body
			body.isValid(name);
		}
		SymbolTable.closeScope();
		
		return new IR_TYPE_WRAPPER(new AST_TYPE_CLASS(name), null); //TODO
	}

	public T_Exp buildIr(){
		IRUtils.currentClass = name;
		IRUtils.openScope();

		ArrayList<T_Exp> methods= new ArrayList<T_Exp>();
		for (AST_CLASS_BODY body = this.body; body != null && body.first != null; body = body.rest){
			if(body.first.getClass().equals(AST_FIELD.class)){
				body.first.buildIr();
			}
			else{
				methods.add(body.first.buildIr());
			}
		}
		T_Exp res = null;

		if(methods.size()==1){
			res = methods.get(0);
		}
		else if(methods.size() > 1){
			T_Seq method_seq = new T_Seq(methods.get(0),methods.get(1));
			for (int i=2;i<methods.size();i++){
				method_seq = new T_Seq(method_seq,methods.get(i));
			}
			res = method_seq;
		}

		IRUtils.closeScope();
		return res;
	}
}