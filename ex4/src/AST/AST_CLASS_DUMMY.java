package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_CLASS_DUMMY extends AST_Node
{
  
  public AST_CLASS_DUMMY(int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
   System.out.print("====================== CLASS -> class dummy\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP_REST */
		/**************************************/

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		System.out.print("AST NODE class dummy\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "CLASS_DUMMY\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
	}
	
  
}
