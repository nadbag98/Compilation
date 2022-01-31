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

public class IRcommand_Check_Div_By_Zero extends IRcommand
{
	TEMP t;
	
	public IRcommand_Check_Div_By_Zero(TEMP t)
	{
		this.t          = t;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
    String label_end        = getFreshLabel("end");
		MIPSGenerator.getInstance().bnez(t,label_end);
    MIPSGenerator.getInstance().la(t,"_div_by_zero");
    MIPSGenerator.getInstance().print_string(t);
    MIPSGenerator.getInstance().li("$v0",10);
    MIPSGenerator.getInstance().syscall();
    MIPSGenerator.getInstance().label(label_end);
	}
}
