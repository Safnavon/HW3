import AST.AST_Node;
import AST.AST_EXP_VAR_SUBSCRIPT;
import AST.AST_STMT_ASSIGN;
import AST.AST_EXP_OP_BINOP;
import AST.AST_EXP_BINOP;
import AST.AST_EXP_CALL;
import AST.AST_STMT_CALL;

import java.util.ArrayList;

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
import AST.AST_CLASS_ELEMENT_METHOD_VIRTUAL;
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
import AST.AST_TYPE_CLASS;
import AST.AST_TYPE_PRIMITIVE;
import AST.AST_STMT_VAR;
import AST.AST_EXP_CALL_VIRTUAL;
import AST.Visitor;
import AST.AST_STMT_WHILE;

/** Pretty-prints an SLP AST.
 */
public class SemanticChecker implements Visitor {
	
	int indent = 0;
	
	private static final String STMT_BLOCK = "statement_block";
	
	private static final String MAIN_SCOPE = "main_scope";
	
	private static final String INITIATED = "initiated_variable";
	
	private static final String NOT_INITIATED = "uninitiated_variable";
	
	private static final String SCOPE_LIMIT = null;
	
	private static final String CLASS_SCOPE = "class_scope";
	
	private static final String METHOD_SCOPE = "method_scope";
	
	private static final String METHOD_DECLARATION = "method_declaration";
	
	private AST_CLASS_DECL_LIST classDeclList = null;
	
	private AST_CLASS_DECL classDecl = null;
	
	private ArrayList<String[]> scopeTable = new ArrayList<>();
	
	private AST_TYPE returnType = null;
	
	private String currentScope = MAIN_SCOPE;
	
	protected final AST_Node root;
	
	/** Constructs a printin visitor from an AST.
	 * 
	 * @param root The root of the AST.
	 */
	public SemanticChecker(AST_Node root) {
		this.root = root;
	}

	/** Prints the AST with the given root.
	 */
	public boolean check() {
		boolean legal = true;
		try{
			root.accept(this);
		}
		catch(Exception e){
			System.out.println(e);
			int line = e.getStackTrace()[0].getLineNumber();
			System.out.println("exception thrown at line: "+line);
			
			e.printStackTrace();
			
			legal = false;
		}
		return legal;
	}
	
	public void indent(AST_Node node){
		System.out.print("\n");
		for(int i=indent; i>0; i--){
			System.out.print("  ");
		}
		System.out.print(node.line+": ");
	}

	
	@Override
	public void visit(AST_STMTS_LIST stmts) throws Exception {
		for(int i=0; i< stmts.statements.size(); i++){
			
			AST_STMT s = stmts.statements.get(i);
			
			if(i==stmts.statements.size()-1 && currentScope == METHOD_SCOPE){
				if(returnType!=null && !(s instanceof AST_STMT_RETURN)){
					//throw new Exception("semantic checker line: 120");		
				}
			}
			
			visit(s);
		}
	}

	@Override
	public void visit(AST_STMT stmt) throws Exception {
		
		if(stmt==null)
			throw new Exception("semantic checker line: 131");
		if(stmt instanceof AST_STMT_ASSIGN)
			visit((AST_STMT_ASSIGN)stmt);
		else if(stmt instanceof AST_STMT_CALL)
			visit((AST_STMT_CALL)stmt);
		else if(stmt instanceof AST_STMT_RETURN)
			visit((AST_STMT_RETURN)stmt);
		else if(stmt instanceof AST_STMT_IF)
			visit((AST_STMT_IF)stmt);
		else if(stmt instanceof AST_STMT_WHILE)
			visit((AST_STMT_WHILE)stmt);		
		else if(stmt instanceof AST_STMT_VAR)
			visit((AST_STMT_VAR)stmt);
		else if(stmt instanceof AST_STMT_LIST)
			visit((AST_STMT_LIST)stmt);
		else
			throw new Exception("semantic checker line: 147");
	}

	@Override
	public void visit(AST_STMT_ASSIGN stmt) throws Exception {
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
			
			
			if(!typeAssignable(stmt.loc.type, stmt.rhs.type)){
				throw new Exception("semantic checker line: 168");
			}
			
		indent--;
	}
	
	public boolean typeAssignable(AST_TYPE dst_tp, AST_TYPE src_tp){
		
		if(dst_tp==null && src_tp!=null)
			return false;
		
		if(dst_tp==null && src_tp==null)
			return true;
		
		if(src_tp==null && (dst_tp.dim>0 || (!dst_tp.name.equals("int") && !dst_tp.name.equals("string"))))
			return true;
		
		if(src_tp.name.equals(dst_tp.name) && src_tp.dim == dst_tp.dim)
			return true;
		
		if(dst_tp.dim==0 && src_tp.dim==0)
			return stringTypeEquals(dst_tp.name, src_tp.name);
		
		return false;
	}
	
	public boolean stringTypeEquals(String tp1, String tp2){
		
		if(tp1.equals(tp2))
			return true;
		
		for(int i=0; i<classDeclList.classes.size(); i++)
			if(classDeclList.classes.get(i).name.equals(tp2))
				if(classDeclList.classes.get(i).parentName != null)
					return stringTypeEquals(tp1, classDeclList.classes.get(i).parentName);
		return false;
	}
	
	private boolean callCanBeVoid = false;
	
	public void visit(AST_STMT_CALL stmt) throws Exception {
		indent(stmt);
		System.out.print("call statement:");
		indent++;
			callCanBeVoid = true;
			visit(stmt.call);
			callCanBeVoid = false;
			
		indent--;
	}
	
	public void visit(AST_STMT_RETURN stmt) throws Exception {
		indent(stmt);
		

		
		System.out.print("return statement");
		if(stmt.e!=null){
			System.out.print(", expression to be returned:");
			indent++;
				visit(stmt.e);
			indent--;
		}
		
		if(returnType==null){
			if(stmt.e != null)
				throw new Exception("semantic checker line: 229");
		}
		else{
			if(!typeAssignable(returnType, stmt.e.type)){
				throw new Exception("semantic checker line: 233");
			}
		}
	}
	
	public void visit(AST_STMT_IF stmt) throws Exception {
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
		
		if(stmt.cond.type == null || stmt.cond.type.name != "int" || stmt.cond.type.dim > 0){
			throw new Exception("semantic checker line: 255");
		}
		
	}
	
	public void visit(AST_STMT_WHILE stmt) throws Exception {
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
		
		if(stmt.cond.type == null || stmt.cond.type.name != "int" || stmt.cond.type.dim > 0){
			throw new Exception("semantic checker line: 277");
		}
		
	}
	
	public void visit(AST_STMT_LIST stmt) throws Exception {
		indent(stmt);
		
		
		String prevScope = currentScope;
		String[] field = {SCOPE_LIMIT, SCOPE_LIMIT, currentScope, SCOPE_LIMIT};
		scopeTable.add(field);
		
		currentScope = STMT_BLOCK;
		
		System.out.print((stmt.sl==null?"empty ":"")+"statement block"+(stmt.sl==null?"":":"));
		if(stmt.sl!=null){
			indent++;
				visit(stmt.sl);
			indent--;
		}
		
		popScope();
		
		currentScope = prevScope;
		
	}
	
	boolean varShouldExist = true;
	
	public void visit(AST_STMT_VAR stmt) throws Exception {
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
			
				varShouldExist = false;
				visit(stmt.varName);
				varShouldExist = true;
			indent--;
			if(stmt.rhs!=null){
				indent(stmt.rhs);
				System.out.print("initial value expression:");
				indent++;
					visit(stmt.rhs);
				indent--;
				
				
				if(!typeAssignable(stmt.tp, stmt.rhs.type)){
					throw new Exception("semantic checker line: 328");
				}
				
			}
		indent--;
		
		if(nameInCurrScope(stmt.varName.name)){
			throw new Exception("semantic checker line: 335");
		}
		
		String[] field = {stmt.varName.name, typeToString(stmt.tp), currentScope, stmt.rhs!=null?INITIATED:NOT_INITIATED};
		scopeTable.add(field);
		
	}

	
	public boolean varExists(String varName){
		for(int i = scopeTable.size()-1; i>=0; i--){
			if(scopeTable.get(i)[0]!=SCOPE_LIMIT && scopeTable.get(i)[0].equals(varName))
				return true;
		}
		return false;
	}
	
	public boolean nameInCurrScope(String name){
		for(int i = scopeTable.size()-1; scopeTable.get(i)[0] != SCOPE_LIMIT; i--){
			if(scopeTable.get(i)[0].equals(name))
				return true;
		}
		return false;
	}
	
	public void visit(AST_EXP_OP op) throws Exception {
		if(op==null)
			throw new Exception("semantic checker line: 362");
		if(op instanceof AST_EXP_OP_BINOP)
			visit((AST_EXP_OP_BINOP)op);
		else
			throw new Exception("semantic checker line: 366");
	}
	
	public void visit(AST_EXP_OP_BINOP op) {
		indent(op);
		System.out.print(op.op);
	}

	@Override
	public void visit(AST_EXP expr) throws Exception {
		
		if(expr==null)
			throw new Exception("semantic checker line: 378");
		
		if(expr instanceof AST_EXP_VAR_SIMPLE)
			visit((AST_EXP_VAR_SIMPLE)expr);
		else if(expr instanceof AST_EXP_CALL_VIRTUAL)
			visit((AST_EXP_CALL_VIRTUAL)expr);
		else if(expr instanceof AST_EXP_VAR_SUBSCRIPT)
			visit((AST_EXP_VAR_SUBSCRIPT)expr);
		else if(expr instanceof AST_EXP_PARENTHESES)
			visit((AST_EXP_PARENTHESES)expr);
		else if(expr instanceof AST_EXP_BINOP)
			visit((AST_EXP_BINOP)expr);		
		else if(expr instanceof AST_EXP_LITERAL_NULL)
			visit((AST_EXP_LITERAL_NULL)expr);	
		else if(expr instanceof AST_EXP_LITERAL_STRING)
			visit((AST_EXP_LITERAL_STRING)expr);	
		else if(expr instanceof AST_EXP_LITERAL_INT)
			visit((AST_EXP_LITERAL_INT)expr);
		else if(expr instanceof AST_EXP_NEW_CLASS)
			visit((AST_EXP_NEW_CLASS)expr);
		else if(expr instanceof AST_EXP_NEW_ARR)
			visit((AST_EXP_NEW_ARR)expr);
		else if(expr instanceof AST_EXP_VAR_FIELD)
			visit((AST_EXP_VAR_FIELD)expr);
		else
			throw new Exception("semantic checker line: 426");
	}
	
	public void visit(AST_EXP_NEW_CLASS expr) throws Exception {
		indent(expr);
		
		if(!isNonArrayTypeDefined(expr.className))
			throw new Exception("semantic checker line: 433");
		
		expr.type = new AST_TYPE_CLASS(expr.line, expr.className);
		
		System.out.print("instantiation of class: " + expr.className);
	}
	
	public void visit(AST_EXP_NEW_ARR expr) throws Exception {
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
		
		
		
		if(expr.sz.type==null || !(expr.sz.type.name.equals("int") &&  expr.sz.type.dim==0)){
			throw new Exception("semantic checker line: 459");
		}
		
		
		if(!isTypeDefined(expr.tp))
			throw new Exception("semantic checker line: 464");
		
		if(expr.tp==null)
			throw new Exception("semantic checker line: 467");
		if(expr.tp instanceof AST_TYPE_CLASS)
			expr.type = new AST_TYPE_CLASS(expr.tp.line, expr.tp.name, expr.tp.dim+1);
		else if(expr.tp instanceof AST_TYPE_PRIMITIVE)
			expr.type = new AST_TYPE_PRIMITIVE(expr.tp.line, expr.tp.name, expr.tp.dim+1);
		else
			throw new Exception("semantic checker line: 473");
	}
	
	
	public void visit(AST_EXP_BINOP expr) throws Exception {
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
		
		
		if(expr.op.op.equals("+")){
			if(expr.left.type.dim>0 || expr.right.type.dim>0)
				throw new Exception("semantic checker line: 501");
			if(!(expr.left.type.name.equals("int") && expr.right.type.name.equals("int")) &&
				!(expr.left.type.name.equals("string") && expr.right.type.name.equals("string")))
					throw new Exception("semantic checker line: 504");
			
			String typeName = expr.left.type.name;
			expr.type = new AST_TYPE_PRIMITIVE(expr.line, typeName);
		}
		if(expr.op.op.equals("==") || expr.op.op.equals("!=")){
			if(!(typeAssignable(expr.left.type, expr.right.type) || typeAssignable(expr.right.type, expr.left.type)))
				throw new Exception("semantic checker line: 509");
			expr.type = new AST_TYPE_PRIMITIVE(expr.line, "int");
		}
		if(expr.op.op.equals("-") || expr.op.op.equals("*") || expr.op.op.equals("/") ||
				expr.op.op.equals("<=") || expr.op.op.equals("<") || expr.op.op.equals(">=") || expr.op.op.equals(">")){
			
			if(expr.left.type.dim>0 || expr.right.type.dim>0)
				throw new Exception("semantic checker line: 516");
			
			if(!(expr.left.type.name.equals("int") && expr.right.type.name.equals("int")))
				throw new Exception("semantic checker line: 516");
			
			expr.type = new AST_TYPE_PRIMITIVE(expr.line, "int");
		}
	}
	
	public void visit(AST_EXP_PARENTHESES expr) throws Exception {
		indent(expr);
		System.out.print("expression in parentheses:");
		indent++;
			visit(expr.expr);
		indent--;
		
		if(expr.expr == null || expr.expr.type == null)
			throw new Exception("semantic checker line: 530");
		
		if(expr.expr.type instanceof AST_TYPE_PRIMITIVE){
			expr.type = new AST_TYPE_PRIMITIVE(expr.expr.line, expr.expr.type.name, expr.expr.type.dim);
		}
		else if(expr.expr.type instanceof AST_TYPE_CLASS){
			expr.type = new AST_TYPE_CLASS(expr.expr.line, expr.expr.type.name, expr.expr.type.dim);
		}
		else
			throw new Exception("semantic checker line: 539");
		
	}
	
	public void visit(AST_EXP_LITERAL ltr) throws Exception {
		
		if(ltr==null)
			throw new Exception("semantic checker line: 546");
		
		if(ltr instanceof AST_EXP_LITERAL_INT)
			visit((AST_EXP_LITERAL_INT)ltr);
		else if(ltr instanceof AST_EXP_LITERAL_STRING)
			visit((AST_EXP_LITERAL_STRING)ltr);
		else if(ltr instanceof AST_EXP_LITERAL_NULL)
			visit((AST_EXP_LITERAL_NULL)ltr);
		else
			throw new Exception("semantic checker line: 555");
	}

	@Override
	public void visit(AST_EXP_LITERAL_INT ltr) {
		indent(ltr);
		
		ltr.type = new AST_TYPE_PRIMITIVE(ltr.line, "int");
		
		System.out.print("int literal: " + ltr.toString());
	}
	
	public void visit(AST_EXP_LITERAL_STRING ltr) {
		indent(ltr);
		
		ltr.type = new AST_TYPE_PRIMITIVE(ltr.line, "string");
		
		System.out.print("String literal: " + ltr.toString());
	}

	public void visit(AST_EXP_LITERAL_NULL ltr) {
		indent(ltr);
		
		ltr.type = null;
		
		System.out.print("null literal");
	}
	

	@Override
	public void visit(AST_TYPE n) throws Exception {
		indent(n);
		
		if(n!=null && !isTypeDefined(n)){
			throw new Exception("semantic checker line: 589");
		}
		
		System.out.print(n.toString());
	}

	@Override
	public void visit(AST_PROGRAM program) throws Exception {
		if(program.cdl!=null)
			visit(program.cdl);
		else
			System.out.println("\nEmpty program - no classes declared.");
	}
	
	public boolean isCircularInheritance(String name, String parentName){
		
		if(parentName==null)
			return false;
		
		if(name.equals(parentName))
			return true;
		
		for(AST_CLASS_DECL cd : classDeclList.classes){
			if(parentName.equals(cd.name))
				return isCircularInheritance(name, cd.parentName);
		}
		
		return false;
	}

	
	
	public void verifyMainMethod() throws Exception{
		
		
		int mainCount = 0;
		
		for(AST_CLASS_DECL cd1 : classDeclList.classes){
			if(cd1.elements!=null && cd1.elements.elems!=null)
				for(AST_CLASS_ELEMENT elem : cd1.elements.elems){
					
					if(!(elem instanceof AST_CLASS_ELEMENT_METHOD))
						continue;
					
					AST_CLASS_ELEMENT_METHOD mtd = (AST_CLASS_ELEMENT_METHOD)elem;
					
					if(!mtd.name.name.equals("main"))
						continue;
					
					
					if(mtd.tp!=null)
						continue;
					
					if(mtd.fr==null || mtd.fr.fmls.size()!=1)
						continue;
					
					if(!mtd.fr.fmls.get(0).name.name.equals("args"))
						continue;
					
					if(mtd.fr.fmls.get(0).tp.dim != 1)
						continue;
					
					if(!mtd.fr.fmls.get(0).tp.name.equals("string"))
						continue;
					
					
					mainCount++;
					
				}
		}
		
		
		if(mainCount!=1)
			throw new Exception();
		
		
		
	}
	
	@Override
	public void visit(AST_CLASS_DECL_LIST classDeclList) throws Exception {
		
		currentScope = MAIN_SCOPE;
		String[] field1 = {SCOPE_LIMIT, SCOPE_LIMIT, currentScope, SCOPE_LIMIT};
		scopeTable.add(field1);
		
		
		this.classDeclList = classDeclList;
		
		verifyMainMethod();
		
		for(AST_CLASS_DECL cd : classDeclList.classes){
			
			classDecl = cd;
			
			currentScope = CLASS_SCOPE;
			
			
			
			int cntAppearances=0;
			for(AST_CLASS_DECL cd1 : classDeclList.classes)
				if(cd.name.equals(cd1.name))
					cntAppearances++;
			
			if(cntAppearances!=1 || !(Character.isUpperCase(cd.name.charAt(0))))
				throw new Exception("semantic checker line: 643");
			
			if(isCircularInheritance(cd.name, cd.parentName))
				throw new Exception("semantic checker line: 646");
			
			if(cd.elements!=null && cd.elements.elems!=null)
				for(AST_CLASS_ELEMENT element  : cd.elements.elems){
					
					String name = "";
					
					AST_TYPE type = null;
					
					if(element==null)
						throw new Exception("semantic checker line: 655");
					
					if(element instanceof AST_CLASS_ELEMENT_FIELD){
						
						type = ((AST_CLASS_ELEMENT_FIELD)element).tp;
						if(!isTypeDefined(type))
							throw new Exception("semantic checker line: 661");
						
						for(int i=0; i<((AST_CLASS_ELEMENT_FIELD)element).ids.ids.size(); i++){
							
							name = ((AST_CLASS_ELEMENT_FIELD)element).ids.ids.get(i).name;
							
							cntAppearances = classDeclList.countAppearances(name, cd);
							
							if(cntAppearances!=1 || !(Character.isLowerCase(name.charAt(0))) || isReserved(name))
								throw new Exception("semantic checker line: 670");
						
							String[] field = {name, typeToString(type), currentScope, NOT_INITIATED};
							scopeTable.add(field);	
						}
					}
					
					else if(element instanceof AST_CLASS_ELEMENT_METHOD){
						
						name = ((AST_CLASS_ELEMENT_METHOD)element).name.name;
						
						
						
						boolean hasDifferentField = classDeclList.hasDifferentFieldOfSameName((AST_CLASS_ELEMENT_METHOD)element, cd);
						
						int numTimesInSameClass = cd.numTimesContained(((AST_CLASS_ELEMENT_METHOD)element).name.name);
						
						if(numTimesInSameClass!=1 || hasDifferentField || !(Character.isLowerCase(name.charAt(0))) || isReserved(name))
							throw new Exception("semantic checker line: 684");
						
						
						type = ((AST_CLASS_ELEMENT_METHOD)element).tp;
						if(!(type==null) && !isTypeDefined(type))
							throw new Exception("semantic checker line: 689");
						
						String[] field = {name, typeToString(type), currentScope, METHOD_DECLARATION};
						scopeTable.add(field);
					}
					else
						throw new Exception("semantic checker line: 695");
				}
			
			String[] field = {SCOPE_LIMIT, SCOPE_LIMIT, currentScope, SCOPE_LIMIT};
			scopeTable.add(field);
			currentScope = CLASS_SCOPE;
			
			visit(cd);
			popScope();
			
			classDecl = null;
			
		}
		
		popScope();
	}

	public String typeToString(AST_TYPE tp){
		if(tp==null)
			return "void";
		String str = tp.name;
		for(int i=0; i<tp.dim; i++)
			str+="[]";
		return str;
	}

	public boolean isTypeDefined(AST_TYPE tp){
		
		String[] arr = {"int", "string"};
		
		for(int i=0; i<arr.length; i++){
			if(arr[i].equals(tp.name))
				return true;
		}
		
		return isNonArrayTypeDefined(tp.name);
	}
	
	public boolean isNonArrayTypeDefined(String name){
		for(AST_CLASS_DECL acd : classDeclList.classes){
			if(name.equals(acd.name)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isReserved(String str){
		
		String[] arr = {"class", "extends", "static", "void", "int", "string",
				"return", "if", "else", "while", "break", "continue", "this",
						"new", "length", "true", "false", "null"};
		
		for(int i=0; i<arr.length; i++){
			if(arr[i].equals(str))
				return true;
		}
		return false;
	}
	
	@Override
	public void visit(AST_CLASS_DECL classDecl) throws Exception {
		indent(classDecl);
		
		System.out.print(classDecl.toString());
		
		indent++;
			
			
			
			if(classDecl.elements!=null)
				visit(classDecl.elements);
		
		indent--;
	}

	@Override
	public void visit(AST_CLASS_ELEMENT_LIST classElemList) throws Exception {
		for(AST_CLASS_ELEMENT ce:classElemList.elems)
			visit(ce);
		
		
		
	}

	@Override
	public void visit(AST_CLASS_ELEMENT classElem) throws Exception {
		// abstract
		if(classElem==null)
			throw new Exception("semantic checker line: 785");
		if(classElem instanceof AST_CLASS_ELEMENT_FIELD)
			visit((AST_CLASS_ELEMENT_FIELD)classElem);
		else if(classElem instanceof AST_CLASS_ELEMENT_METHOD)
			visit((AST_CLASS_ELEMENT_METHOD)classElem);
		else
			throw new Exception("semantic checker line: 791");

	}
	
	public void visit(AST_CLASS_ELEMENT_FIELD f) throws Exception {
		indent(f);
		System.out.print(f.toString());
		indent++;
			visit(f.tp);
			visit(f.ids);
		indent--;
	}
	
	public void visit(AST_CLASS_ELEMENT_METHOD m) throws Exception {
		indent(m);
		System.out.print(m.toString());
		indent++;
		
		currentScope = METHOD_SCOPE;
		
		String[] field = {SCOPE_LIMIT, SCOPE_LIMIT, currentScope, SCOPE_LIMIT};
		scopeTable.add(field);
		

		
		returnType = m.tp;
		
			if(m.tp!=null)
				visit(m.tp);
			visit(m.name);
			if(m.fr!=null){
				
				
				
				visit(m.fr);
				

				
			}
			if(m.stmts!=null){
				
				visit(m.stmts);
				
				
				
			}
			else if(returnType!=null)
				//throw new Exception();
			popScope();

		indent--;
	}
	
	
	public void popScope(){
		while(true){
			
			String[] scopeEntry = 	scopeTable.get(scopeTable.size()-1);
			scopeTable.remove(scopeTable.size()-1);
			
			if(scopeEntry[0] == SCOPE_LIMIT &&  scopeEntry[1] == SCOPE_LIMIT )
				
				break;
		}
	}
	
	
	@Override
	public void visit(AST_ID_LIST idList) throws Exception {
		for(AST_EXP_VAR_SIMPLE s:idList.ids){
			visit(s);
		}
	}

	@Override
	public void visit(AST_FORMAL_LIST formals) throws Exception {
		indent(formals);
		System.out.print(formals.toString());
		indent++;
			for(AST_FORMAL fm : formals.fmls){
				
				if(formals.timesContained(fm.name.name) != 1){
					throw new Exception("semantic checker line: 866");
				}
				if(!isTypeDefined(fm.tp)){
					throw new Exception("semantic checker line: 869");
				}
				String[] field = {fm.name.name, typeToString(fm.tp), currentScope, INITIATED};
				scopeTable.add(field);
				
				visit(fm);
			}
		indent--;
	}

	@Override
	public void visit(AST_FORMAL formal) throws Exception {
		indent(formal.tp);
		System.out.print(formal.toString());
		indent++;
			visit(formal.tp);
			visit(formal.name);
		indent--;
	}

	@Override
	public void visit(AST_EXP_LIST exprList) throws Exception {
		for(AST_EXP e:exprList.exprs){
			visit(e);
		}
	}

	@Override
	public void visit(AST_EXP_CALL call) throws Exception {
		if(call==null)
			throw new Exception("semantic checker line: 899");
		if(call instanceof AST_EXP_CALL_VIRTUAL)
			visit((AST_EXP_CALL_VIRTUAL)call);
		else
			throw new Exception("semantic checker line: 903");
	}
	
	
	public void visit(AST_EXP_CALL_VIRTUAL call) throws Exception {
		
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
				//visit(call.funcName);
			indent--;
			if(call.l!=null){
				indent(call.l);
				System.out.print("argument expression"+(call.l.exprs.size()>1?"s":"")+":");
				indent++;
					visit(call.l);
				indent--;
			}
		indent--;
		
		
		AST_CLASS_DECL cdl = null;
		
		if(call.object != null){
			if(call.object.type.dim > 0)
				throw new Exception("semantic checker line: 939");
			if(!isNonArrayTypeDefined(call.object.type.name))
				throw new Exception("semantic checker line: 941");
		}
		
		boolean isFound = false;
		
		String callObjectName = call.object==null?classDecl.name:call.object.type.name;
		
		for(AST_CLASS_DECL cd : classDeclList.classes){
			
			
			if(!stringTypeEquals(cd.name, callObjectName))
				continue;
			else
				cdl = cd;
		
			if(cdl!=null && cdl.elements!=null)
				for(AST_CLASS_ELEMENT elem : cdl.elements.elems){
					if(!(elem!=null && (elem instanceof AST_CLASS_ELEMENT_METHOD_VIRTUAL)))
						continue;
					
					AST_CLASS_ELEMENT_METHOD_VIRTUAL mtd = (AST_CLASS_ELEMENT_METHOD_VIRTUAL)elem;
					
					if(!mtd.name.name.equals(call.funcName.name))
						continue;
					
					isFound = true;
					
					AST_TYPE type = mtd.tp;
					
					if(type==null){
						if(!callCanBeVoid)
							throw new Exception();
					}
						
					if(type!=null){
						if(type instanceof AST_TYPE_PRIMITIVE){
							call.type = new AST_TYPE_PRIMITIVE(type.line, type.name, type.dim);
						}
						else if(type instanceof AST_TYPE_CLASS){
							call.type = new AST_TYPE_CLASS(type.line, type.name, type.dim);
						}
						else
							throw new Exception("semantic checker line: 972");
					}
					validateArgs(mtd.fr, call.l);
				}
		}
		if (!isFound)
			throw new Exception("semantic checker line: 978");
	}

	
	public void validateArgs(AST_FORMAL_LIST formals, AST_EXP_LIST exprs) throws Exception {
		
		if(formals==null && exprs == null)
			return;
		if((formals==null && exprs!=null) || (formals!=null && exprs==null))
			throw new Exception("semantic checker line: 987");
		
		if(formals.fmls==null || exprs.exprs==null)
			throw new Exception("semantic checker line: 990");
		
		if(formals.fmls.size() != exprs.exprs.size())
			throw new Exception("semantic checker line: 993");
		
		for(int i=0; i<formals.fmls.size(); i++){
			
			AST_TYPE formalType = formals.fmls.get(i).tp;
			
			AST_TYPE argType = exprs.exprs.get(i).type;
			
			if(!typeAssignable(formalType, argType))
				throw new Exception("semantic checker line: 1002");
			
			
		}
	}
	
	@Override
	public void visit(AST_EXP_VAR location) throws Exception {
		// abstract
		if(location==null)
			throw new Exception("semantic checker line: 1012");
		if(location instanceof AST_EXP_VAR_SIMPLE)
			visit((AST_EXP_VAR_SIMPLE)location);
		else if(location instanceof AST_EXP_VAR_FIELD)
			visit((AST_EXP_VAR_FIELD)location);
		else if(location instanceof AST_EXP_VAR_SUBSCRIPT)
			visit((AST_EXP_VAR_SUBSCRIPT)location);
		else
			throw new Exception("semantic checker line: 1020");
	}
	
	public void visit(AST_EXP_VAR_SIMPLE location) throws Exception{
		
		indent(location);
		
		if(!(Character.isLowerCase(location.name.charAt(0))) || isReserved(location.name))
			throw new Exception("semantic checker line: 1028");
		
		if(varShouldExist)
			if(!varExists(location.name))
				throw new Exception("semantic checker line: 1031");
		
		location.type = getTypeFromScope(location.name);
		
		System.out.print(location.toString());
		
	}
	
	
	public AST_TYPE getTypeFromScope(String name){
		for(int i = scopeTable.size()-1; i>=0; i--){
			if(scopeTable.get(i)[0]!=SCOPE_LIMIT && scopeTable.get(i)[0].equals(name)){
				
				
				if(scopeTable.get(i)[1].equals("void")){
					return null;
				}
				
				String type = scopeTable.get(i)[1];
				int dim = 0;
				while(type.endsWith("[]")){
					type = type.substring(0, type.length()-2);
					dim++;
				}
				
				if(type.equals("int") || type.equals("string")){
					return new AST_TYPE_PRIMITIVE(0,type,dim);
				}
				else{
					return new AST_TYPE_CLASS(0,type,dim);
				}
				
			}
		}
		return null;
	}
	
	public void visit(AST_EXP_VAR_FIELD location) throws Exception{
		
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
		
		String typeName = location.object.type.name;
		if(location.object.type.dim>0)
			throw new Exception("semantic checker line: 1086");
		
		boolean isFound = false;
		for(AST_CLASS_DECL acd : classDeclList.classes){
			if(stringTypeEquals(acd.name, typeName)){
				if(acd.elements!=null)
					for(AST_CLASS_ELEMENT elem : acd.elements.elems){
						if(!(elem instanceof AST_CLASS_ELEMENT_FIELD))
							continue;
	
						AST_CLASS_ELEMENT_FIELD field = (AST_CLASS_ELEMENT_FIELD)elem;
	
						for(int i=0; i<field.ids.ids.size(); i++){
							String id = field.ids.ids.get(i).name;
							if(!id.equals(location.name.name))
								continue;
							else{
								AST_TYPE tp = field.tp;
								if(tp==null)
									throw new Exception("semantic checker line: 1105");
								
								if(tp instanceof AST_TYPE_PRIMITIVE){
									location.type = new AST_TYPE_PRIMITIVE(tp.line, tp.name, tp.dim);
									isFound = true;
								}
								else if(tp instanceof AST_TYPE_CLASS){
									location.type = new AST_TYPE_CLASS(tp.line, tp.name, tp.dim);
									isFound = true;
								}
								else
									throw new Exception("semantic checker line: 1116");
							}
						}
					}
			}
		}
		if(!isFound)
			throw new Exception("semantic checker line: 1123");
	}
	
	public void visit(AST_EXP_VAR_SUBSCRIPT location) throws Exception{
		
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

		
		if(!location.where.type.name.equals("int") || location.where.type.dim > 0)
			throw new Exception("semantic checker line: 1145");
		
		if(location.arr.type.dim <= 0)
			throw new Exception("semantic checker line: 1148");
		
		AST_TYPE type = location.arr.type;
		
		if(type instanceof AST_TYPE_PRIMITIVE){
			location.type = new AST_TYPE_PRIMITIVE(type.line, type.name, type.dim-1);
		}
		else if(type instanceof AST_TYPE_CLASS){
			location.type = new AST_TYPE_CLASS(type.line, type.name, type.dim-1);
		}
		else
			throw new Exception("semantic checker line: 1159");
		
	}
}
















