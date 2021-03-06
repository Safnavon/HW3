package AST; import IR.*;
import IR.BINOPS;
import src.*;
import src.IRUtils;

import java.util.ArrayList;

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

	
	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		if (SymbolTable.isInCurrentScope(name)) {
			throw new Exception(name + " is already defined in this scope");
		}
		type.isValid(); // such type exists
		if (exp!=null){
			IR_TYPE_WRAPPER expWrapper = exp.isValid();
			if(exp instanceof AST_EXP_NULL){
				if(!type.getClass().equals(AST_TYPE_CLASS.class)) {
					if( !type.getClass().equals(AST_TYPE_TERM.class) || !( ((AST_TYPE_TERM)type).type.equals(TYPES.STRING) ))  {
						throw new Exception("null value can be assigned to strings and class instances only");
					}
				}
			}
			else if(!expWrapper.type.isExtending(type)) {
				throw new Exception("wrong value type assigned to variable '" + name + "'");
			}
		}
		SymbolTable.put(name, type);
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}

	public T_Exp buildIr() {
		T_Exp val;
		if (exp == null) {
			val = new T_Const(0);
		} else {
			val = exp.buildIr();
		}

		int currOffset = IRUtils.getOffset();
		IRUtils.pushVar(name, type, SCOPE_TYPE.LOCAL);

		ArrayList<T_Exp> declareVarSeq = new ArrayList<>();
		T_Binop addr = new T_Binop(BINOPS.PLUS, new T_Temp("$sp"), new T_Const(-4));
		T_Move move_sp = new T_Move(new T_Temp("$sp"), addr);

		if (IRUtils.loopNesting > 0) {
			T_Label dontPushVarLabel = new T_Label("DontPushVar", true);
			T_Temp isFirstTimeInLoop = IRUtils.loopTemporaries.get(IRUtils.loopNesting);
			// don't move $sp if not first time inside loop
			declareVarSeq.add(new T_CJump(new T_Relop(RELOPS.EQUAL, isFirstTimeInLoop, new T_Const(0)), dontPushVarLabel));
			declareVarSeq.add(move_sp);
			declareVarSeq.add(dontPushVarLabel);
		}
		else {
			declareVarSeq.add(move_sp);
		}
		T_Move move_value = new T_Move(new T_Mem(new T_Binop(BINOPS.PLUS, new T_Temp("$fp"), new T_Const(currOffset * (-4)))), val);
		declareVarSeq.add(move_value);
		return new T_Seq(declareVarSeq);
	}
}
