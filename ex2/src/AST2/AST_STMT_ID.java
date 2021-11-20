package AST;

public abstract class AST_STMT_ID extends AST_STMT
{
  public String s;
  public AST_EXP_LIST l;
  
  public AST_STMT_ID(String s, AST_EXP_LIST l)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (s == null && l == null) System.out.print("====================== stmt --> RETURN SEMICOLON\n");
    if (s != null && l == null) System.out.print("====================== stmt --> ID();\n");
    if (s != null && l != null) System.out.print("====================== stmt --> ID(expList);\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
    this.s = s;
    this.l = l;

	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST AST_STMT_ID */
		/**************************************/
		System.out.print("AST NODE AST_STMT_ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
    System.out.print(s);
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "AST_STMT_ID\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);

	}
  
}
