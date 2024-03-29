package AST;

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
	public AST_DEC(AST_Node child, int i)
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
	
}
