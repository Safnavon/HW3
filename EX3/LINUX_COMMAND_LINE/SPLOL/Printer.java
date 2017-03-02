import AST.AST_Node;
import AST.AST_EXP_VAR_SUBSCRIPT;
import AST.AST_STMT_ASSIGN;
import AST.AST_EXP_OP_BINOP;
import AST.AST_EXP_BINOP;
import AST.AST_EXP_CALL;
import AST.AST_STMT_CALL;
import AST.AST_CLASS_DECL;
import AST.AST_CLASS_DECL_LIST;
import AST.AST_CLASS_ELEMENT;
import AST.AST_CLASS_ELEMENT_LIST;
import AST.AST_EXP;
import AST.AST_EXP_LIST;
import AST.AST_CLASS_ELEMENT_FIELD;
import AST.AST_FORMAL;
import AST.AST_FORMAL_LIST;
import AST.AST_ID_LIST;
import AST.AST_STMT_IF;
import AST.AST_EXP_LITERAL_INT;
import AST.AST_STMT_LIST;
import AST.AST_EXP_LITERAL;
import AST.AST_EXP_VAR;
import AST.AST_EXP_VAR_FIELD;
import AST.AST_CLASS_ELEMENT_METHOD;
import AST.AST_EXP_NEW_ARR;
import AST.AST_EXP_NEW_CLASS;
import AST.AST_EXP_LITERAL_NULL;
import AST.AST_EXP_OP;
import AST.AST_EXP_PARENTHESES;
import AST.AST_PROGRAM;
import AST.AST_STMT_RETURN;
import AST.AST_EXP_VAR_SIMPLE;
import AST.AST_STMT;
import AST.AST_STMTS_LIST;
import AST.AST_EXP_LITERAL_STRING;
import AST.AST_TYPE;
import AST.AST_STMT_VAR;
import AST.AST_EXP_CALL_VIRTUAL;
import AST.Visitor;
import AST.AST_STMT_WHILE;

/** Pretty-prints an SLP AST.
 */
public class Printer implements Visitor {
	
	int indent = 0;
	
	protected final AST_Node root;
	
	/** Constructs a printin visitor from an AST.
	 * 
	 * @param root The root of the AST.
	 */
	public Printer(AST_Node root) {
		this.root = root;
	}

	/** Prints the AST with the given root.
	 * @throws Exception 
	 */
	public void print() throws Exception {
		root.accept(this);
	}
	
	public void indent(AST_Node node){
		System.out.print("\n");
		for(int i=indent; i>0; i--){
			System.out.print("  ");
		}
		System.out.print(node.line+": ");
	}

	@Override
	public void visit(AST_STMTS_LIST stmts) {
		for(AST_STMT s : stmts.statements){
			visit(s);
		}
	}

	@Override
	public void visit(AST_STMT stmt) {
		
		if(stmt instanceof AST_STMT_ASSIGN)
			visit((AST_STMT_ASSIGN)stmt);
		else if(stmt instanceof AST_STMT_CALL)
			visit((AST_STMT_CALL)stmt);
		else if(stmt instanceof AST_STMT_IF)
			visit((AST_STMT_IF)stmt);
		else if(stmt instanceof AST_STMT_LIST)
			visit((AST_STMT_LIST)stmt);
		else if(stmt instanceof AST_STMT_RETURN)
			visit((AST_STMT_RETURN)stmt);
		else if(stmt instanceof AST_STMT_VAR)
			visit((AST_STMT_VAR)stmt);
		else if(stmt instanceof AST_STMT_WHILE)
			visit((AST_STMT_WHILE)stmt);
	}

	@Override
	public void visit(AST_STMT_ASSIGN stmt) {
		indent(stmt);
		System.out.print("assignment statement:");
		indent++;
			indent(stmt.loc);
			System.out.print("left hand side:");
			indent++;
				visit(stmt.loc);
			indent--;
			indent(stmt.rhs);
			System.out.print("right hand side:");
			indent++;
				visit(stmt.rhs);
			indent--;
		indent--;
	}
	
	public void visit(AST_STMT_CALL stmt) {
		indent(stmt);
		System.out.print("call statement:");
		indent++;
			visit(stmt.call);
		indent--;
	}
	
	public void visit(AST_STMT_RETURN stmt) {
		indent(stmt);
		System.out.print("return statement");
		if(stmt.e!=null){
			System.out.print(", expression to be returned:");
			indent++;
				visit(stmt.e);
			indent--;
		}
	}
	/*
	public void visit(AST_STMT_IF stmt) {
		indent(stmt);
		System.out.print("if statement:");
		indent++;
			indent(stmt.cond);
			System.out.print("condition:");
			indent++;
				visit(stmt.cond);
			indent--;
			indent(stmt.ifStmt);
			System.out.print("if body:");
			indent++;
				visit(stmt.ifStmt);
			indent--;
		indent--;
	}*/
	
	public void visit(AST_STMT_WHILE stmt) {
		indent(stmt);
		System.out.print("while statement:");
		indent++;
			indent(stmt.cond);
			System.out.print("condition:");
			indent++;
				visit(stmt.cond);
			indent--;
			indent(stmt.stmt);
			System.out.print("while body:");
			indent++;
				visit(stmt.stmt);
			indent--;
		indent--;
	}
	
	public void visit(AST_STMT_LIST stmt) {
		indent(stmt);
		System.out.print((stmt.sl==null?"empty ":"")+"statement block"+(stmt.sl==null?"":":"));
		if(stmt.sl!=null){
			indent++;
				visit(stmt.sl);
			indent--;
		}
	}
	
	public void visit(AST_STMT_VAR stmt) {
		indent(stmt);
		System.out.print("variable declaration statement"+(stmt.rhs!=null?", with initial value":"")+":");
		indent++;
			indent(stmt.tp);
			System.out.print("variable type:");
			indent++;
				visit(stmt.tp);
			indent--;
			indent(stmt.varName);
			System.out.print("variable name:");
			indent++;
				visit(stmt.varName);
			indent--;
			if(stmt.rhs!=null){
				indent(stmt.rhs);
				System.out.print("initial value expression:");
				indent++;
					visit(stmt.rhs);
				indent--;
			}
		indent--;
	}
	
	public void visit(AST_EXP_OP op) {
		if(op instanceof AST_EXP_OP_BINOP)
			visit((AST_EXP_OP_BINOP)op);
	}
	
	public void visit(AST_EXP_OP_BINOP op) {
		indent(op);
		System.out.print(op.op);
	}

	@Override
	public void visit(AST_EXP expr) {
		if(expr instanceof AST_EXP_VAR_SIMPLE)
			visit((AST_EXP_VAR_SIMPLE)expr);
		else if(expr instanceof AST_EXP_VAR_FIELD)
			visit((AST_EXP_VAR_FIELD)expr);
		else if(expr instanceof AST_EXP_VAR_SUBSCRIPT)
			visit((AST_EXP_VAR_SUBSCRIPT)expr);
		else if(expr instanceof AST_EXP_CALL_VIRTUAL)
			visit((AST_EXP_CALL_VIRTUAL)expr);
		else if(expr instanceof AST_EXP_NEW_CLASS)
			visit((AST_EXP_NEW_CLASS)expr);
		else if(expr instanceof AST_EXP_NEW_ARR)
			visit((AST_EXP_NEW_ARR)expr);
		else if(expr instanceof AST_EXP_BINOP)
			visit((AST_EXP_BINOP)expr);			
		else if(expr instanceof AST_EXP_LITERAL_INT)
			visit((AST_EXP_LITERAL_INT)expr);
		else if(expr instanceof AST_EXP_LITERAL_STRING)
			visit((AST_EXP_LITERAL_STRING)expr);	
		else if(expr instanceof AST_EXP_LITERAL_NULL)
			visit((AST_EXP_LITERAL_NULL)expr);		
		else if(expr instanceof AST_EXP_PARENTHESES)
			visit((AST_EXP_PARENTHESES)expr);
	}
	
	public void visit(AST_EXP_NEW_CLASS expr) {
		indent(expr);
		System.out.print("instantiation of class: " + expr.className);
	}
	
	public void visit(AST_EXP_NEW_ARR expr) {
		indent(expr);
		System.out.print("instantiation of array:");
		indent++;
			indent(expr.tp);
			System.out.print("array type:");
			indent++;
				visit(expr.tp);
			indent--;
			indent(expr.sz);
			System.out.print("expression for size of array:");
			indent++;
				visit(expr.sz);
			indent--;
		indent--;
	}
	
	
	public void visit(AST_EXP_BINOP expr) {
		indent(expr);
		System.out.print("binary opetation expression:");
		indent++;
			indent(expr.op);
			System.out.print("operation:");
			indent++;
				visit(expr.op);
			indent--;
			indent(expr.left);
			System.out.print("left hand side expression:");
			indent++;
				visit(expr.left);
			indent--;
			indent(expr.right);
			System.out.print("right hand side expression:");
			indent++;
				visit(expr.right);
			indent--;
		indent--;
	}
	
	public void visit(AST_EXP_PARENTHESES expr) {
		indent(expr);
		System.out.print("expression in parentheses:");
		indent++;
			visit(expr.expr);
		indent--;
	}
	
	public void visit(AST_EXP_LITERAL ltr) {
		if(ltr instanceof AST_EXP_LITERAL_INT)
			visit((AST_EXP_LITERAL_INT)ltr);
		else if(ltr instanceof AST_EXP_LITERAL_STRING)
			visit((AST_EXP_LITERAL_STRING)ltr);
		else if(ltr instanceof AST_EXP_LITERAL_NULL)
			visit((AST_EXP_LITERAL_NULL)ltr);
	}

	@Override
	public void visit(AST_EXP_LITERAL_INT ltr) {
		indent(ltr);
		System.out.print("int literal: " + ltr.toString());
	}
	
	public void visit(AST_EXP_LITERAL_STRING ltr) {
		indent(ltr);
		System.out.print("String literal: " + ltr.toString());
	}

	public void visit(AST_EXP_LITERAL_NULL ltr) {
		indent(ltr);
		System.out.print("null literal");
	}
	

	@Override
	public void visit(AST_TYPE n) {
		indent(n);
		System.out.print(n.toString());
	}

	@Override
	public void visit(AST_PROGRAM program) {
		if(program.cdl!=null)
			visit(program.cdl);
		else
			System.out.println("\nEmpty program - no classes declared.");
	}

	@Override
	public void visit(AST_CLASS_DECL_LIST classDeclList) {
		for(AST_CLASS_DECL cd : classDeclList.classes){
			visit(cd);
		}
	}

	@Override
	public void visit(AST_CLASS_DECL classDecl) {
		indent(classDecl);
		
		System.out.print(classDecl.toString());
		
		indent++;
			if(classDecl.elements!=null)
				visit(classDecl.elements);
		indent--;
	}

	@Override
	public void visit(AST_CLASS_ELEMENT_LIST classElemList) {
		for(AST_CLASS_ELEMENT ce:classElemList.elems)
			visit(ce);
		
	}

	@Override
	public void visit(AST_CLASS_ELEMENT classElem) {
		// abstract
		if(classElem instanceof AST_CLASS_ELEMENT_FIELD)
			visit((AST_CLASS_ELEMENT_FIELD)classElem);
		else if(classElem instanceof AST_CLASS_ELEMENT_METHOD)
			visit((AST_CLASS_ELEMENT_METHOD)classElem);

	}
	
	public void visit(AST_CLASS_ELEMENT_FIELD f) {
		indent(f);
		System.out.print(f.toString());
		indent++;
			visit(f.tp);
			visit(f.ids);
		indent--;
	}
	
	public void visit(AST_CLASS_ELEMENT_METHOD m) {
		indent(m);
		System.out.print(m.toString());
		indent++;
			if(m.tp!=null)
				visit(m.tp);
			visit(m.name);
			if(m.fr!=null)
				visit(m.fr);
			if(m.stmts!=null)
				visit(m.stmts);
		indent--;
	}
	
	@Override
	public void visit(AST_ID_LIST idList) {
		for(AST_EXP_VAR_SIMPLE s:idList.ids){
			visit(s);
		}
	}

	@Override
	public void visit(AST_FORMAL_LIST formals) {
		indent(formals);
		System.out.print(formals.toString());
		indent++;
			for(AST_FORMAL fm : formals.fmls){
				visit(fm);
			}
		indent--;
	}

	@Override
	public void visit(AST_FORMAL formal) {
		indent(formal.tp);
		System.out.print(formal.toString());
		indent++;
			visit(formal.tp);
			visit(formal.name);
		indent--;
	}

	@Override
	public void visit(AST_EXP_LIST exprList) {
		for(AST_EXP e:exprList.exprs){
			visit(e);
		}
	}

	@Override
	public void visit(AST_EXP_CALL call) {
		if(call instanceof AST_EXP_CALL_VIRTUAL)
			visit((AST_EXP_CALL_VIRTUAL)call);
		
	}
	
	public void visit(AST_EXP_CALL_VIRTUAL call) {
		
		indent(call);
		System.out.print(call.toString());
		indent++;
			if(call.object!=null){
				indent(call.object);
				System.out.print("reference to object:");
				indent++;
					visit(call.object);
				indent--;
			}
			indent(call.funcName);
			System.out.print("method name:");
			indent++;
				visit(call.funcName);
			indent--;
			if(call.l!=null){
				indent(call.l);
				System.out.print("argument expression"+(call.l.exprs.size()>1?"s":"")+":");
				indent++;
					visit(call.l);
				indent--;
			}
		indent--;
		
	}

	@Override
	public void visit(AST_EXP_VAR location) {
		// abstract
		if(location instanceof AST_EXP_VAR_SIMPLE)
			visit((AST_EXP_VAR_SIMPLE)location);
		else if(location instanceof AST_EXP_VAR_FIELD)
			visit((AST_EXP_VAR_FIELD)location);
		else if(location instanceof AST_EXP_VAR_SUBSCRIPT)
			visit((AST_EXP_VAR_SUBSCRIPT)location);
	}
	
	public void visit(AST_EXP_VAR_SIMPLE location){
		
		indent(location);
		System.out.print(location.toString());
		
		
	}
	
	public void visit(AST_EXP_VAR_FIELD location){
		
		indent(location);
		System.out.print(location.toString());
		indent++;
			indent(location.object);
			System.out.print("referred object:");
			indent++;
				visit(location.object);
			indent--;
			System.out.print("referred variable:");
			indent++;
				visit(location.name);
			indent--;
		indent--;
		
	}
	
	public void visit(AST_EXP_VAR_SUBSCRIPT location){
		
		indent(location);
		System.out.print(location.toString());
		indent++;
			indent(location.arr);
			System.out.print("referred array object:");
			indent++;
				visit(location.arr);
			indent--;
			indent(location.where);
			System.out.print("offset expression:");
			indent++;
				visit(location.where);
			indent--;
		indent--;

		
	}
}
/*
	public void visit(StmtList stmts) {
		for (Stmt s : stmts.statements) {
			s.accept(this);
			System.out.println();
		}
	}

	public void visit(UnopExpr expr) {
		System.out.print(expr.op);
		expr.operand.accept(this);
	}
	
	public void visit(BinopExpr expr) {
		expr.lhs.accept(this);
		System.out.print(expr.op);
		expr.rhs.accept(this);
	}
	
	public void visit(Stmt stmt) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt abstract class");
	}
	
	public void visit(PrintStmt stmt) {
		System.out.print("print(");
		stmt.expr.accept(this);
		System.out.print(");");
	}
	
	public void visit(AssignStmt stmt) {
		stmt.varExpr.accept(this);
		System.out.print("=");
		stmt.rhs.accept(this);
		System.out.print(";");
	}
	
	public void visit(Expr expr) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}	
	
	public void visit(ReadIExpr expr) {
		System.out.print("readi()");
	}	
	
	public void visit(VarExpr expr) {
		System.out.print(expr.name);
	}
	
	public void visit(IntLiteral expr) {
		System.out.print(expr.value);
	}
*/