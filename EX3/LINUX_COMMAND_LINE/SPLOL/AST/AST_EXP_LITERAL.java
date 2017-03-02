package AST;

public abstract class AST_EXP_LITERAL extends AST_EXP {
	
	public AST_EXP_LITERAL(int line){
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