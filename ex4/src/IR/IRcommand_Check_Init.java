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

public class IRcommand_Check_Init extends IRcommand
{
	TEMP t;
	
	public IRcommand_Check_Init(TEMP t)
	{
		this.t          = t;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	    String label_end        = getFreshLabel("end");
	    MIPSGenerator.getInstance().bnez(this.t,label_end);
	    MIPSGenerator.getInstance().la(this.t,"_null_ref");
	    MIPSGenerator.getInstance().subu("$sp","$sp", 4);
	    MIPSGenerator.getInstance().store("0($sp)",this.t);
	    MIPSGenerator.getInstance().print_string();
	    MIPSGenerator.getInstance().li("$v0",10);
	    MIPSGenerator.getInstance().syscall();
	    MIPSGenerator.getInstance().label(label_end);
	}
}
