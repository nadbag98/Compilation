package AST;

public class AST_NEWEXP extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_TYPE t;
  	public AST_EXP e;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_NEWEXP(AST_TYPE t, AST_EXP e, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (e == null) System.out.print("====================== newExp --> NEW TYPE\n");
    		if (e != null) System.out.print("====================== newExp --> NEW TYPE(exp)\n");
		
		
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
    		this.e = e;
		this.line = line;
    
    }

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST NEWEXP */
		/**************************************/
		System.out.print("AST NODE NEWEXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
    		if (t != null) t.PrintMe();
		if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "NEWEXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		TYPE t1 = sym_table.findType(this.t.s);
		
		if (null == t1){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		if (this.e != null) {
			if (!t1.isArray()) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
			TYPE t2 = this.e.visit(sym_table);
			if (t2 != TYPE_INT.getInstance() || !this.e.isValidForArray) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
			
		}
		else {
			if (!t1.isClass()) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}
		
		return t1;			
	}
	
}
