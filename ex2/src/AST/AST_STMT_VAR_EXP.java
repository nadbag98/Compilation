package AST;

public class AST_STMT_VAR_EXP extends AST_STMT
{
  public AST_VAR v;
  public AST_EXP e;
  public AST_NEWEXP ne;
  
  public AST_STMT_VAR_EXP(AST_VAR v, AST_EXP e, AST_NEWEXP ne)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (e != null && ne == null) System.out.print("====================== stmt --> var exp\n");
    if (e == null && ne != null) System.out.print("====================== stmt --> var newExp\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
    this.e = e;
    this.ne = ne;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST AST_STMT_VAR_EXP */
		/**************************************/
		System.out.print("AST NODE AST_STMT_VAR_EXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
    if (e != null) e.PrintMe();
    if (ne != null) ne.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "AST_STMT_VAR_EXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
    if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
    if (ne != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,ne.SerialNumber);
	}
  
}
