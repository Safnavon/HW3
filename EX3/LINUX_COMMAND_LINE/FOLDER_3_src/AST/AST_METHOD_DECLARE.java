package AST;

import IR.*;
import IR.BINOPS;
import src.*;

import java.util.ArrayList;

public class AST_METHOD_DECLARE extends AST_CLASS_BODY_ITEM
{
	public AST_TYPE type; // if null if means the return value should be void
	public String name;
	public AST_FORMAL_LIST formals;
	public AST_STMT_LIST stmts;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_METHOD_DECLARE(AST_TYPE type,String name,AST_FORMAL_LIST formals,AST_STMT_LIST exps)
	{
		this.type = type;// if null if means the return value should be void
		this.name = name;
		this.formals = formals;
		this.stmts = exps;
	}


	public IR_TYPE_WRAPPER isValid() throws Exception {
		if (type != null ) {
			type.isValid();
		}
		ClassChecker.addFunction(className, this);
//		if (SymbolTable.isInCurrentScope(name)) {
//			throw new Exception(name + " is already defined in this scope");
//		}
		SymbolTable.put(name, new AST_TYPE_METHOD(this));
		SymbolTable.openScope();	// do not swap these lines
		if(formals!=null)formals.isValid();			// do not swap these lines
		if(stmts!=null) stmts.isValid(type);
		SymbolTable.closeScope();
		return new IR_TYPE_WRAPPER(new AST_TYPE_METHOD(this), null);//TODO
	}

	@Override
	public T_Exp buildIr() {
		IRUtils.resetOffset();
		IRUtils.isInMain = name.equals("main");
		T_Label funcLable = null;
		try {
			funcLable = ClassChecker.get(className).getFunctionByName(name).funcLabel;
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
		IRUtils.openScope();

		// add formals to scope
		int offset = 1;
		if (formals != null) {
			ArrayList<AST_FORMAL> formalList = formals.toArrayList();
			for (AST_FORMAL formal : formalList) {
				IRUtils.pushVar(formal.name, formal.type, SCOPE_TYPE.PARAMETER, offset);
				offset++;
			}
		}

		T_Exp body = null;
		if (stmts != null) {
			body = stmts.buildIr();
		}
		T_Exp exit = name.equals("main") ? new T_Exit() : new T_JumpRegister(new T_Temp("$ra"));

		IRUtils.closeScope();

		return new T_Function(body, funcLable, exit);

	}


}