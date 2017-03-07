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
		if (SymbolTable.isInCurrentScope(name)) {
			throw new Exception(name + " is already defined in this scope");				
		}
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
		T_Label funcLable = new T_Label(IRUtils.currentClass + "_" + name, true);
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


		T_Exp prologue, epilogue;
		T_Exp body = null;

		// prologue
		ArrayList<T_Exp> prologueSeq = new ArrayList<T_Exp>();
		T_Exp push_$fp = pushReg("$fp");
		T_Exp push_$ra = pushReg("$ra");
		T_Exp set_$fp = new T_Move(new T_Temp("$fp"), new T_Temp("$sp"));
			// TODO- save important registers
		prologueSeq.add(push_$fp);
		prologueSeq.add(push_$ra);
		prologueSeq.add(set_$fp);
		prologue = new T_Seq(prologueSeq);

		// method body
		if (stmts != null) {
			body = stmts.buildIr();
		}

		// epilogue
		ArrayList<T_Exp> epilogueSeq = new ArrayList<T_Exp>();
		epilogueSeq.add(popToReg("$ra"));
		epilogueSeq.add(popToReg("$fp"));
		T_Exp exit = name.equals("main") ? new T_Exit() : new T_JumpRegister(new T_Temp("$ra"));
		epilogueSeq.add(exit);
		epilogue = new T_Seq(prologueSeq);

		IRUtils.closeScope();

		return new T_Function(prologue, epilogue, body, funcLable);

	}

	private T_Exp pushReg(String specialReg) {
		T_Exp $SP = new T_Temp("$sp");
		T_Exp decrementSP = new T_Move($SP, new T_Binop(BINOPS.PLUS, new T_Temp("$sp"), new T_Const(-4)));
		T_Exp push = new T_Move(new T_Mem(new T_Binop(BINOPS.PLUS, $SP, new T_Const(0))) ,new T_Temp(specialReg));
		return new T_Seq(decrementSP, push);
	}

	private T_Exp popToReg(String specialReg) {
		T_Exp $SP = new T_Temp("$sp");
		T_Exp pop = new T_Move(new T_Temp(specialReg), new T_Mem(new T_Binop(BINOPS.PLUS, $SP, new T_Const(0))));
		T_Exp incrementSP = new T_Move($SP, new T_Binop(BINOPS.PLUS, $SP, new T_Const(4)));
		return new T_Seq(pop, incrementSP);
	}
}