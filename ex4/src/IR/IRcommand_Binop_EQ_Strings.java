/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;

public class IRcommand_Binop_EQ_Strings extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_EQ_Strings(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/*******************************/
		/* [1] Allocate 3 fresh labels */
		/*******************************/
		String label_end        = getFreshLabel("str_eq_end");
		String label_loop  = getFreshLabel("str_eq_loop");
		String label_AssignZero = getFreshLabel("neq_label");
		
		MIPSGenerator.getInstance().li(this.dst, 1);
		
		MIPSGenerator.getInstance().mov("$s0" ,this.t1);
		MIPSGenerator.getInstance().mov("$s1" ,this.t2);
		
		MIPSGenerator.getInstance().label(label_loop);
		MIPSGenerator.getInstance().lb("$s2", 0, "$s0");
		MIPSGenerator.getInstance().lb("$s3", 0, "$s1");
		
		MIPSGenerator.getInstance().bne("$s2", "$s3", label_AssignZero);
		
		MIPSGenerator.getInstance().addu("$s0", "$s0", 1);
		MIPSGenerator.getInstance().addu("$s1", "$s1", 1);
		MIPSGenerator.getInstance().beq("$s2", 0, label_end);
		MIPSGenerator.getInstance().move(this.dst, this.dst);
		
		MIPSGenerator.getInstance().jump(label_loop);
		
		MIPSGenerator.getInstance().label(label_AssignZero);
		MIPSGenerator.getInstance().li(this.dst, 0);
		
		MIPSGenerator.getInstance().label(label_end);
	}
}
