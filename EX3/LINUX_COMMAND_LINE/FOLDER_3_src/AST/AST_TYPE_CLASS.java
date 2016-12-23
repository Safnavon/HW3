package AST; import SymbolTable; import ClassChecker;

public class AST_TYPE_CLASS extends AST_TYPE
{
	public String name;

	public AST_TYPE_CLASS(String name){
		this.name = name;
	}
	public boolean isExtending(AST_TYPE other){
		if(! other instanceof AST_TYPE_CLASS){
			return false;
		}
		else {
			AST_TYPE_CLASS _other = (AST_TYPE_CLASS) other;
			String subClassName = _other.name;
			do{
				if(this.name.equals(subClassName)){
					return true;
				}
				subClassName = ClassChecker.get(subClassName).extend;

			} while(subClassName != null);
			return false;
		}
	}

	public boolean equals(Object other){
		if(! other instanceof AST_TYPE_CLASS){
			return false;
		}
		else{
			AST_TYPE_CLASS _other = (AST_TYPE_CLASS) other;
			return this.name.equals(_other.name);
		}
	}
}
