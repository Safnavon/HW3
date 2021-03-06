package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;

public class AST_TYPE_ARRAY extends AST_TYPE
{
	public AST_TYPE type;

	public boolean isExtending(AST_TYPE other) throws Exception {
		if(! (other instanceof AST_TYPE_ARRAY)){
			return false;
		}
		else {
			AST_TYPE_ARRAY _other = (AST_TYPE_ARRAY) other;
			if(		this.type instanceof AST_TYPE_ARRAY && _other.type instanceof AST_TYPE_ARRAY ||
					this.type instanceof AST_TYPE_TERM && _other.type instanceof AST_TYPE_TERM){
				return this.type.isExtending(_other.type);
			}
			else if (this.type instanceof AST_TYPE_CLASS && _other.type instanceof AST_TYPE_CLASS){
				return this.type.equals(_other.type);//notice equals here
			}
			else{
				return false;
			}
		}
	}

	public AST_TYPE_ARRAY(AST_TYPE type){
		this.type = type;
	}

	public boolean equals(Object other){
		if(! (other instanceof AST_TYPE_ARRAY)){
			return false;
		}
		try {
			return this.isExtending((AST_TYPE_ARRAY) other);
		} catch (Exception e) {
			return false;
		}
	}

	public IR_TYPE_WRAPPER isValid() throws Exception {
		type.isValid();
		return new IR_TYPE_WRAPPER(null, null);
	}

}
