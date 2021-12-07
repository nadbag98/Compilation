package AST;

public class AST_CLASSDEC extends AST_Node
{
  public String s1;
  public String s2;
  public AST_CFIELD_LIST l;
  
  
  public AST_CLASSDEC(String s1, String s2, AST_CFIELD_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (s2 != null) System.out.print("====================== varDec --> 1/2\n");
		if (s2 == null) System.out.print("====================== varDec --> 2/2\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s1 = s1;
		this.s2 = s2;
    		this.l = l;
		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST CLASSDEC */
		/**************************************/
		System.out.print("AST NODE CLASSDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "CLASSDEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	}
  
}
