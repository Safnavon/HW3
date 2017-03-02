package AST;

/** The super class of all AST node for program statements.
 */
public abstract class AST_STMT extends AST_Node {
	
	public AST_STMT(int line){
		super(line);
	}
	
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception {
		visitor.visit(this);
	}
}