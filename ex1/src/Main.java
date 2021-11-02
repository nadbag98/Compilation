   
import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		String tokenName;
		boolean is_error = false;
		boolean withValue = false;
		boolean notFirst = false;
		try
		{
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

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			while (s.sym != TokenNames.EOF && !is_error)
			{
				
				/*********************/
				/* [7] Print to file */
				/*********************/
				
				switch (s.sym) {
					case 1:
						tokenName = "PLUS";
						break;
					case 2:
						tokenName = "MINUS";
						break;
					case 3:
						tokenName = "TIMES";
						break;
					case 4:
						tokenName = "DIVIDE";
						break;
					case 5:
						tokenName = "LPAREN";
						break;
					case 6:
						tokenName = "RPAREN";
						break;
					case 7:
						tokenName = "NUMBER";
						withValue = true;
						break;
					case 8:
						tokenName = "ID";
						withValue = true;
						break;
					case 9:
						tokenName = "LBRACK";
						break;
					case 10:
						tokenName = "RBRACK";
						break;
					case 11:
						tokenName = "LBRACE";
						break;
					case 12:
						tokenName = "RBRACE";
						break;
					case 13:
						tokenName = "NIL";
						break;
					case 14:
						tokenName = "COMMA";
						break;
					case 15:
						tokenName = "DOT";
						break;
					case 16:
						tokenName = "SEMICOLON";
						break;
					case 17:
						tokenName = "ASSIGN";
						break;
					case 18:
						tokenName = "EQ";
						break;
					case 19:
						tokenName = "LT";
						break;
					case 20:
						tokenName = "GT";
						break;
					case 21:
						tokenName = "ARRAY";
						break;
					case 22:
						tokenName = "CLASS";
						break;
					case 23:
						tokenName = "EXTENDS";
						break;
					case 24:
						tokenName = "RETURN";
						break;
					case 25:
						tokenName = "WHILE";
						break;
					case 26:
						tokenName = "IF";
						break;
					case 27:
						tokenName = "NEW";
						break;
					case 29:
						tokenName = "STRING";
						withValue = true;
						break;
					case 31:
						tokenName = "TYPE_INT";
						break;
					case 32:
						tokenName = "TYPE_STRING";
						break;
					case 28:
						tokenName = "INT";
						if (Integer.parseInt(s.value.toString()) < 32768){
							withValue = true;
							break; 
						}
					default:
						tokenName = "ERROR";
						file_writer.close();
						file_writer = new PrintWriter(outputFilename);
						file_writer.print("ERROR");
						is_error = true;
				}
				if (!is_error) {
					if (notFirst){
						file_writer.print("\n");
					}
					file_writer.print(tokenName);
					if (withValue) {
						file_writer.print("(");
						file_writer.print(s.value);
						file_writer.print(")");
					}
					file_writer.print("[");
					file_writer.print(l.getLine());
					file_writer.print(",");
					file_writer.print(l.getTokenStartPosition());
					file_writer.print("]");
				}
				/***********************/
				/* [8] Read next token */
				/***********************/
				withValue = false;
				notFirst = true;
				s = l.next_token();
			}
			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


