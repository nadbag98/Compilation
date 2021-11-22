package AST;

public class AST_NEWEXP extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String i;
  public AST_EXP e;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_NEWEXP(String i, AST_EXP e)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (e == null) System.out.print("====================== newExp --> NEW ID\n");
    if (e != null) System.out.print("====================== newExp --> NEW ID(exp)\n");
		
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.i = i;
    this.e = e
    
    }

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST NEWEXP */
		/**************************************/
		System.out.print("AST NODE NEWEXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
    Syste.out.print(i);
		if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "NEWEXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
}
