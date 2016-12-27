package AST; import src.ClassChecker;
import src.SymbolTable;

public class AST_TYPE_TERM extends AST_TYPE
{
	public TYPES type;

	public AST_TYPE_TERM(TYPES type){
		this.type = type;
	}

	public boolean isExtending(AST_TYPE other){
		AST_TYPE_TERM _other;
		if(other instanceof AST_TYPE_TERM){
			_other = (AST_TYPE_TERM) other;
			return this.type == null ? _other.type == null : this.type.equals(_other.type);
		}
		else{
			return false;
		}

	}

	public boolean equals(Object other){
		if(! (other instanceof AST_TYPE_TERM)){
			return false;
		}
		return this.isExtending((AST_TYPE_TERM) other);
	}

	@Override
	public AST_TYPE isValid() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
