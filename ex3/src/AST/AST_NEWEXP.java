package AST;

public class AST_NEWEXP extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_TYPE t;
  	public AST_EXP e;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_NEWEXP(AST_TYPE t, AST_EXP e, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (e == null) System.out.print("====================== newExp --> NEW TYPE\n");
    		if (e != null) System.out.print("====================== newExp --> NEW TYPE(exp)\n");
		
		
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
    		this.e = e;
		this.line = line-1;
    
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
    		if (t != null) t.PrintMe();
		if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "NEWEXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
}
