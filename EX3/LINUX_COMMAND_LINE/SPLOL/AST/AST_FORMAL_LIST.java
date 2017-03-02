package AST;


import java.util.ArrayList;
import java.util.List;

public class AST_FORMAL_LIST extends AST_Node {
	public final List<AST_FORMAL> fmls = new ArrayList<AST_FORMAL>();
	
	public AST_FORMAL_LIST(int line) {
		super(line);
	}

	/** Adds a statement to the tail of the list.
	 * 
	 * @param stmt A program statement.
	 */
	public void add(AST_FORMAL fml) {
		fmls.add(fml);
	}

	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	public int timesContained(String name){
		int cnt = 0;
		for(AST_FORMAL fm : fmls){
			if(fm.name.name.equals(name))
				cnt++;
		}
		return cnt;
	}
	
	public String toString(){
		return "Formal parameters:";
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