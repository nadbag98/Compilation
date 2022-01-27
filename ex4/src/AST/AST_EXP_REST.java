package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_EXP_REST extends AST_EXP
{
  public String s;
  
  public AST_EXP_REST(String s, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (s == null) System.out.print("====================== exp --> NIL\n");
    if (s != null) System.out.print("====================== exp --> STRING\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s = s;
		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP_REST */
		/**************************************/
		System.out.print("AST NODE EXP_REST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (s != null) System.out.print(s);
    if (s == null) System.out.print("NIL");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP_REST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		System.out.print("Visiting AST_EXP_REST\n");
	
		if (this.s == null){
			return TYPE_NIL.getInstance();
		} else {
			return TYPE_STRING.getInstance();
		}
	}
	
	public TEMP IRme(){
		TEMP res = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR inst = IR.getInstance();
		if (this.s == null) {
			inst.Add_IRcommand(new IRcommand_Li(res, 0));
		} 
		else {
			String str_lab = IRcommand.getFreshLabel("str"); 
			inst.Add_IRcommand(new IRcommand_Allocate_String(str_lab, this.s));
			inst.Add_IRcommand(new IRcommand_La(res, str_lab));
		}
		return res;
	}
  
}
