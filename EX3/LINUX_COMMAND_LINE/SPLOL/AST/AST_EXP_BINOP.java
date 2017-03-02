package AST;

/** An AST node for binary expressions.
 */
public class AST_EXP_BINOP extends AST_EXP {
	public final AST_EXP left;
	public final AST_EXP right;
	public final AST_EXP_OP_BINOP op;
	
	public AST_EXP_BINOP(int line, AST_EXP lhs, AST_EXP_OP_BINOP op, AST_EXP rhs) {
		super(line);
		this.left = lhs;
		this.right = rhs;
		this.op = op;
	}

	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
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
	
	public String toString() {
		return left.toString() + op.toString() + right.toString();
	}	
}