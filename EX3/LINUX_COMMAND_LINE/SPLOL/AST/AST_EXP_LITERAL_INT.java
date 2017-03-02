package AST;

/** An expression denoting a constant integer.
 */
public class AST_EXP_LITERAL_INT extends AST_EXP_LITERAL {
	public final int i;

	public AST_EXP_LITERAL_INT(int line, int i) {
		super(line);
		this.i=i;
	}

	/**
	 * Accepts a visitor object as part of the visitor pattern.
	 * 
	 * @param visitor
	 *            A visitor.
	 */
	@Override
	public void accept(Visitor visitor) {
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
		return Integer.toString(i);
	}
}