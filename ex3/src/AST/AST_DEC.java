package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_DEC extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_Node child;
	public int i;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC(AST_Node child, int i, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		switch(i)
		{
			case(1):
				System.out.print("====================== dec --> varDec \n");
				break;
			case(2):
				System.out.print("====================== dec --> funcDec \n");
				break;
			case(3):
				System.out.print("====================== dec --> classDec \n");
				break;
			case(4):
				System.out.print("====================== dec --> arrayTypedef \n");
				break;
		}
		
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.child = child;
		this.i = i;
		this.line = line;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (child != null) child.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"DEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (child != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,child.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		if (this.i ==  2){
			AST_FUNCDEC func_child = (AST_FUNCDEC) child;
			func_child.visit(sym_table, null, false);
			return null;
		}
		this.child.visit(sym_table);
		return null;
	}
	
}
