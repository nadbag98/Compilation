package AST;

public class AST_VARDEC_CLASS extends AST_Node
{
  	public AST_TYPE t;
    public String s;
    public String s2;
    public int i;
  	public int rule;

  
  public AST_VARDEC_CLASS(AST_TYPE t, String s, String s2, int i, int rule, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    
		System.out.print("if there a problem come to vardec_class");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.s = s;
    this.s2 = s2;
	    this.rule = rule;
	    this.i = i;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VARDEC_CLASS\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (t != null) t.PrintMe();
		

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("VARDEC_CLASS\n%s\n", s));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
  
	    if (sym_table.searchCurrScope(this.s)){
				throw new ArithmeticException(String.format("%d", this.line)); 
			}

	    TYPE t1 = findType(this.t.s);
	    if (null == t1){
				throw new ArithmeticException(String.format("%d", this.line));
			}

	    switch(rule){
	      case(1):
		  if (t1 != TYPE_STRING.getInstance()){
		    throw new ArithmeticException(String.format("%d", this.line));
		  }
		  break;
	      case(2):
		  if (t1 != TYPE_INT.getInstance()){
		    throw new ArithmeticException(String.format("%d", this.line));
		  }
		  break;
	      case(3):
		  if (t1 == TYPE_INT.getInstance() || t1 == TYPE_STRING.getInstance()){
		    throw new ArithmeticException(String.format("%d", this.line));
		  }
	    }  
	   

	    sym_table.enter(this.s, t1);	 
      
	}
  
}
