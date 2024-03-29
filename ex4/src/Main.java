import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
import IR.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class Main
{
  static public void main(String argv[])
  {
    Lexer l;
    Parser p;
    Symbol s;
    AST_INITIAL AST_I;
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
      p = new Parser(l, outputFilename);

      /***********************************/
      /* [5] 3 ... 2 ... 1 ... Parse !!! */
      /***********************************/
      AST_I = (AST_INITIAL) p.parse().value;
      
      /*************************/
      /* [6] Print the AST ... */
      /*************************/
      AST_I.PrintMe();

      /**************************/
      /* [7] Semant the AST ... */
      /**************************/
      SYMBOL_TABLE sym_table = SYMBOL_TABLE.getInstance();
      AST_I.visit(sym_table);

      /**********************/
      /* [8] IR the AST ... */
      /**********************/
      AST_I.IRme();
      
      /***********************/
      /* [9] MIPS the IR ... */
      /***********************/
      IR.getInstance().MIPSme();

      /**************************************/
      /* [10] Finalize AST GRAPHIZ DOT file */
      /**************************************/
      AST_GRAPHVIZ.getInstance().finalizeFile();      

      /***************************/
      /* [11] Finalize MIPS file */
      /***************************/
      MIPSGenerator.getInstance().finalizeFile();
      
      GRAPH graph = new GRAPH();
      graph.finalize(file_writer);

      /**************************/
      /* [12] Close output file */
      /**************************/
      file_writer.close();
      }
           
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}

