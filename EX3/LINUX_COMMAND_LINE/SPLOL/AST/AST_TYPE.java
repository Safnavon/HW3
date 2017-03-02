package AST;

public abstract class AST_TYPE extends AST_Node {
	public final String name;
	public int dim;
	
	public AST_TYPE(int line, String name){
		super(line);
		this.name=name;
		this.dim=0;
	}

	
	public AST_TYPE(int line, String name, int dim){
		super(line);
		this.name=name;
		this.dim=dim;
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
	
	public abstract String toString();

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