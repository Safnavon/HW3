package AST;

public abstract class AST_EXP_OP extends AST_EXP {
	
	public final String op;
	
	public AST_EXP_OP(int line, String op) {
		super(line);
		this.op=op;
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