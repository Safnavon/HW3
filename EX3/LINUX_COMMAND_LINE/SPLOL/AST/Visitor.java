package AST;
/** An interface for AST visitors.
 */
public interface Visitor {
	public void visit(AST_STMTS_LIST stmts) throws Exception;
	public void visit(AST_STMT stmt) throws Exception;
	public void visit(AST_STMT_ASSIGN stmt) throws Exception;
	public void visit(AST_EXP expr) throws Exception;
	public void visit(AST_EXP_LITERAL_INT expr);
	public void visit(AST_EXP_BINOP expr) throws Exception;
	public void visit(AST_TYPE n) throws Exception;
	public void visit(AST_PROGRAM program) throws Exception;
	public void visit(AST_CLASS_DECL_LIST classDeclList) throws Exception;
	public void visit(AST_CLASS_DECL classDecl) throws Exception;
	public void visit(AST_CLASS_ELEMENT_LIST classElemList) throws Exception;
	public void visit(AST_CLASS_ELEMENT classElem) throws Exception;
	public void visit(AST_ID_LIST idList) throws Exception;
	public void visit(AST_FORMAL_LIST formals) throws Exception;
	public void visit(AST_FORMAL formal) throws Exception;
	public void visit(AST_EXP_LIST exprList) throws Exception;
	public void visit(AST_EXP_CALL call) throws Exception;
	public void visit(AST_EXP_VAR location) throws Exception;
}