package AST;
/** An interface for a propagating AST visitor.
 * The visitor passes down objects of type <code>DownType</code>
 * and propagates up objects of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType,UpType> {
	public UpType visit(AST_STMTS_LIST stmts, DownType d);
	public UpType visit(AST_STMT stmt, DownType d);
	public UpType visit(AST_STMT_ASSIGN stmt, DownType d);
	public UpType visit(AST_EXP expr, DownType d);
	public UpType visit(AST_EXP_LITERAL_INT expr, DownType d);
	public UpType visit(AST_EXP_BINOP expr, DownType d);
	public UpType visit(AST_TYPE tp, DownType d);
	public UpType visit(AST_PROGRAM program, DownType d);
	public UpType visit(AST_CLASS_DECL_LIST classDeclList, DownType d);
	public UpType visit(AST_CLASS_DECL classDecl, DownType d);
	public UpType visit(AST_CLASS_ELEMENT_LIST classElemList, DownType d);
	public UpType visit(AST_CLASS_ELEMENT_FIELD field, DownType d);
	public UpType visit(AST_CLASS_ELEMENT_METHOD_VIRTUAL virtualMethod, DownType d);
	public UpType visit(AST_ID_LIST idList, DownType d);
	public UpType visit(AST_FORMAL_LIST formals, DownType d);
	public UpType visit(AST_FORMAL formal, DownType d);
	public UpType visit(AST_EXP_LIST exprList, DownType d);
	public UpType visit(AST_EXP_CALL_VIRTUAL virtualCall, DownType d);
	public UpType visit(AST_EXP_VAR_SIMPLE shortLocation, DownType d);
	public UpType visit(AST_EXP_VAR_FIELD longLocation, DownType d);
	public UpType visit(AST_EXP_VAR_SUBSCRIPT arrLocation, DownType d);
}