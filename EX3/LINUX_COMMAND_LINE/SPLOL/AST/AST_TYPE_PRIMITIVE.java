package AST;

public class AST_TYPE_PRIMITIVE extends AST_TYPE {

	
	public AST_TYPE_PRIMITIVE(int line, String name){
		super(line, name);
	}
	
	public AST_TYPE_PRIMITIVE(int line, String name, int dim){
		super(line, name, dim);
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
	
	public String toString(){
		return "Primitive data type: "+(dim>0?(dim+"-dimensional array of "):"") + name;
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