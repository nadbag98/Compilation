package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_VAR extends AST_Node
{
  	public AST_VAR v;
  	public String s;
	public AST_EXP e;
  
  public AST_VAR(AST_VAR v, String s, AST_EXP e, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (v == null && e == null && s != null) System.out.print("====================== var --> ID\n");
		if (v != null && e == null && s != null) System.out.print("====================== var --> var ID\n");
		if (v != null && e != null && s == null) System.out.print("====================== var --> var exp\n");

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
		/* AST NODE TYPE = AST VAR */
		/**************************************/
		System.out.print("AST NODE VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,"VAR\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		if (this.v == null && this.e == null) {
			TYPE t1 = sym_table.searchAll(this.s);
			if (t1 == null) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
			return t1;
		}
		TYPE t1 = this.v.visit(sym_table);
		if (this.e == null) {
			if (!t1.isClass()) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
			TYPE t2 = sym_table.searchFamily(this.s, (TYPE_CLASS) t1);
			if (t2 == null) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
			return t2;
		}
		if (!t1.isArray()) {
			throw new ArithmeticException(String.format("%d", this.line));
		}
		TYPE t2 = this.e.visit(sym_table);
		if (t2 != TYPE_INT.getInstance()) {
			throw new ArithmeticException(String.format("%d", this.line));
		}
		TYPE_ARRAY t1_array = (TYPE_ARRAY) t1;
		return t1_array.arrayType;
		
	}
  
}
