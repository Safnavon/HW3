package AST;

public class AST_EXP_VAR_FIELD extends AST_EXP_VAR {
	public final AST_EXP object;
	public final AST_EXP_VAR_SIMPLE name;

	public AST_EXP_VAR_FIELD(int line, AST_EXP object, AST_EXP_VAR_SIMPLE name) {
		super(line);
		this.object=object;
		this.name=name;
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

	@Override
	public String toString() {
	
		return "reference to external variable:";
	}
}