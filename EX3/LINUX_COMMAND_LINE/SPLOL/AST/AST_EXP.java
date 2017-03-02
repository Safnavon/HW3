package AST;

/** A base class for AST nodes for expressions.
 */
public abstract class AST_EXP extends AST_Node {
	
	public AST_TYPE type = null;
	
	public AST_EXP(int line){
		super(line);
	}
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws Exception 
	 */
	public abstract void accept(Visitor visitor) throws Exception;
	public abstract String toString();
}