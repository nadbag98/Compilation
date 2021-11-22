package AST;

public class AST_VARDEC extends AST_Node
{
  public AST_TYPE t;
	public AST_Node exp;
  public String s;
  
  public AST_VARDEC(AST_TYPE t, String s, AST_Node exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (exp != null) System.out.print("====================== varDec --> 2/3\n");
		if (exp == null) System.out.print("====================== varDec --> 1\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.s = s;
    this.exp = exp;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VARDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (t != null) t.PrintMe();
		if (exp != null) exp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("VARDEC\n%s\n", s));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
	}
  
}
