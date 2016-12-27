package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_EXP_BINOP extends AST_EXP
{
	BINOPS OP;
	public AST_EXP left;
	public AST_EXP right;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,BINOPS op)
	{
		this.left = left;
		this.right = right;
		this.OP = op;
	}
	private AST_TYPE validateClassTypes(AST_TYPE leftClass, AST_TYPE rightClass)throws Exception{
		AST_TYPE_CLASS l,r;
		if(!(leftClass instanceof AST_TYPE_CLASS) || !(rightClass instanceof AST_TYPE_CLASS)){
			throw new Exception("Expected both sides of op to be classes");
		}
		l = (AST_TYPE_CLASS) leftClass;
		r = (AST_TYPE_CLASS) rightClass;
		if(l.isExtending(r) || r.isExtending(l)){
			return new AST_TYPE_TERM(TYPES.INT);
		}
		else{
			throw new Exception("Tried to compane type "+l.name+" with type "+r.name);
		}
	}
	@Override
	public AST_TYPE isValid() throws Exception {
		AST_TYPE leftType =left.isValid();
		AST_TYPE rightType = right.isValid();
		AST_TYPE_TERM leftTypeTerm;
		AST_TYPE_TERM rightTypeTerm;

		if(leftType.getClass().equals(AST_TYPE_TERM.class))
			leftTypeTerm = (AST_TYPE_TERM)leftType;
		else{
			throw(new Exception("left expression is not term"));
		}
		if(rightType.getClass().equals(AST_TYPE_TERM.class))
			rightTypeTerm = (AST_TYPE_TERM)rightType;
		else{
			throw(new Exception("right expression is not term"));

		}
		if(!leftTypeTerm.type.equals(rightTypeTerm.type)){
			throw(new Exception("term types arent equal"));
		}
		if(!(leftTypeTerm.type.equals(TYPES.INT))){
			if(!leftTypeTerm.type.equals(TYPES.STRING)){
				throw(new Exception("term types arent int or string"));

			}
			if(OP!=BINOPS.PLUS ||OP!=BINOPS.EQUAL){
				throw(new Exception("only PLUS or EQUAL operands are supported for type string"));
			}
		}

		return leftTypeTerm;
	}
}
