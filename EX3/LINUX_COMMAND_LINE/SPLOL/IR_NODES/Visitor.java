package IR_NODES;


/** An interface for AST visitors.
 */
public interface Visitor {
	public void visit(IR_Node irNode) throws Exception;
	public void visit(Label label) throws Exception;
	public void visit(T_Binop t_binop) throws Exception;
	public void visit(T_Call t_call) throws Exception;
	public void visit(T_Cjump t_cjump);
	public void visit(T_Const t_const) throws Exception;
	public void visit(T_exp t_exp) throws Exception;
	public void visit(T_Function t_function) throws Exception;
	public void visit(T_JumpLabel t_jumpLabel) throws Exception;
	public void visit(T_JumpRegister t_JumpRegister) throws Exception;
	public void visit(T_Label t_Label) throws Exception;
	public void visit(T_Mem t_Mem) throws Exception;
	public void visit(T_Move t_move) throws Exception;
	public void visit(T_Seq t_seq) throws Exception;
	public void visit(T_Temp t_temp) throws Exception;
	public void visit(Temp temp) throws Exception;
}