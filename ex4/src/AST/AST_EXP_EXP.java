package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_EXP_EXP extends AST_EXP
{
  public AST_EXP e1;
public AST_BINOP b;
  public AST_EXP e2;
  public boolean is_int_op;
  public boolean is_string_op;
  
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
		this.is_string_op = false;
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
				if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()) ) {
					this.is_string_op = true;
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
		IR inst = IR.getInstance();
		if (b == null) {
			return this.e1.IRme();
		}
		TEMP t1 = null;
		TEMP t2 = null;
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
				
		if (this.e1  != null) t1 = this.e1.IRme();
		if (this.e2 != null) t2 = this.e2.IRme();
		String gt_max_int = IRcommand.getFreshLabel("gt_max_int"); 
		String lt_min_int = IRcommand.getFreshLabel("lt_min_int");
		String good_range = IRcommand.getFreshLabel("good_range");
		if (this.b.op == 1) {
			if (this.is_int_op) {
				inst.Add_IRcommand(new IRcommand_Binop_Add_Integers(dst,t1,t2));
				inst.Add_IRcommand(new IRcommand_Check_Range(dst, gt_max_int, lt_min_int, good_range));
			}
			else {
				String str1_len_loop= IRcommand.getFreshLabel("str1_len_loop"); 
				String str1_len_end= IRcommand.getFreshLabel("str1_len_end"); 
				String str2_len_loop= IRcommand.getFreshLabel("str2_len_loop"); 
				String str2_len_end= IRcommand.getFreshLabel("str2_len_end"); 
				String copy_str1_loop= IRcommand.getFreshLabel("copy_str1_loop");
				String copy_str1_end= IRcommand.getFreshLabel("copy_str1_end"); 
				String copy_str2_loop= IRcommand.getFreshLabel("copy_str2_loop"); 
				String copy_str2_end= IRcommand.getFreshLabel("copy_str2_end");
				inst.Add_IRcommand(new IRcommand_Binop_Add_Strings(dst,t1,t2,str1_len_loop, str1_len_end, str2_len_loop, str2_len_end, copy_str1_loop,
			 	copy_str1_end, copy_str2_loop, copy_str2_end));
			}
		}
		if (this.b.op == 2)
		{
			inst.Add_IRcommand(new IRcommand_Binop_Sub_Integers(dst,t1,t2));
			inst.Add_IRcommand(new IRcommand_Check_Range(dst, gt_max_int, lt_min_int, good_range));
		}
		if (this.b.op == 3)
		{
			inst.Add_IRcommand(new IRcommand_Binop_Mul_Integers(dst,t1,t2));
			inst.Add_IRcommand(new IRcommand_Check_Range(dst, gt_max_int, lt_min_int, good_range));
		}
		if (this.b.op == 4)
		{
			inst.Add_IRcommand(new IRcommand_Binop_Div_Integers(dst,t1,t2));
			inst.Add_IRcommand(new IRcommand_Check_Range(dst, gt_max_int, lt_min_int, good_range));
		}
		if (this.b.op == 5)
		{
			inst.Add_IRcommand(new IRcommand_Binop_LT_Integers(dst,t1,t2));
		}
		if (this.b.op == 6)
		{
			inst.Add_IRcommand(new IRcommand_Binop_GT_Integers(dst,t1,t2));
		}
		if (this.b.op == 7)
		{
			if (!this.is_string_op) {
				inst.Add_IRcommand(new IRcommand_Binop_EQ_NotStrings(dst,t1,t2));
			}
			else {
				inst.Add_IRcommand(new IRcommand_Binop_EQ_Strings(dst,t1,t2));
			}
		
		}
		return dst;
	}
  
}
