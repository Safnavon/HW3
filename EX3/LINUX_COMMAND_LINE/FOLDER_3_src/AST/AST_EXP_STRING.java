package AST; import AST.AST_EXP;
import IR.*;
import IR.BINOPS;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

import java.util.ArrayList;


public class AST_EXP_STRING extends AST_EXP {
	
	public String value;
	
	public AST_EXP_STRING(String q) {
		this.value = q;
	}
	
	@Override
	public IR_TYPE_WRAPPER isValid() throws Exception {
		//TODO in the mips simulator, string labels must be prefixed with String_
		computedType = new AST_TYPE_TERM(TYPES.STRING);
		return new IR_TYPE_WRAPPER(computedType, null); //TODO
	}

	public T_Exp buildIr(){

		 AST_TYPE type = new AST_TYPE_TERM(TYPES.INT);
		 AST_EXP size = new AST_EXP_INT(value.length());
		ArrayList<T_Exp> resExpList = new ArrayList<>();
		T_Temp locationTemp =new T_Temp();
		T_Temp sizeTemp = (T_Temp)size.buildIr();
		ArrayList<T_Exp> calcSizeExps = new ArrayList<>();

		T_Binop op=new T_Binop(IR.BINOPS.PLUS, sizeTemp, new T_Const(1));
		calcSizeExps.add( new T_Binop(IR.BINOPS.TIMES,op,new T_Const(4)));
		T_ESeq sizeEseq = new T_ESeq(calcSizeExps);
		T_Move resExp = new T_Move(locationTemp, new T_Malloc(sizeEseq));
		resExpList.add(resExp);

		resExp = new T_Move(new T_Mem(new T_Binop(IR.BINOPS.PLUS,locationTemp,new T_Const(0))), sizeTemp);
		resExpList.add(resExp);

		T_Temp count = new T_Temp();
		resExp = new T_Move(count, new T_Const(1));
		resExpList.add(resExp);
		T_Label loop = new T_Label("Loop", true);
		T_Label exit = new T_Label("Exit", true);
		T_CJump cJumpExit = new T_CJump(new T_Relop(RELOPS.GT, count, sizeTemp), exit);
		T_JumpLabel jumpLoop = new T_JumpLabel(loop);
		resExpList.add(loop);
		resExpList.add(cJumpExit);

		T_Binop address = new T_Binop(IR.BINOPS.PLUS, locationTemp, new T_Binop(IR.BINOPS.PLUS, count, new T_Const(4)));
		resExp = new T_Move(new T_Mem(address), new T_Const(0));
		resExpList.add(resExp);

		resExp = new T_Move(count, new T_Binop(BINOPS.PLUS, count, new T_Const(1)));
		resExpList.add(resExp);

		resExpList.add(jumpLoop);
		resExpList.add(exit);

		resExpList.add(locationTemp);

		for (int i=0 ; i<value.length();i++ ) {
			int offset = 4*(i+1);
			resExpList.add(new T_Move(new T_Binop(BINOPS.PLUS,locationTemp,new T_Const(offset)),new T_Const(value.charAt(i))));
		}
		return new T_ESeq(resExpList);


	}


}
