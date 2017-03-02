package AST;

public abstract class AST_EXP_CALL extends AST_EXP {
	public final AST_EXP_VAR_SIMPLE funcName;
	public final AST_EXP_LIST l;
	
	public AST_EXP_CALL(int line, AST_EXP_VAR_SIMPLE funcName, AST_EXP_LIST l) {
		super(line);
		this.funcName=funcName;
		this.l=l;
	}
	
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return funcName+"("+ (l==null?"":l.toString()) +")";
	}
}