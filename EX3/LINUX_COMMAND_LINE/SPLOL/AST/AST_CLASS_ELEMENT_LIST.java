package AST;


import java.util.ArrayList;
import java.util.List;

public class AST_CLASS_ELEMENT_LIST extends AST_Node {
	public final List<AST_CLASS_ELEMENT> elems = new ArrayList<AST_CLASS_ELEMENT>();
	
	public AST_CLASS_ELEMENT_LIST(int line) {
		super(line);
	}

	/** Adds a statement to the tail of the list.
	 * 
	 * @param stmt A program statement.
	 */
	public void add(AST_CLASS_ELEMENT elem) {
		elems.add(elem);
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
}