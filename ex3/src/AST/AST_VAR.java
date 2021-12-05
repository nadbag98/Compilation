package AST;

public class AST_VAR extends AST_Node
{
  public AST_VAR v;
  public String s;
	public AST_EXP e;
  
  public AST_VAR(AST_VAR v, String s, AST_EXP e)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (v == null && e == null && s != null) System.out.print("====================== var --> ID\n");
		if (v != null && e == null && s != null) System.out.print("====================== var --> var ID\n");
		if (v != null && e != null && s == null) System.out.print("====================== var --> var exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
		this.s = s;
    this.e = e;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST VAR */
		/**************************************/
		System.out.print("AST NODE VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,"VAR\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
  
}
