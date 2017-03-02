import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import IR_NODES.Label;
import IR_NODES.Stringlit;
import IR_NODES.T_Alloc;
import IR_NODES.T_Binop;
import IR_NODES.T_Call;
import IR_NODES.T_Cjump;
import IR_NODES.T_Const;
import IR_NODES.T_ESeq;
import IR_NODES.T_Function;
import IR_NODES.T_JumpLabel;
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

public class IR_Generator {

	private PrintWriter writer;

	public void generateCode(T_exp irRoot, ArrayList<ArrayList<Object>> funcs, T_exp mainClass, String mainClassName, String outputFile)
			throws Exception {
		try {
			writer = new PrintWriter(outputFile, "UTF-8");
			writer.println(".data\n");
			generateVFT(funcs);	
			genStrings();
			writer.println(".text");
			writer.println("main:\n");
			for(int i=0;i<=7;i++)
				writer.println("\tli $t"+i+",0\n");
			Temp mainClassObject = irGenerator(mainClass);
			writer.println("\taddi $sp,$sp,-4\n");
			writer.println("\taddi $sp,$sp,-4\n");
			writer.println("\tsw " + mainClassObject + ",0($sp)\n");
			writer.println("\taddi $sp,$sp,-4\n");
			// Put return address here maybe.
			writer.println("\tj Label_0_" + mainClassName + "_main\n");
			irGenerator(irRoot);
			writer.println("Label_0_AccessViolation:\n");
			writer.println("\tli $a0,666\n");
			writer.println("\tli $v0,1\n");
			writer.println("\tsyscall\n");;
			writer.println("\tli $v0,10\n");
			writer.println("\tsyscall\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed generating code.");
		}
	}

	private void generateVFT(ArrayList<ArrayList<Object>> funcs) {
		for (ArrayList<Object> table : funcs) {
			String className = (String) table.get(0);
			if (table.size() > 1)
				writer.print("VFTable_" + className + ": .word ");
			for (int i = 1; i < table.size(); i++) {
				String[] tuple = (String[]) table.get(i);
				String funcName = tuple[0];
				writer.print("Label_0_" + tuple[1] + "_" + funcName);
				if (i + 1 < table.size())
					writer.print(",");
			}
			writer.println();
			writer.println();
		}
	}

	private void printChar(char c) {
		writer.println("\tli $a0," + (int) c + "\n");
		writer.println("\tli $v0,11\n");
		writer.println("\tsyscall\n");
	}
	
	private void genStrings() {
		for (Stringlit s : Stringlit.strList) {
			writer.println("String_"+s.getIndex()+": .asciiz \""+s.getStr()+"\"\n");
		}
	}
	
	private void printString(String s) {
		for (char c : s.toCharArray()) {
			printChar(c);
		}
	}

	private String IR_to_MIPS_binop(String op) {
		switch (op) {
		case "+":
			return "add";
		case "-":
			return "sub";
		case "*":
			return "mul";
		case "/":
			return "div";
		default:
			return null;
		}
	}

	public Temp irGenerator(T_exp t_exp) throws Exception {
		if (t_exp == null)
			throw new Exception("null node");
		if (t_exp instanceof T_Binop) {
			return irGenerator((T_Binop) (t_exp));
		} else if (t_exp instanceof T_Call) {
			return irGenerator((T_Call) (t_exp));
		} else if (t_exp instanceof T_Cjump) {
			return irGenerator((T_Cjump) (t_exp));
		} else if (t_exp instanceof T_Const) {
			return irGenerator((T_Const) (t_exp));
		} else if (t_exp instanceof T_Function) {
			return irGenerator((T_Function) (t_exp));
		} else if (t_exp instanceof T_JumpLabel) {
			return irGenerator((T_JumpLabel) (t_exp));
		} else if (t_exp instanceof T_Label) {
			return irGenerator((T_Label) (t_exp));
		} else if (t_exp instanceof T_exit) {
			return irGenerator((T_exit) (t_exp));
		} else if (t_exp instanceof T_Mem) {
			return irGenerator((T_Mem) (t_exp));
		}else if (t_exp instanceof T_String) {
			return irGenerator((T_String) (t_exp));
		} else if (t_exp instanceof T_Move) {
			return irGenerator((T_Move) (t_exp));
		} else if (t_exp instanceof T_JumpRegister) {
			return irGenerator((T_JumpRegister) (t_exp));
		} else if (t_exp instanceof T_Seq) {
			return irGenerator((T_Seq) (t_exp));
		}else if (t_exp instanceof T_StringConcat) {
			return irGenerator((T_StringConcat) (t_exp));
		} else if (t_exp instanceof T_ESeq) {
			return irGenerator((T_ESeq) (t_exp));
		} else if (t_exp instanceof T_PrintInt) {
			return irGenerator((T_PrintInt) (t_exp));
		} else if (t_exp instanceof T_Temp) {
			return irGenerator((T_Temp) (t_exp));
		} else if (t_exp instanceof T_Alloc) {
			return irGenerator((T_Alloc) (t_exp));
		} else {
			System.out.println(t_exp.getClass().getSimpleName());
			throw new Exception("????????????????????????");
		}
	}

	public Temp irGenerator(T_String t_String) {
		Temp t=new Temp();
		writer.println("\tla "+t+",String_"+t_String.getStr().getIndex()+"\n");
		return t;
	}

	public Temp irGenerator(T_Label t_label) throws Exception {
		writer.println(t_label.getLabel().toString() + ":");
		writer.println();
		return null;
	}

	public Temp irGenerator(T_Binop t_binop) throws Exception {
		String op = t_binop.getOp();
		// allocate a new temp for the result
		Temp t1 = new Temp();
		// move left operand to t2 temp
		Temp t2 = irGenerator(t_binop.getLeft());
		// move right operand to t3 temp
		Temp t3 = irGenerator(t_binop.getRight());
		// generate binop instructions

		String mips_op = IR_to_MIPS_binop(op);
		if (mips_op != null) {
			writer.println("\t" + mips_op + " " + t1 + "," + t2 + "," + t3);
			writer.println();
			return t1;
		}
		return null;
	}

	public Temp irGenerator(T_Const t_const) throws Exception {
		Temp r = new Temp();
		writer.println("\tli " + r + "," + t_const.getValue());
		writer.println();
		return r;
	}
	
	public Temp irGenerator(T_StringConcat t_exp) throws Exception {
		Temp t1=irGenerator(t_exp.getLeft());
		Temp t2=irGenerator(t_exp.getRight());
		Temp counter=new Temp();
		Temp c=new Temp();
		Temp b=new Temp();
		Temp zero=new Temp();
		writer.println("\tli "+counter+",0\n");
		writer.println("\tli "+zero+",0\n");
		Label start=new Label("LoopStart",false);
		Label cont=new Label("Cont",false);
		writer.println(start+":\n");
		writer.println("\tadd "+b+","+counter+","+t1+"\n");
		writer.println("\tlb "+c+",("+b+")\n");
		writer.println("\tbeq "+c+","+zero+","+cont+"\n");
		writer.println("\taddi "+counter+","+counter+",1\n");
		writer.println("\tj "+start+"\n");
		writer.println(cont+":\n");
		
		Temp counter2=new Temp();
		writer.println("\tli "+counter2+",0\n");
		Label start2=new Label("LoopStart",false);
		Label cont2=new Label("Cont",false);
		writer.println(start2+":\n");
		writer.println("\tadd "+b+","+counter2+","+t2+"\n");
		writer.println("\tlb "+c+",("+b+")\n");
		writer.println("\tbeq "+c+","+zero+","+cont2+"\n");
		writer.println("\taddi "+counter2+","+counter2+",1\n");
		writer.println("\tj "+start2+"\n");
		writer.println(cont2+":\n");
		
		Temp size=new Temp();
		writer.println("\tadd "+size+","+counter+","+counter2+"\n");
		writer.println("\taddi "+size+","+size+",1\n");
		writer.println("\taddi $a0,"+size+",0\n");
		writer.println("\tli $v0,9\n");
		writer.println("\tsyscall\n");
		Temp res=new Temp();
		writer.println("\taddi "+res+",$v0,0\n");
		
		Temp counter3=new Temp();
		writer.println("\tli "+counter3+",0\n");
		Label start3=new Label("LoopStart",false);
		Label cont3=new Label("Cont",false);
		writer.println(start3+":\n");
		writer.println("\tadd "+b+","+counter3+","+t1+"\n");
		writer.println("\tlb "+c+",("+b+")\n");
		writer.println("\tbeq "+c+","+zero+","+cont3+"\n");
		Temp d=new Temp();
		writer.println("\tadd "+d+","+counter3+","+res+"\n");
		writer.println("\tsb "+c+",("+d+")\n");
		writer.println("\taddi "+counter3+","+counter3+",1\n");
		writer.println("\tj "+start3+"\n");
		writer.println(cont3+":\n");
		
		Temp counter4=new Temp();
		writer.println("\tli "+counter4+",0\n");
		Label start4=new Label("LoopStart",false);
		Label cont4=new Label("Cont",false);
		writer.println(start4+":\n");
		writer.println("\tadd "+b+","+counter4+","+t2+"\n");
		writer.println("\tlb "+c+",("+b+")\n");
		writer.println("\tbeq "+c+","+zero+","+cont4+"\n");
		writer.println("\tadd "+d+","+counter+","+counter4+"\n");
		writer.println("\tadd "+d+","+d+","+res+"\n");
		writer.println("\tsb "+c+",("+d+")\n");
		writer.println("\taddi "+counter4+","+counter4+",1\n");
		writer.println("\tj "+start4+"\n");
		writer.println(cont4+":\n");
		
		writer.println("\taddi "+size+","+size+",-1\n");
		writer.println("\tadd "+b+","+size+","+res+"\n");
		writer.println("\tsb "+zero+",("+b+")\n");
		return res;
	}

	public Temp irGenerator(T_Mem t_Mem) throws Exception {
		Temp r = new Temp();
		Temp s = irGenerator(t_Mem.getChild());
		writer.println("\tlw " + r + ",0(" + s + ")");
		writer.println();
		return r;
	}

	public Temp irGenerator(T_Move t_move) throws Exception {
		if (t_move.getDst() instanceof T_Temp) {
			Temp src = irGenerator(t_move.getSrc());
			Temp dst = irGenerator(t_move.getDst());
			writer.println("\taddi " + dst + "," + src + ",0");
		}
		if (t_move.getDst() instanceof T_Mem) {	
			if (t_move.getSrc() instanceof T_Label) {
				Temp dst = irGenerator(((T_Mem) t_move.getDst()).getChild());
				T_Label tlabel = (T_Label) t_move.getSrc();
				Temp t = new Temp();
				writer.println("\tla " + t + "," + tlabel.getLabel().getName());
				writer.println();
				writer.println("\tsw " + t + ",0(" + dst + ")");
			} else {
				Temp src = irGenerator(t_move.getSrc());
				Temp dst = irGenerator(((T_Mem) t_move.getDst()).getChild());
				writer.println("\tsw " + src + ",0(" + dst + ")");
			}
		}
		writer.println();
		return null;
	}

	public Temp irGenerator(T_Seq t_seq) throws Exception {
		if (t_seq.getExpList() == null)
			System.out.println("No expressions in seq.");
		else {
			for (T_exp exp : t_seq.getExpList()) {
				irGenerator(exp);
			}
		}
		return null;
	}

	public Temp irGenerator(T_Function t_function) throws Exception {
		writer.println(t_function.getName() + ":");
		writer.println();
		writer.println("\taddi $sp,$sp,-4\n");
		writer.println("\tsw $fp,0($sp)\n");
		writer.println("\taddi $fp,$sp,0\n");
		irGenerator(t_function.getBody());
		Pattern r=Pattern.compile("(.*)_main");
		Matcher m=r.matcher(t_function.getName().getName());
		if(!m.matches())
			writer.println("\tjr $ra\n");
		else
		{
			writer.println("\tli $v0,10\n");
			writer.println("\tsyscall\n");
		}
		return null;
	}

	public Temp irGenerator(T_Cjump t_cjump) throws Exception {
		Label jumpIfTrue = t_cjump.getJumpIfTrue();
		Label jumpIfFalse = t_cjump.getJumpIfFalse();
		Temp left = irGenerator(t_cjump.getLeft());
		Temp right = irGenerator(t_cjump.getRight());
		if (t_cjump.getOp().equals("=="))
			writer.print("\tbeq ");
		else if (t_cjump.getOp().equals(">"))
			writer.print("\tbgt ");
		else if (t_cjump.getOp().equals("<"))
			writer.print("\tblt ");
		else if (t_cjump.getOp().equals(">="))
			writer.print("\tbge ");
		else if (t_cjump.getOp().equals("<="))
			writer.print("\tble ");
		else if (t_cjump.getOp().equals("!="))
			writer.print("\tbne ");
		else
			System.out.println("Op: "+t_cjump.getOp());
		writer.println(left + "," + right + "," + jumpIfTrue + "\n");
		writer.println("\tj " + jumpIfFalse);
		writer.println();
		return null;
	}

	public Temp irGenerator(T_ESeq t_eseq) throws Exception {
		if (t_eseq.getExpList() == null)
			System.out.println("No expressions in Eseq!!");
		else {
			for (T_exp exp : t_eseq.getExpList()) {
				irGenerator(exp);
			}
		}
		return irGenerator(t_eseq.getValue());
	}

	public Temp irGenerator(T_Alloc t_alloc) throws Exception {
		Temp t = new Temp();
		Temp size = irGenerator(t_alloc.getSize());
		writer.println("\taddi $a0," + size + ",0\n");
		writer.println("\tli $v0,9\n");
		writer.println("\tsyscall\n");
		writer.println("\taddi " + t + ",$v0,0");
		writer.println();
		return t;
	}

	public Temp irGenerator(T_Temp t_temp) throws Exception {
		return t_temp.getTemp();
	}

	public Temp irGenerator(T_PrintInt t_temp) throws Exception {
		writer.println("\taddi $a0," + irGenerator(t_temp.getValue()) + ",0\n");
		writer.println("\tli $v0,1\n");
		writer.println("\tsyscall\n");
		return null;
	}

	public Temp irGenerator(T_JumpRegister t_jump) throws Exception {
		writer.println("\tjr " + t_jump.getTemp());
		writer.println();
		return null;
	}

	public Temp irGenerator(T_exit t_exit) throws Exception {
		writer.println("\tli $v0,10\n");
		writer.println("\tsyscall\n");
		return null;
	}

	public Temp irGenerator(T_Call t_call) throws Exception {
		// Prologue:
		Temp sp = new Temp();
		writer.println("\taddi " + sp + ",$sp,0\n");
		ArrayList<T_exp> args = t_call.getArgs();
		for (int i = args.size() - 1; i >= 0; i--) {
			writer.println("\taddi $sp,$sp,-4\n");
			Temp t = irGenerator(args.get(i));
			writer.println("\tsw " + t + ",0($sp)\n");

		}
		Temp obj = irGenerator(t_call.getObject());
		Temp zero=new Temp();
		writer.println("\tli "+zero+",0\n");
		writer.println("\tbeq "+obj+","+zero+",Label_0_AccessViolation\n");
		writer.println("\taddi $sp,$sp,-4\n");
		writer.println("\tsw " + obj + ",0($sp)\n");
		writer.println("\taddi $sp,$sp,-4\n");
		writer.println("\tsw $ra,0($sp)\n");
		Temp vft = new Temp();
		writer.println("\tlw " + vft + ",0(" + obj + ")" + "\n");
		Temp func = new Temp();
		writer.println("\tlw " + func + "," + t_call.getOffset() * 4 + "(" + vft + ")\n");
		
		//Actual Call:
		writer.println("\taddi $a3,"+func+",0\n");
		writer.println("\tjalr $a3\n");


		// Epilogue:
		writer.println("\tlw $ra,4($fp)\n");
		writer.println("\tlw $fp,0($fp)\n");
		writer.println("\taddi $sp," + sp + ",0\n");
		Temp res = new Temp();
		writer.println("\taddi " + res + ",$a2,0\n");
		return res;

	}
}
