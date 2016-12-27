import AST.*;
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import src.ClassChecker;
import src.SymbolTable;
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];

		try
		{
			SymbolTable.init();
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);


			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);

			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l);

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			AST_PROGRAM program = (AST_PROGRAM) p.parse().value;

		//	while (program != null)
		//	{
			//	System.out.print(program.PrintMe());
				//System.out.print("\n");
			//	program = program.rest;
		//	}
			program.isValid();
			/**************************/
			/* [10] Close output file */
			/**************************/

			file_writer.print("OK");
			file_writer.close();



    	}

		catch (Exception e)
		{
			try{
			file_writer = new PrintWriter(outputFilename);
			file_writer.print("fail");
			file_writer.close();
			e.printStackTrace();	
			}
			catch(Exception d){
				d.printStackTrace();

			}
		}
	}
}
