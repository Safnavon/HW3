

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import AST.AST_EXP_NEW_CLASS;
import AST.AST_PROGRAM;
import IR_NODES.T_exp;
import java_cup.runtime.Symbol;

/** The entry point of the SLP (Straight Line Program) application.
 *
 */
public class Main {
	private static boolean printtokens = false;
	
	/** Reads an SLP and pretty-prints it.
	 * 
	 * @param args Should be the name of the file containing an SLP.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		AST_PROGRAM root = null;
		try{
			if (args.length < 2) {
				System.out.println("Error: Missing argument!");
				System.out.println("Usage: COMPILER <input-filename> <output-filename>");
				System.exit(-1);
			}

			FileWriter outFile = new FileWriter(args[1]);
			
			try {

				
				// Parse the input file
				FileReader txtFile = new FileReader(args[0]);
				Lexer scanner = new Lexer(txtFile);
				Parser parser = new Parser(scanner);
				parser.printTokens = printtokens;
				
				Symbol parseSymbol = parser.parse();
				System.out.println("Parsed " + args[0] + " successfully!");
				root = (AST_PROGRAM) parseSymbol.value;
				
				
				// Pretty-print the program to System.out
				/*System.out.print("\nAbstract Syntax Tree: " + args[0] + "\n");
				Printer printer = new Printer(root);
				printer.print();
				
				outFile.write("OK");
				outFile.close();
				System.out.println("\nOK");*/
				
				SemanticChecker smtck = new SemanticChecker(root);
				if(!smtck.check()){
					throw new Exception();
				}
				else{
					outFile.write("OK");
					outFile.close();
					System.out.println("\nALL OK");
				}
				
				
			} catch (Exception e) {
				outFile.write("FAIL");
				outFile.close();
				System.out.println("\nFAIL");
				return;
			}
			
		} catch (Exception e) {
			System.out.print(e);
		}
		
		IR ir=new IR();
		T_exp IRRoot=ir.visit(root);
		ArrayList<ArrayList<Object>> funcs=ir.funcTables;
		AST_EXP_NEW_CLASS mainClass=new AST_EXP_NEW_CLASS(-1,ir.mainClass);
		T_exp mainClassNode=ir.visit(mainClass);
		IR_Generator irg=new IR_Generator();
		irg.generateCode(IRRoot, funcs, mainClassNode,ir.mainClass, args[1]);
		System.out.println("Seems good");
	}
}