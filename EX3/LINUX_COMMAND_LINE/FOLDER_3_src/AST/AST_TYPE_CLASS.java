package AST; import src.ClassChecker;
import src.IR_TYPE_WRAPPER;
import src.SymbolTable;
import src.ClassChecker;


public class AST_TYPE_CLASS extends AST_TYPE
{
	public String name;

	public AST_TYPE_CLASS(String name){
		this.name = name;
	}

	public boolean isExtending(AST_TYPE other) throws Exception {
		if(! (other instanceof AST_TYPE_CLASS)){
			return false;
		}
		else {
			AST_TYPE_CLASS _other = (AST_TYPE_CLASS) other;
			String subClassName = _other.name;
			do{
				if(this.name.equals(subClassName)){
					return true;
				}
				subClassName = ClassChecker.get(subClassName).parent == null ? null : ClassChecker.get(subClassName).parent.name;
			} while(subClassName != null);
			return false;
		}
	}

	public boolean equals(Object other){
		if(! (other instanceof AST_TYPE_CLASS)){
			return false;
		}
		else{
			AST_TYPE_CLASS _other = (AST_TYPE_CLASS) other;
			return this.name.equals(_other.name);
		}
	}

	/* we need to validate that a type perceived as class type is indeed an existing class
	 for example- without this, the following method would pass the validity check if no other method calls it:
	 void foo(String s) {return;} --> String is not necessarily a class (and we use 'string' in IC)
	*/
	public IR_TYPE_WRAPPER isValid() throws Exception {
		if(SymbolTable.get(name) == null){
			throw(new Exception("Error: type " + name + " is not a class name nor a primitive type"));
		}
		return new IR_TYPE_WRAPPER(null, null);
	}

}
