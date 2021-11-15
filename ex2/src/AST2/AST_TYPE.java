package AST;

public class AST_TYPE extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String s;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_TYPE(String s)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (s != null) System.out.format("====================== TYPE --> ( %s ) \n", s);
		
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s = s;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE TYPE\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("TYPE\n(%s)",s));
	}
	
}
