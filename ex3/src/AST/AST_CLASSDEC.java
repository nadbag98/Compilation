package AST;

public class AST_CLASSDEC extends AST_Node
{
  public String s1;
  public String s2;
  public AST_CFIELD_LIST l;
  
  
  public AST_CLASSDEC(String s1, String s2, AST_CFIELD_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (s2 != null) System.out.print("====================== varDec --> 1/2\n");
		if (s2 == null) System.out.print("====================== varDec --> 2/2\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s1 = s1;
		this.s2 = s2;
    		this.l = l;
		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST CLASSDEC */
		/**************************************/
		System.out.print("AST NODE CLASSDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "CLASSDEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		if (sym_table.searchCurrScope(this.s1)){
			throw new ArithmeticException(String.format("%d", this.line)); 
		}
		

		
		TYPE t1 = new TYPE_CLASS(null, this.s1, null);
		if (this.s2 != null){
			t1.father = sym_table.find(this.s2);
			if (t1.father == null){
				throw new ArithmeticException(String.format("%d", this.line)); 
			}
		}
		t1.data_members = new TYPE_LIST(null, null);
		
		sym_table.beginScope();
		visit(this.l, t1);
		sym_table.endScope();
		sym_table.enter(this.s, t1);	
		
	}
  	
  
}
