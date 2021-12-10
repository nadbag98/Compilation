package AST;

public class AST_STMT_VAR_EXP extends AST_STMT
{
  public AST_VAR v;
  public AST_EXP e;
  public AST_NEWEXP ne;
  
  public AST_STMT_VAR_EXP(AST_VAR v, AST_EXP e, AST_NEWEXP ne, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

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
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
		TYPE t1 = this.v.visit(sym_table);
		TYPE t2;
		if (this.e != null){
			t2 = this.e.visit(sym_table);
		} else {
			t2 = this.ne.visit(sym_table);
		}
		if (!sym_table.checkInheritance(t1, t2)){
			throw new ArithmeticException(String.format("%d", this.line)); 
		}	
		
		return null;
	}
  
}
