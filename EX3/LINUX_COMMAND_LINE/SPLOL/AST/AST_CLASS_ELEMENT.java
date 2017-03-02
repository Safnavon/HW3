package AST;

public abstract class AST_CLASS_ELEMENT extends AST_Node {
	
	public AST_CLASS_ELEMENT(int line){
		super(line);
	}
	
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception{
		visitor.visit(this);
	}
}