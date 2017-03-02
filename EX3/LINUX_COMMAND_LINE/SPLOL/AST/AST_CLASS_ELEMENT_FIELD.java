package AST;

public class AST_CLASS_ELEMENT_FIELD extends AST_CLASS_ELEMENT {
	
	public final AST_TYPE tp;
	public final AST_ID_LIST ids;
	
	public AST_CLASS_ELEMENT_FIELD(int line, AST_TYPE tp, AST_ID_LIST ids) {
		super(line);
		this.tp=tp;
		this.ids=ids;
	}

	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	public String toString(){
		return "Declaration of field"+(((ids!=null)&&(ids.ids.size()>1))?"s":"")+":";
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