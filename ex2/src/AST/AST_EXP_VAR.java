package AST;

public abstract class AST_EXP_VAR extends AST_EXP
{
  public AST_VAR v;
	public String s;
  public AST_EXP_LIST l;
  
  public AST_EXP_VAR(AST_VAR v, String s, AST_EXP_LIST l)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (s == null) System.out.print("====================== exp --> var\n");
		if (l != null && s != null) System.out.print("====================== exp --> var id expList\n");
		if (l == null && s != null) System.out.print("====================== exp --> var id\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
		this.s = s;
    this.l = l;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE EXP_VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	}
  
}
