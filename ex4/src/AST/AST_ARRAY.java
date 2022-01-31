package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_ARRAY extends AST_Node
{
  public String s;
	public AST_TYPE t;
  
  public AST_ARRAY(String s,AST_TYPE t, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== arrayTypedef --> []ID = type();\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s = s;
		this.t = t;
		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE arrayTypedef\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		System.out.print("ARRAY ");
		System.out.print(s);
		System.out.print(" EQ ");
		if (t != null) t.PrintMe();
		System.out.print(" LBRACK RBRACK SEMICOLON");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("arrayTypedef\n%s\n", s));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		if (sym_table.searchCurrScope(this.s)){
			throw new ArithmeticException(String.format("%d", this.line)); 
		}
		TYPE t1 = sym_table.findType(this.t.s);
		if (t1 == null || t1 == TYPE_VOID.getInstance()) {
			throw new ArithmeticException(String.format("%d", this.line));
		}
		TYPE t2 = new TYPE_ARRAY(t1, this.s);
		sym_table.enter(this.s, t2, null);
		return null;
	}
	
	public TEMP IRme(){
		return null;
	}
  
}
