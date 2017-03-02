import java.util.ArrayList;
import java.util.Arrays;

import AST.AST_CLASS_DECL;
import AST.AST_CLASS_DECL_LIST;
import AST.AST_CLASS_ELEMENT;
import AST.AST_CLASS_ELEMENT_FIELD;
import AST.AST_CLASS_ELEMENT_METHOD_VIRTUAL;
import AST.AST_EXP;
import AST.AST_EXP_BINOP;
import AST.AST_EXP_CALL;
import AST.AST_EXP_CALL_VIRTUAL;
import AST.AST_EXP_LITERAL_INT;
import AST.AST_EXP_LITERAL_NULL;
import AST.AST_EXP_LITERAL_STRING;
import AST.AST_EXP_NEW_ARR;
import AST.AST_EXP_NEW_CLASS;
import AST.AST_EXP_PARENTHESES;
import AST.AST_EXP_VAR_FIELD;
import AST.AST_EXP_VAR_SIMPLE;
import AST.AST_EXP_VAR_SUBSCRIPT;
import AST.AST_FORMAL;
import AST.AST_PROGRAM;
import AST.AST_STMT;
import AST.AST_STMTS_LIST;
import AST.AST_STMT_ASSIGN;
import AST.AST_STMT_CALL;
import AST.AST_STMT_IF;
import AST.AST_STMT_LIST;
import AST.AST_STMT_RETURN;
import AST.AST_STMT_VAR;
import AST.AST_STMT_WHILE;
import AST.AST_TYPE;
import IR_NODES.Label;
import IR_NODES.Stringlit;
import IR_NODES.T_Alloc;
import IR_NODES.T_Binop;
import IR_NODES.T_Call;
import IR_NODES.T_Cjump;
import IR_NODES.T_Const;
import IR_NODES.T_ESeq;
import IR_NODES.T_Function;
import IR_NODES.T_JumpRegister;
import IR_NODES.T_Label;
import IR_NODES.T_Mem;
import IR_NODES.T_Move;
import IR_NODES.T_PrintInt;
import IR_NODES.T_Seq;
import IR_NODES.T_String;
import IR_NODES.T_StringConcat;
import IR_NODES.T_Temp;
import IR_NODES.T_exit;
import IR_NODES.T_exp;
import IR_NODES.Temp;

public class IR {

	String mainClass;
	String currentFunction;

	class var {
		String name, type;
		int scope, offset;

		public var(String name, int scope, int offset, String type) {
			this.name = name;
			this.scope = scope;
			this.offset = offset;
			this.type = type;
		}

		public String toString() {
			return "(" + name + ", " + scope + ", " + offset + ", " + type + ")";
		}
	}

	public void printFuncTable() {
		System.out.print("[");
		for (ArrayList<Object> table : funcTables) {
			System.out.print("[");
			for (Object tuple : table) {
				if (tuple instanceof String) {
					System.out.print(tuple);
				} else {
					System.out.print(Arrays.toString((String[]) tuple));
				}
				System.out.print(", ");
			}
			System.out.print("],");
		}
		System.out.print("]");
		System.out.println();
	}

	private static int scope, offset, loop;
	private static String currentClass;
	ArrayList<ArrayList<Object>> classes = new ArrayList<>(); // List of list of
																// classes and
																// their fields.
	ArrayList<ArrayList<Object>> funcTables = new ArrayList<>(); // List of list
																	// of
																	// classes
																	// and their
																	// functions.
	private static ArrayList<var> varTable;

	private ArrayList<Object> getTable(String className) {
		for (ArrayList<Object> t : classes) {
			if (t.get(0).equals(className)) {
				return t;
			}
		}
		return null;
	}

	private ArrayList<Object> getFuncTable(String className) {
		for (int i = 0; i < funcTables.size(); i++) {
			if (funcTables.get(i).get(0).equals(className))
				return funcTables.get(i);
		}
		return null;
	}

	private int getFuncIndex(ArrayList<Object> funcT, String funcName) {
		for (int i = 1; i < funcT.size(); i++) {
			if (((String[]) funcT.get(i))[0].equals(funcName))
				return i;
		}
		return -1;
	}

	private void exitScope() {
		for (int i = varTable.size() - 1; i >= 0; i--) {
			if (varTable.get(i).scope == scope) {
				varTable.remove(i);
			}
		}
		scope--;
	}

	private String getType(AST_EXP expr) {
		if (expr instanceof AST_EXP_VAR_SIMPLE) {
			AST_EXP_VAR_SIMPLE simpleVar = (AST_EXP_VAR_SIMPLE) expr;
			for (int i = varTable.size() - 1; i >= 0; i--) {
				var v = varTable.get(i);
				if (v.name.equals(simpleVar.name))
					return v.type;
			}
			return null;
		} else if (expr instanceof AST_EXP_VAR_FIELD) {
			AST_EXP_VAR_FIELD varField = (AST_EXP_VAR_FIELD) expr;
			String objType = getType(varField.object);
			ArrayList<Object> fieldTable = getTable(objType);
			for (int i = 1; i < fieldTable.size(); i++) {
				String[] pair = (String[]) fieldTable.get(i);
				if (pair[0].equals(varField.name.name))
					return pair[1];
			}
			return null;
		} else if (expr instanceof AST_EXP_VAR_SUBSCRIPT) {
			AST_EXP_VAR_SUBSCRIPT varSub = (AST_EXP_VAR_SUBSCRIPT) expr;
			return getType(varSub.arr);
		} else if (expr instanceof AST_EXP_CALL)
			return getType((AST_EXP_CALL_VIRTUAL) expr);
		else if (expr instanceof AST_EXP_CALL_VIRTUAL) {
			AST_EXP_CALL_VIRTUAL call = (AST_EXP_CALL_VIRTUAL) expr;
			String objType;
			if (call.object != null)
				objType = getType(call.object);
			else
				objType = currentClass;
			ArrayList<Object> funcTable = getFuncTable(objType);
			int index = getFuncIndex(funcTable, call.funcName.name);
			return ((String[]) funcTable.get(index))[2];
		} else if (expr instanceof AST_EXP_NEW_CLASS)
			return ((AST_EXP_NEW_CLASS) expr).className;
		else if (expr instanceof AST_EXP_NEW_ARR)
			return expr.type.name;
		else if (expr instanceof AST_EXP_BINOP)
			return getType(((AST_EXP_BINOP) expr).left);
		else if (expr instanceof AST_EXP_LITERAL_INT)
			return "int";
		else if (expr instanceof AST_EXP_LITERAL_STRING)
			return "string";
		else if (expr instanceof AST_EXP_LITERAL_NULL)
			return null;
		else if (expr instanceof AST_EXP_PARENTHESES)
			return getType(((AST_EXP_PARENTHESES) expr).expr);
		return null;
	}

	private var getVar(String varName) {
		for (int i = varTable.size() - 1; i >= 0; i--) {
			var temp = varTable.get(i);
			if (temp.name.equals(varName))
				return temp;
		}
		return null;
	}

	public T_exp visit(AST_STMTS_LIST stmts) {
		if (stmts == null) {
			return new T_Seq(null);
		} else {
			ArrayList<T_exp> sequence = new ArrayList<>();
			for (AST_STMT s : stmts.statements) {
				sequence.add(visit(s));
			}
			return new T_Seq(sequence);
		}
	}

	public T_exp visit(AST_STMT stmt) {
		if (stmt instanceof AST_STMT_ASSIGN)
			return visit((AST_STMT_ASSIGN) stmt);
		else if (stmt instanceof AST_STMT_CALL)
			return visit((AST_STMT_CALL) stmt);
		else if (stmt instanceof AST_STMT_IF)
			return visit((AST_STMT_IF) stmt);
		else if (stmt instanceof AST_STMT_LIST)
			return visit((AST_STMT_LIST) stmt);
		else if (stmt instanceof AST_STMT_RETURN)
			return visit((AST_STMT_RETURN) stmt);
		else if (stmt instanceof AST_STMT_VAR)
			return visit((AST_STMT_VAR) stmt);
		else if (stmt instanceof AST_STMT_WHILE)
			return visit((AST_STMT_WHILE) stmt);
		return null;
	}

	public T_exp visit(AST_STMT_VAR stmt) {
		offset++;
		varTable.add(new var(stmt.varName.name, scope, offset, stmt.tp.name));
		ArrayList<T_exp> seq = new ArrayList<>();
		T_Binop temp = new T_Binop("+", new T_Temp(new Temp("sp")), new T_Const(-4));

		T_Move first = new T_Move(new T_Temp(new Temp("sp")), temp);
		if (loop > 0) {
			Label firstLoop = new Label("FirstLoop", false);
			Label after = new Label("After", false);

			seq.add(new T_Cjump("==", new T_Temp(new Temp("t" + loop)), new T_Const(0), firstLoop, after));
			seq.add(new T_Label(firstLoop));
			seq.add(first);
			seq.add(new T_Label(after));
		} else {
			seq.add(first);
		}
		T_exp temp2;
		if (stmt.rhs == null)
			temp2 = new T_Const(0);
		else
			temp2 = visit(stmt.rhs);
		T_Move second = new T_Move(new T_Mem(new T_Binop("+", new T_Temp(new Temp("fp")), new T_Const(offset * (-4)))),
				temp2);
		seq.add(second);
		return new T_Seq(seq);
	}

	public T_exp visit(AST_STMT_LIST stmt) {
		scope++;
		T_exp node = visit(stmt.sl);
		exitScope();
		return node;

	}

	public T_exp visit(AST_STMT_ASSIGN stmt) {
		T_Move node = new T_Move(visit(stmt.loc), visit(stmt.rhs));
		return node;

	}

	public T_exp visit(AST_STMT_CALL stmt) {
		return visit(stmt.call);

	}

	public T_exp visit(AST_STMT_IF stmt) {
		Label ifTrue = new Label("True", false);
		Label ifFalse = new Label("False", false);

		ArrayList<T_exp> sequence = new ArrayList<>();
		if (!(stmt.cond instanceof AST_EXP_BINOP)) {
			sequence.add(new T_Cjump("==", visit(stmt.cond), new T_Const(1), ifTrue, ifFalse));
		} else {
			AST_EXP_BINOP cond = (AST_EXP_BINOP) stmt.cond;
			String binop = cond.op.op;
			System.out.println("Op: "+binop);
			if (binop.equals("*") || binop.equals("+") || binop.equals("-") || binop.equals("/")){
				sequence.add(new T_Cjump("==", visit(stmt.cond), new T_Const(1), ifTrue, ifFalse));
			}
			else{
				sequence.add(new T_Cjump(cond.op.op, visit(cond.left), visit(cond.right), ifTrue, ifFalse));
			}
		}
		sequence.add(new T_Label(ifTrue));
		sequence.add(visit(stmt.ifStmt));
		sequence.add(new T_Label(ifFalse));
		return new T_Seq(sequence);
	}
	
	private T_Cjump genCjump(AST_EXP cond, Label ifTrue,Label ifFalse){
		T_Cjump cjump;
		if (!(cond instanceof AST_EXP_BINOP)) {
			cjump=new T_Cjump("==", visit(cond), new T_Const(1), ifTrue, ifFalse);
		} else {
			AST_EXP_BINOP condBinop = (AST_EXP_BINOP) cond;
			String binop = condBinop.op.op;
			if (binop.equals("*") || binop.equals("+") || binop.equals("-") || binop.equals("/")){
				cjump=new T_Cjump("==", visit(condBinop), new T_Const(1), ifTrue, ifFalse);
			}
			else{
				cjump=(new T_Cjump(binop, visit(condBinop.left), visit(condBinop.right), ifTrue, ifFalse));
			}
		}
		return cjump;
		
	}
	
	public T_exp visit(AST_STMT_WHILE stmt) {
		loop++;
		Label ifTrue = new Label("True", false);
		Label ifFalse = new Label("False", false);

		ArrayList<T_exp> sequence = new ArrayList<>();
		T_exp cjump=genCjump(stmt.cond,ifTrue,ifFalse);
		sequence.add(cjump);
		sequence.add(new T_Label(ifTrue));
		sequence.add(visit(stmt.stmt));
		sequence.add(new T_Move(new T_Temp(new Temp("t" + loop)), new T_Const(1)));
		cjump=genCjump(stmt.cond,ifTrue,ifFalse);
		sequence.add(cjump);
		sequence.add(new T_Label(ifFalse));
		sequence.add(new T_Move(new T_Temp(new Temp("t" + loop)), new T_Const(0)));
		loop--;
		return new T_Seq(sequence);
	}

	public T_exp visit(AST_EXP expr) {
		if (expr instanceof AST_EXP_VAR_SIMPLE)
			return visit((AST_EXP_VAR_SIMPLE) expr);
		else if (expr instanceof AST_EXP_VAR_FIELD)
			return visit((AST_EXP_VAR_FIELD) expr);
		else if (expr instanceof AST_EXP_VAR_SUBSCRIPT)
			return visit((AST_EXP_VAR_SUBSCRIPT) expr);
		else if (expr instanceof AST_EXP_CALL_VIRTUAL)
			return visit((AST_EXP_CALL_VIRTUAL) expr);
		else if (expr instanceof AST_EXP_NEW_CLASS)
			return visit((AST_EXP_NEW_CLASS) expr);
		else if (expr instanceof AST_EXP_NEW_ARR)
			return visit((AST_EXP_NEW_ARR) expr);
		else if (expr instanceof AST_EXP_BINOP)
			return visit((AST_EXP_BINOP) expr);
		else if (expr instanceof AST_EXP_LITERAL_INT)
			return visit((AST_EXP_LITERAL_INT) expr);
		else if (expr instanceof AST_EXP_LITERAL_STRING)
			return visit((AST_EXP_LITERAL_STRING) expr);
		else if (expr instanceof AST_EXP_LITERAL_NULL)
			return visit((AST_EXP_LITERAL_NULL) expr);
		else if (expr instanceof AST_EXP_PARENTHESES)
			return visit((AST_EXP_PARENTHESES) expr);
		return null;

	}

	public T_exp visit(AST_EXP_VAR_SIMPLE expr) {
		var v = getVar(expr.name);
		if (v.scope == 0) {
			T_Mem temp = new T_Mem(new T_Binop("+", new T_Temp(new Temp("fp")), new T_Const(8)));
			return new T_Mem(new T_Binop("+", temp, new T_Const(v.offset * 4)));
		} else if (v.scope == 1) {
			return new T_Mem(new T_Binop("+", new T_Temp(new Temp("fp")), new T_Const((v.offset + 2) * 4)));
		} else if (v.scope >= 2) {
			return new T_Mem(new T_Binop("+", new T_Temp(new Temp("fp")), new T_Const(v.offset * (-4))));
		}

		System.out.println("Tried to access var with scope <0");
		return null;

	}

	public T_exp visit(AST_EXP_NEW_CLASS expr) {
		ArrayList<T_exp> expList = new ArrayList<>();
		Temp temp = new Temp();
		int classSize = getTable(expr.className).size();
		T_Move node = new T_Move(new T_Temp(temp), new T_Alloc(new T_Const(classSize * 4)));
		expList.add(node);
		T_exp vftNode;
		if (getFuncTable(expr.className) != null && getFuncTable(expr.className).size() > 1) {
			Label vft = new Label("VFTable_" + expr.className, true);
			vftNode = new T_Label(vft);
		} else {
			vftNode = new T_Const(0);
		}
		node = new T_Move(new T_Mem(new T_Temp(temp)), vftNode);
		expList.add(node);
		for (int i = 1; i < classSize; i++) {
			T_Mem tempNode = new T_Mem(new T_Binop("+", new T_Temp(temp), new T_Const(4 * i)));
			node = new T_Move(tempNode, new T_Const(0));
			expList.add(node);
		}
		return new T_ESeq(expList, new T_Temp(temp));
	}

	public T_exp visit(AST_EXP_CALL_VIRTUAL expr) {
		String objType;
		if (expr.object == null) {
			objType = "this";
		} else {
			objType = getType(expr.object);
		}
		T_exp obj;
		if (!objType.equals("this"))
			obj = visit(expr.object);
		else {
			obj = new T_Mem(new T_Binop("+", new T_Temp(new Temp("fp")), new T_Const(8)));
			objType = currentClass;
		}
		//printFuncTable();
		int foffset = getFuncIndex(getFuncTable(objType), expr.funcName.name);
		foffset--;
		ArrayList<T_exp> args = new ArrayList<>();
		if (expr.l != null) {
			for (AST_EXP e : expr.l.exprs) {
				args.add(visit(e));
			}
		}
		if (expr.funcName.name.equals("printInt"))
			return new T_PrintInt(visit(expr.l.exprs.get(0)));
		else
			return new T_Call(obj, foffset, args);
	}

	public T_exp visit(AST_EXP_LITERAL_INT expr) {
		return new T_Const(expr.i);
	}
	
	public T_exp visit(AST_EXP_LITERAL_STRING expr) {
		Stringlit str=new Stringlit(expr.str);
		Stringlit.strList.add(str);
		return new T_String(str);
	}

	public T_exp visit(AST_EXP_LITERAL_NULL expr) {
		return new T_Const(0);
	}

	public T_exp visit(AST_EXP_BINOP expr) {
		System.out.println(expr.op.op);
		if(expr.op.op.equals("+") && getType(expr.left).equals("string")){
			return new T_StringConcat(visit(expr.left),visit(expr.right));
		}else
			return new T_Binop(expr.op.op, visit(expr.left), visit(expr.right));

	}

	public T_exp visit(AST_EXP_PARENTHESES expr) {
		return visit(expr.expr);
	}

	public T_exp visit(AST_EXP_VAR_SUBSCRIPT expr) {
		T_Binop offset = new T_Binop("*", new T_Binop("+", visit(expr.where), new T_Const(1)), new T_Const(4));
		T_Binop binop = new T_Binop("+", visit(expr.arr), offset);
		ArrayList<T_exp> exps = new ArrayList<>();
		Label av = new Label("AccessViolation", true);
		av.setIndex(0);
		Label cont1 = new Label("ContA", false);
		exps.add(new T_Cjump("==", visit(expr.arr), new T_Const(0), av, cont1));
		exps.add(new T_Label(cont1));
		Label cont2 = new Label("ContB", false);
		exps.add(new T_Cjump(">=", visit(expr.where), new T_Mem(visit(expr.arr)), av, cont2));
		exps.add(new T_Label(cont2));
		return new T_Mem(new T_ESeq(exps, binop));
	}

	public T_exp visit(AST_EXP_VAR_FIELD expr) {
		String objType = getType(expr.object);
		ArrayList<Object> fieldTable = getTable(objType);
		int fieldOffset = -1;
		for (int i = 1; i < fieldTable.size(); i++) {
			String[] pair = (String[]) fieldTable.get(i);
			if (pair[0].equals(expr.name.name))
				fieldOffset = i;
		}
		if (fieldOffset == -1) {
			System.out.println("Tried to access nonexistent field");
			return null;
		}
		ArrayList<T_exp> exps = new ArrayList<>();
		Label av = new Label("AccessViolation", true);
		av.setIndex(0);
		Label cont = new Label("ContC", false);
		exps.add(new T_Cjump("==", visit(expr.object), new T_Const(0), av, cont));
		exps.add(new T_Label(cont));
		T_Binop binop = new T_Binop("+", visit(expr.object), new T_Const(fieldOffset * 4));
		return new T_Mem(new T_ESeq(exps, binop));

	}

	public T_exp visit(AST_EXP_NEW_ARR expr) {
		// Allocating the array:
		ArrayList<T_exp> expList = new ArrayList<>();
		Temp x = new Temp(); // array location
		Temp y = new Temp(); // size
		ArrayList<T_exp> expList2 = new ArrayList<>();
		expList2.add(new T_Move(new T_Temp(y), visit(expr.sz)));
		T_Binop bin=new T_Binop("+", new T_Temp(y), new T_Const(1));
		T_ESeq size = new T_ESeq(expList2, new T_Binop("*",bin,new T_Const(4)));
		T_Move node = new T_Move(new T_Temp(x), new T_Alloc(size));
		expList.add(node);

		// Putting the size of the array in the first spot.
		node = new T_Move(new T_Mem(new T_Temp(x)), new T_Temp(y));
		expList.add(node);

		// initializing the array:
		Temp count = new Temp();
		node = new T_Move(new T_Temp(count), new T_Const(1));
		expList.add(node);
		Label loop = new Label("Loop", false);
		Label exit = new Label("Exit", false);
		T_Cjump cjump = new T_Cjump(">", new T_Temp(count), new T_Temp(y), exit, loop);
		expList.add(cjump);

		expList.add(new T_Label(loop));

		T_Binop address = new T_Binop("+", new T_Temp(x), new T_Binop("*", new T_Temp(count), new T_Const(4)));
		node = new T_Move(new T_Mem(address), new T_Const(0));
		expList.add(node);

		node = new T_Move(new T_Temp(count), new T_Binop("+", new T_Temp(count), new T_Const(1)));
		expList.add(node);
		expList.add(cjump);
		expList.add(new T_Label(exit));

		return new T_ESeq(expList, new T_Temp(x));
	}

	/*
	 * public T_exp visit(AST_TYPE n) { // TODO Auto-generated method stub
	 * 
	 * }
	 */

	public T_exp visit(AST_PROGRAM program) {
		return visit(program.cdl);

	}

	public T_exp visit(AST_CLASS_DECL_LIST classDeclList) {
		ArrayList<T_exp> list = new ArrayList<>();
		for (AST_CLASS_DECL cd : classDeclList.classes) {
			varTable = new ArrayList<var>();
			scope = 0;
			list.add(visit(cd));
		}
		return new T_Seq(list);

	}

	public T_exp visit(AST_CLASS_DECL classDecl) {
		currentClass = classDecl.name;
		ArrayList<Object> fieldTable;
		String parent = classDecl.parentName;
		if (parent == null) {
			fieldTable = new ArrayList<Object>();
			fieldTable.add(classDecl.name);
		} else {
			ArrayList<Object> parentTable = getTable(parent);
			fieldTable = new ArrayList<Object>(parentTable);
			fieldTable.set(0, classDecl.name);
		}
		if (classDecl.elements != null) {
			for (AST_CLASS_ELEMENT elem : classDecl.elements.elems) {
				if (elem instanceof AST_CLASS_ELEMENT_FIELD) {
					for (AST_EXP_VAR_SIMPLE id : ((AST_CLASS_ELEMENT_FIELD) elem).ids.ids) {
						String[] pair = new String[2];
						pair[0] = id.name;
						pair[1] = id.type.name;
						fieldTable.add(pair);
					}
				}
			}
		}

		classes.add(fieldTable);
		for (int i = 1; i < fieldTable.size(); i++) {
			String[] pair = (String[]) fieldTable.get(i);
			varTable.add(new var(pair[0], scope, i, pair[1]));
		}

		ArrayList<T_exp> funcList = new ArrayList<>();
		ArrayList<Object> funcTable;
		if (parent == null) {
			funcTable = new ArrayList<>();
			funcTable.add(classDecl.name);
		} else {
			funcTable = new ArrayList<>(getFuncTable(parent));
			funcTable.set(0, classDecl.name);
		}
		funcTables.add(funcTable);
		if (classDecl.elements != null) {
			for (AST_CLASS_ELEMENT elem : classDecl.elements.elems) {
				if (elem instanceof AST_CLASS_ELEMENT_METHOD_VIRTUAL) {
					String name = ((AST_CLASS_ELEMENT_METHOD_VIRTUAL) elem).name.name;
					if (name.equals("main"))
						mainClass = classDecl.name;
					Label labelName = new Label(classDecl.name + "_" + name, true);
					AST_CLASS_ELEMENT_METHOD_VIRTUAL method = (AST_CLASS_ELEMENT_METHOD_VIRTUAL) elem;
					scope = 1;
					int i = 1;
					if (method.fr != null) {
						for (AST_FORMAL arg : method.fr.fmls) {
							varTable.add(new var(arg.name.name, scope, i, arg.tp.name));
							i++;
						}
					}

					int index = getFuncIndex(funcTable, name);
					if (index == -1) {
						String[] pair = new String[3];
						pair[0] = name;
						pair[1] = classDecl.name;
						AST_TYPE funType = ((AST_CLASS_ELEMENT_METHOD_VIRTUAL) elem).tp;
						if (funType == null)
							pair[2] = "null";
						else
							pair[2] = funType.name;
						funcTable.add(pair);
					} else {
						String[] pair = (String[]) funcTable.get(index);
						String[] newpair = pair.clone();
						newpair[1] = classDecl.name;
						funcTable.set(index, newpair);
					}

					offset = 0;
					loop = 0;
					scope = 2;
					currentFunction = name;
					funcList.add(new T_Function(visit(method.stmts), labelName));
					scope = 1;
					exitScope();

				}
			}
		}

		return new T_Seq(funcList);
	}

	/*
	 * public T_exp visit(AST_CLASS_ELEMENT_LIST classElemList){ // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * 
	 * public T_exp visit(AST_CLASS_ELEMENT classElem){ // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

	public T_exp visit(AST_STMT_RETURN stmt) {

		if (currentFunction.equals("main")) {
			return new T_exit();
		} else {
			if (stmt.e != null) {
				ArrayList<T_exp> sequence = new ArrayList<>();
				sequence.add(new T_Move(new T_Temp(new Temp("a2")), visit(stmt.e)));
				sequence.add(new T_JumpRegister(new Temp("ra")));
				return new T_Seq(sequence);
			} else {
				return new T_JumpRegister(new Temp("ra"));
			}
		}
	}

	/*
	 * public T_exp visit(AST_ID_LIST idList) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 */

	/*
	 * public T_exp visit(AST_FORMAL_LIST formals) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 * 
	 * public T_exp visit(AST_FORMAL formal) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 */

	/*
	 * public T_exp visit(AST_EXP_LIST exprList) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 */

}
