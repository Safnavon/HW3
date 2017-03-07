package AST;
import src.IR_TYPE_WRAPPER;
import java.util.ArrayList;

public class AST_FORMAL_LIST extends AST_Node
{
	public AST_FORMAL first; 
	public AST_FORMAL_LIST rest;
	
	public AST_FORMAL_LIST(AST_FORMAL first, AST_FORMAL_LIST rest){
		this.first = first;
		this.rest = rest;
	}

	public AST_FORMAL_LIST(AST_FORMAL first) {
		this.first = first;
		this.rest = null;
	}


	public IR_TYPE_WRAPPER isValid() throws Exception {
		first.isValid();
		if (rest != null) {
			rest.isValid();
		}
		return new IR_TYPE_WRAPPER(null, null); //TODO
	}

	public ArrayList<AST_FORMAL> toArrayList() {
		ArrayList<AST_FORMAL> formals = new ArrayList<AST_FORMAL>();
		formals.add(this.first);
		AST_FORMAL_LIST rest = this.rest;
		while (rest != null) {
			formals.add(rest.first);
			rest = rest.rest;
		}
		return formals;
	}
}