package AST; import IR.*;
import IR.BINOPS;
import src.ClassChecker;
import src.IR_TYPE_WRAPPER;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP exp;
	public String fieldName;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_EXP exp,String fieldName)
	{
		this.exp = exp;
		this.fieldName = fieldName;
	}

	public IR_TYPE_WRAPPER isValid() throws Exception {
		IR_TYPE_WRAPPER wrapper = exp.isValid();

		// get type
		AST_TYPE expType = wrapper.type;
		if(!(expType instanceof AST_TYPE_CLASS)){
			throw new Exception("Cant access property of non-class type " + expType);
		}
		AST_TYPE_CLASS expClass = (AST_TYPE_CLASS) expType;
		AST_TYPE fieldType = ClassChecker.isValidField(expClass.name, this.fieldName);

		// create IR node
		T_Exp exp_temp = wrapper.IR ;
		int field_offset = ClassChecker.getFieldOffset(expClass.name, this.fieldName);
		T_Mem memNode = new T_Mem(new T_Binop(BINOPS.PLUS, exp_temp, new T_Const(field_offset)));
		return new IR_TYPE_WRAPPER(fieldType, memNode);
	}

	@Override
	public T_Exp buildIr() {

		AST_TYPE_CLASS expClass = (AST_TYPE_CLASS) exp.computedType;
		int offset;
		try {
			offset = ClassChecker.getFieldOffset(expClass.name, fieldName);
		} catch (Exception e) {
			throw new RuntimeException("invalid field");
		}

		T_Temp expTemp = new T_Temp();
		T_Move moveExp = new T_Move(expTemp, exp.buildIr());
		T_Relop relop = new T_Relop(RELOPS.EQUAL, expTemp, new T_Const(0));
		T_CJump accessViolationCheck = new T_CJump(relop, new T_Label("access_violation"));

		T_ESeq checkAndGetVar = new T_ESeq(moveExp, new T_ESeq(accessViolationCheck, expTemp));
		T_Binop binop = new T_Binop(BINOPS.PLUS, checkAndGetVar, new T_Const(offset));
		return new T_Mem(binop);
	}
}