package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_VARDEC_CLASS extends AST_Node
{
    public AST_TYPE t;
    public String s;
    public String s2;
    public int i;
    public int rule;
    public int offset;

  
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
    
		System.out.print("vardec_class init");

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
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE_CLASS my_class) throws ArithmeticException {
  
  		  // TODO check duplicate name in same scope	
	    if (sym_table.searchCurrScope(this.s)){
				throw new ArithmeticException(String.format("%d", this.line)); 
			}

	    TYPE t1 = sym_table.findType(this.t.s);
	    if (null == t1 || t1 == TYPE_VOID.getInstance()){
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
	    
	  
	   
	   // check ancestors for contradictions:
	   TYPE_CLASS ancestor = my_class.father;
	   DATA_MEMBER dup;
	   while(ancestor != null)
	   {
	   	dup = ancestor.data_members.find(this.s);
		if (dup != null && dup.t != t1)
		{
			throw new ArithmeticException(String.format("%d", this.line));
		}
		ancestor = ancestor.father;
	   }
	   DATA_MEMBER d = new DATA_MEMBER(t1, this.s);
 	   my_class.data_members.insert(d);
	   if (t1 == TYPE_INT.getInstance() || t1 == TYPE_STRING.getInstance())
	   {
	   	sym_table.enter(this.s, t1, null);
	   } 
	   else {
	 	sym_table.enter(this.s, TYPE_INSTANCE.getInstance(), t1);
	   }
	   
	   this.offset = sym_table.get_class_offset();
	   sym_table.top.offset = this.offset;		
		
	return null;
	}
  
}
