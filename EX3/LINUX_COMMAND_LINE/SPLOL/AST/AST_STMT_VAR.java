package AST;

public class AST_STMT_VAR extends AST_STMT {
	public final AST_TYPE tp;
	public final AST_EXP_VAR_SIMPLE varName;
	public final AST_EXP rhs;

	public AST_STMT_VAR(int line, AST_TYPE tp, AST_EXP_VAR_SIMPLE varName, AST_EXP rhs) {
		super(line);
		this.tp = tp;
		this.varName = varName;
		this.rhs = rhs;
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