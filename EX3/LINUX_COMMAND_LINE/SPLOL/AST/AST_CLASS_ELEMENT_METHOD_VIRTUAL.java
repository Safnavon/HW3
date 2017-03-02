package AST;

public class AST_CLASS_ELEMENT_METHOD_VIRTUAL extends AST_CLASS_ELEMENT_METHOD {

	public AST_CLASS_ELEMENT_METHOD_VIRTUAL(int line, AST_TYPE tp, AST_EXP_VAR_SIMPLE name, AST_FORMAL_LIST fr, AST_STMTS_LIST stmts) {
		super(line, tp, name, fr, stmts);
	}

	/**
	 * Accepts a visitor object as part of the visitor pattern.
	 * 
	 * @param visitor
	 *            A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Declaration of virtual method"+(tp==null?" of type void":"")+":";
	}

	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}
}