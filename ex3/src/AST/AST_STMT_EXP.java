package AST;

public class AST_STMT_EXP extends AST_STMT
{
  public AST_EXP e;
  public AST_STMT_LIST l;
  public int is_while;
  
  public AST_STMT_EXP(AST_EXP e, AST_STMT_LIST l, int is_while, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (e == null) System.out.print("====================== stmt --> RETURN exp SEMICOLON\n");
    if (e != null && is_while == 0) System.out.print("====================== stmt --> IF (exp) {stmtList}\n");
    if (e != null && is_while == 1) System.out.print("====================== stmt --> WHILE (exp) {stmtList}\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
    this.e = e;
    this.l = l;
    this.is_while = is_while;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST AST_STMT_EXP */
		/**************************************/
		System.out.print("AST NODE AST_STMT_EXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (l != null) l.PrintMe();
    if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "AST_STMT_EXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
    if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);

	}
  
}
