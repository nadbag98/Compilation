package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_EXP_EXP extends AST_EXP
{
  public AST_EXP e1;
public AST_BINOP b;
  public AST_EXP e2;
  public boolean is_int_op;
  
  public AST_EXP_EXP(AST_EXP e1, AST_BINOP b, AST_EXP e2, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (b == null) System.out.print("====================== exp --> (exp)\n");
    if (b != null) System.out.print("====================== exp --> exp binop exp\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.e1 = e1;
		this.b = b;
    		this.e2 = e2;
		this.is_int_op = true;
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
					this.is_int_op = false;
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
				if (!( (t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()) ) ) {
					this.is_int_op = false;
				}
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
	
	public TEMP IRme()
	{
		if (b == null) {
			return this.e1.IRme();
		}
		TEMP t1 = null;
		TEMP t2 = null;
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
				
		if (this.e1  != null) t1 = this.e1.IRme();
		if (this.e2 != null) t2 = this.e2.IRme();

		if (this.b.op == 1) {
			if (this.is_int_op) {
				IR.
				getInstance().
				Add_IRcommand(new IRcommand_Binop_Add_Integers(dst,t1,t2));	
			}
			else {
				IR.
				getInstance().
				Add_IRcommand(new IRcommand_Binop_Add_Strings(dst,t1,t2));
			}
		}
		if (this.b.op == 2)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Sub_Integers(dst,t1,t2));
		}
		if (this.b.op == 3)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Mul_Integers(dst,t1,t2));
		}
		if (this.b.op == 4)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Div_Integers(dst,t1,t2));
		}
		if (this.b.op == 5)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_LT_Integers(dst,t1,t2));
		}
		if (this.b.op == 6)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_GT_Integers(dst,t1,t2));
		}
		if (this.b.op == 7)
		{
			if (this.is_int_op) {
				IR.
				getInstance().
				Add_IRcommand(new IRcommand_Binop_EQ_Integers(dst,t1,t2));
			}
			else {
				//TODO
			}
		}
		return dst;
	}
  
}
