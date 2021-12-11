package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_EXP_EXP extends AST_EXP
{
  public AST_EXP e1;
public AST_BINOP b;
  public AST_EXP e2;
  
  public AST_EXP_EXP(AST_EXP e1, AST_BINOP b, AST_EXP e2, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (b == null) System.out.print("====================== exp --> exp\n");
    if (b != null) System.out.print("====================== exp --> exp binop exp\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.e1 = e1;
		this.b = b;
    		this.e2 = e2;
    		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP_EXP */
		/**************************************/
		System.out.print("AST NODE EXP_EXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (e1 != null) e1.PrintMe();
		if (b != null) b.PrintMe();
    		if (e2 != null) e2.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP_EXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (e1 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e1.SerialNumber);
		if (b != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,b.SerialNumber);
		if (e2 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e2.SerialNumber);
	}


	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		if (this.b != null){
			TYPE t1 = null;
			TYPE t2 = null;
			
			if (this.e1  != null) t1 = this.e1.visit(sym_table);
			if (this.e2 != null) t2 = this.e2.visit(sym_table);
			
			if (this.b.op == 1){
				if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
				{
					return TYPE_INT.getInstance();
				}

				if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()))
				{
					return TYPE_STRING.getInstance();
				}
			} else if ((this.b.op == 2) || (this.b.op == 3) || (this.b.op == 5) || (this.b.op == 6)){
				
				if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
				{
					return TYPE_INT.getInstance();
				}
			} else if (this.b.op == 4){

				if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()) && (e2.isZero == 0))
				{
					return TYPE_INT.getInstance();
				}
			} else { // this.b.op == 7
				if (sym_table.checkInheritance(t1, t2) || sym_table.checkInheritance(t2, t1)){
					return TYPE_INT.getInstance();
				}
					
			}

			throw new ArithmeticException(String.format("%d", this.line)); 
		}

		else {

			return this.e1.visit(sym_table);

		}		

	}
  
}
