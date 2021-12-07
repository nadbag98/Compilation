package AST;

public class AST_VARDEC extends AST_Node
{
  	public AST_TYPE t;
	public AST_Node exp;
  	public String s;
  	public int is_new;
  
  
  public AST_VARDEC(AST_TYPE t, String s, AST_Node exp, int is_new, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (exp != null){
			if (is_new == 0) System.out.print("====================== varDec --> type ID := exp;");
			if (is_new == 1) System.out.print("====================== varDec --> type ID := newExp;");
		}
		if (exp == null) System.out.print("====================== varDec --> type ID;");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.s = s;
	    this.exp = exp;
	    this.is_new = is_new;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VARDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (t != null) t.PrintMe();
		System.out.print(s);
		if (exp != null){
			System.out.print(" := ");
			exp.PrintMe();
		}
		System.out.print(";");
		

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("VARDEC\n%s\n", s));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		if (sym_table.searchCurrScope(this.s)){
			throw new ArithmeticException(String.format("%d", this.line)); 
		}
		
		TYPE t1 = sym_table.findType(this.t.s);
		
		if (null == t1){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		if (null != this.exp){
			TYPE t2 = this.exp.visit(sym_table);
			if (is_new == 0){
				if (!sym_table.checkInheritance(t1, t2.t)){
					throw new ArithmeticException(String.format("%d", this.line));
				}
			}
			if (is_new == 1){
				if (!sym_table.checkInheritance(t1, t2)){
					throw new ArithmeticException(String.format("%d", this.line));
				}
			}
		}
		
		sym_table.enter(this.s, t1.getInstance());			
	}
  
}
