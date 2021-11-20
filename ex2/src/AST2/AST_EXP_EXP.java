package AST;

public abstract class AST_EXP_EXP extends AST_EXP
{
  public AST_EXP e1;
	public AST_BINOP b;
  public AST_EXP e2;
  
  public AST_EXP_EXP(AST_EXP e1, AST_BINOP b, AST_EXP e2)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (b == null) System.out.print("====================== exp --> exp\n");
    if (b != null) System.out.print("====================== exp --> exp binop exp\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.e1 = e1;
		this.b = b;
    this.e2 = e2;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP_EXP */
		/**************************************/
		System.out.print("AST NODE EXP_EXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (e1 != null) v.PrintMe();
		if (b != null) l.PrintMe();
    if (e2 != null) v.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP_EXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (e1 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e1.SerialNumber);
		if (e2 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e2.SerialNumber);
	}
  
}
