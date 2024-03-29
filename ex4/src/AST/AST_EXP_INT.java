package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_EXP_INT extends AST_EXP
{
  public int i;
  public int is_neg;
  
  
  public AST_EXP_INT(int i, int is_neg, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (is_neg == 0) System.out.print("====================== exp --> INT\n");
    if (is_neg == 1) System.out.print("====================== exp --> MINUS INT \n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.i = i;
		this.is_neg = is_neg;
		this.line = line;
		if (i == 0){
			this.isZero = 1;
			this.isValidForArray = false;
		}
		if (is_neg == 1){
			this.isValidForArray = false;
		}

	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP_INT */
		/**************************************/
		System.out.print("AST NODE EXP_INT\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (is_neg == 0) System.out.print(String.format("%d\n", i));
    if (is_neg == 1) System.out.print(String.format("-%d\n", i));

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP_INT\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		return TYPE_INT.getInstance();
	}
	
	public TEMP IRme(){
		TEMP res = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR inst = IR.getInstance();
		if (this.is_neg == 0) {
			inst.Add_IRcommand(new IRcommand_Li(res, this.i));
		} 
		else {
			inst.Add_IRcommand(new IRcommand_Li(res, -this.i));
		}
		return res;
	}
  
}
