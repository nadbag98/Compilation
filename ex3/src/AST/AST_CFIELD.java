package AST;

public class AST_CFIELD extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_VARDEC_CLASS v;
  	public AST_FUNCDEC f;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CFIELD(AST_VARDEC_CLASS v, AST_FUNCDEC f, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (v != null && f == null) System.out.print("====================== cField --> varDec \n");
   		if (v == null && f != null) System.out.print("====================== cField --> funcDec \n");
		
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
    		this.f = f;
    		this.line = line;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST CFIELD */
		/**************************************/
		System.out.print("AST NODE CFIELD\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
    		if (f != null) f.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CFIELD\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
    		if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,f.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE_CLASS my_class) throws ArithmeticException {
		if (this.v != null){
			this.v.visit(sym_table, my_class);
		}
		if (this.f != null){
			this.f.visit(sym_table, my_class);
		}
		return null;
	}
	
}
