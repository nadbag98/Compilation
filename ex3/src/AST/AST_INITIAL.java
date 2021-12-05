package AST;

public class AST_INITIAL extends AST_Node
{
	public AST_PRO pro;
	public int line;
  
  public AST_INITIAL(AST_PRO pro, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (pro != null) System.out.print("====================== init -> pro\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.pro = pro;
		this.line = line;
		System.out.print("HERERERERERER: ");
		System.out.print(String.format("%d\n", this.line));
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST INITIAL */
		/**************************************/
		System.out.print("AST NODE INITIAL\n");
		System.out.print(String.format("%d", this.line));

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (pro != null) pro.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"INITIAL\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (pro != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,pro.SerialNumber);
	}
  
}