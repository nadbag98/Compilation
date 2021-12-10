package AST;

public class AST_STMT_VAR_ID extends AST_STMT
{
  public AST_VAR v;
  public String s;
  public AST_EXP_LIST e;
  
  public AST_STMT_VAR_ID(AST_VAR v, String s, AST_EXP_LIST e, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (e == null) System.out.print("====================== stmt --> var id\n");
    if (e != null) System.out.print("====================== stmt --> var id expList\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
    this.s = s;
    this.e = e;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST AST_STMT_VAR_ID */
		/**************************************/
		System.out.print("AST NODE AST_STMT_VAR_ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		    System.out.print(s);
		    if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "AST_STMT_VAR_ID\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
    		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
		
		TYPE t1 = this.v.visit(sym_table);
		if (t1 == null || !t1.isClass()){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		TYPE t2 = sym_table.searchFamily(this.s, (TYPE_CLASS)t1);
		
		if (t2 == null || !t2.isFunc()){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		TYPE_FUNCTION func = (TYPE_FUNCTION) t2;
		
		if (e == null){
			if (func.params.head != null){
				throw new ArithmeticException(String.format("%d", this.line));
			}			
		} else {
			TYPE_LIST lst = new TYPE_LIST(null, null);
			this.e.visit(sym_table, lst);
			if (!lst.equals(func.params)){
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}

		return func.returnType;
	}
	
	
  
}
