package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_ID extends AST_STMT
{
  public String s;
  public AST_EXP_LIST l;
  
  public AST_STMT_ID(String s, AST_EXP_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (s == null && l == null) System.out.print("====================== stmt --> RETURN SEMICOLON\n");
    if (s != null && l == null) System.out.print("====================== stmt --> ID();\n");
    if (s != null && l != null) System.out.print("====================== stmt --> ID(expList);\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
    this.s = s;
    this.l = l;

	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST AST_STMT_ID */
		/**************************************/
		System.out.print("AST NODE AST_STMT_ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
    System.out.print(s);
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "AST_STMT_ID\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);

	}
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
		if (this.s == null && this.l == null){
			if (returnType != TYPE_VOID.getInstance()){
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}
		
		if (this.s != null) { 				
			TYPE t1 = sym_table.searchAll(this.s);
			if (t1 == null || !t1.isFunc()){
				throw new ArithmeticException(String.format("%d", this.line));
			}
			TYPE_FUNCTION func = (TYPE_FUNCTION) t1;
			
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
		
		return null;
	}
  
}
