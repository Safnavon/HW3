package AST;


import java.util.List;
import java.util.ArrayList;

/** An AST node for a list of statements.
 */
public class AST_STMTS_LIST extends AST_Node {
	public final List<AST_STMT> statements = new ArrayList<AST_STMT>();
	
	public AST_STMTS_LIST(int line, AST_STMT stmt) {
		super(line);
		statements.add(stmt);
	}
	public AST_STMTS_LIST(int line) {
		super(line);
	}

	/** Adds a statement to the tail of the list.
	 * 
	 * @param stmt A program statement.
	 */
	public void addStmt(AST_STMT stmt) {
		statements.add(stmt);
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