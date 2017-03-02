package AST;

public abstract class AST_CLASS_ELEMENT_METHOD extends AST_CLASS_ELEMENT {
	public final AST_TYPE tp;
	public final AST_EXP_VAR_SIMPLE name;
	public final AST_FORMAL_LIST fr;
	public final AST_STMTS_LIST stmts;

	public AST_CLASS_ELEMENT_METHOD(int line, AST_TYPE tp, AST_EXP_VAR_SIMPLE name, AST_FORMAL_LIST fr, AST_STMTS_LIST stmts) {
		super(line);
		this.tp = tp;
		this.name = name;
		this.fr = fr;
		this.stmts = stmts;
	}
	
	public abstract String toString();
	
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception {
		visitor.visit(this);
	}
}