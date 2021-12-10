package AST;

public class AST_STMT_VARDEC extends AST_STMT
{
  public AST_VARDEC v;
  
  public AST_STMT_VARDEC(AST_VARDEC v, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (v != null) System.out.print("====================== stmt --> varDec\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STMT_VARDEC */
		/**************************************/
		System.out.print("AST NODE STMT_VARDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "STMT_VARDEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
		this.v.visit(sym_table);
		return null;
	}
  
}
