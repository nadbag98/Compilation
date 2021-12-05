package AST;

public class AST_FUNCDEC extends AST_Node
{

  public AST_TYPE t;
  public String s;
	public AST_TYPE_LIST l1;
  public AST_STMT_LIST l2;
  
  public AST_FUNCDEC(AST_TYPE t, String s, AST_TYPE_LIST l1, AST_STMT_LIST l2)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (l1 != null) System.out.print("====================== funcDec --> 1/2\n");
		if (l1 == null) System.out.print("====================== funcDec --> 2/2\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.s = s;
    this.l1 = l1;
    this.l2 = l2;
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
		if (l1 != null) l1.PrintMe();
    if (l2 != null) l2.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("funcDec\n%s\n", s));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
    if (l1 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l1.SerialNumber);
    if (l2 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l2.SerialNumber);
	}
  
}
