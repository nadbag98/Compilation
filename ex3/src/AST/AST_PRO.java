package AST;

public class AST_PRO extends AST_Node
{
  public AST_DEC dec;
	public AST_PRO pro;
  
  public AST_PRO( AST_DEC dec,AST_PRO pro, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (pro == null) System.out.print("====================== pro -> dec\n");
		if (pro != null) System.out.print("====================== pro -> dec pro\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.pro = pro;
		this.dec = dec;
		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST PRO */
		/**************************************/
		System.out.print("AST NODE PRO\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (dec != null) dec.PrintMe();
		if (pro != null) pro.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"PRO\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,dec.SerialNumber);
		if (pro != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,pro.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		this.dec.visit(sym_table);
		this.pro.visit(sym_table);
		return null;
	}
  
}

