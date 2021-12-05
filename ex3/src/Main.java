   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
import SYMBOL_TABLE.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_INITIAL AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);
			
			/******************************/
			/* [2] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [3] Initialize a new parser */
			/*******************************/
			p = new Parser(l, outputFilename);

			/***********************************/
			/* [4] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_INITIAL) p.parse().value;
			
			SYMBOL_TABLE sym_table = getInstance();

			String output = "OK";

			try 
			{
				AST.visit(sym_table);
			}
			catch (ArithmeticException e)
			{
				output = "ERROR(" + e.getMessage() + ")";
			}


			/********************************/
			/* [5] Write OK */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			file_writer.print(output);
			file_writer.close();
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			/**************************/
			/* [7] Semant the AST ... */
			/**************************/
			AST.SemantMe();
			
			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
