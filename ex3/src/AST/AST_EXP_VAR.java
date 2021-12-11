package AST;

public class AST_EXP_VAR extends AST_EXP
{
  public AST_VAR v;
	public String s;
  public AST_EXP_LIST l;
  
  public AST_EXP_VAR(AST_VAR v, String s, AST_EXP_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (s == null) System.out.print("====================== exp --> var\n");
		if (l != null && s != null) System.out.print("====================== exp --> var id expList\n");
		if (l == null && s != null) System.out.print("====================== exp --> var id\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
		this.s = s;
    		this.l = l;
    		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE EXP_VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		
		TYPE t1 = this.v.visit(sym_table);
		
		if (this.s == null && this.l == null) {
			return t1;
		}
		
		if (t1 == null || !t1.isClass()){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		TYPE t2 = sym_table.searchFamily(this.s, (TYPE_CLASS) t1);
		
		if (t2 == null || !t2.isFunc()){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		TYPE_FUNCTION func = (TYPE_FUNCTION) t2;
		
		if (this.l == null){
			if (func.params.head != null){
				throw new ArithmeticException(String.format("%d", this.line));
			}			
		} else {
			TYPE_LIST lst = new TYPE_LIST(null, null);
			this.l.visit(sym_table, lst);
			if (!lst.equals(func.params)){
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}

		return func.returnType;
	}
  
}
