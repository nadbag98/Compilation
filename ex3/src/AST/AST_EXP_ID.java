package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_EXP_ID extends AST_EXP
{
  public String i;
  public AST_EXP_LIST l;
  
  public AST_EXP_ID(String i, AST_EXP_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (l == null) System.out.print("====================== exp --> ID\n");
    if (l != null) System.out.print("====================== exp --> ID expList\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.i = i;
		this.l = l;
		this.line = line;
	}
  
  	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP_ID */
		/**************************************/
		System.out.print("AST NODE EXP_ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP_ID\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		TYPE t1 = sym_table.searchAll(this.i);
		if (t1 == null || !t1.isFunc()){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		TYPE_FUNCTION func = (TYPE_FUNCTION) t1;

		if (this.l == null) {
			if (func.params.head != null){
				throw new ArithmeticException(String.format("%d", this.line));
			}			
		} 
		else {
			TYPE_LIST lst = new TYPE_LIST(null, null);
			this.l.visit(sym_table, lst);
			if (!func.params.equalsOrFamily(lst)) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}
		return func.returnType;
		
	}
  
}
