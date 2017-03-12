package AST; import IR.*;
import src.ClassChecker;
import src.IRUtils;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

import java.util.ArrayList;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT body)
	{
		this.cond = cond;
		this.body = body;
	}
	
	public IR_TYPE_WRAPPER isValid(AST_TYPE expectedReturnValue) throws Exception {
		if (! cond.isValid().type.equals(new AST_TYPE_TERM(TYPES.INT))) {
			throw new Exception("while statement condition must be an integer");
		}
		SymbolTable.openScope();
		body.isValid(expectedReturnValue);
		SymbolTable.closeScope();
		return new IR_TYPE_WRAPPER(null,null);//TODO
	}

	@Override
	public T_Exp buildIr() {
		IRUtils.loopNesting++;
		T_Exp expVal = cond.buildIr();
		T_Label labelStart = new T_Label("StartWhile",true);
		T_Label labelEnd = new T_Label("EndWhile",true);
		T_Temp isFirstLoop  = new T_Temp();
		IRUtils.loopTemporaries.put(IRUtils.loopNesting, isFirstLoop);
		T_Move setIsFirstLoop = new T_Move(isFirstLoop, new T_Const(1));
		T_Move setNotFirstLoop = new T_Move(isFirstLoop, new T_Const(0));
		T_Exp bodyIr = body.buildIr();
		T_JumpLabel jumpStart = new T_JumpLabel(labelStart);
		T_CJump jumpEnd = new T_CJump(new T_Relop(RELOPS.EQUAL,expVal,new T_Const(0)), labelEnd);
		IRUtils.loopNesting++;

		ArrayList<T_Exp> seq = new ArrayList<>();
		seq.add(setIsFirstLoop);
		seq.add(labelStart);
		seq.add(jumpEnd);
		seq.add(bodyIr);
		seq.add(jumpStart);
		seq.add(labelEnd);
		seq.add(setNotFirstLoop);
		return new T_Seq(seq);
	}
}


