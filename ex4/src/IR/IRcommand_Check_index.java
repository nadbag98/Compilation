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

public class IRcommand_Check_index extends IRcommand
{
	TEMP addr;
  TEMP idx;
	
	public IRcommand_Check_index(TEMP addr, TEMP idx)
	{
		this.addr = addr;
    this.idx = idx;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
    String label_end        = getFreshLabel("end");
    String label_error        = getFreshLabel("error");
 		MIPSGenerator.getInstance().bltz(idx,label_error);
    MIPSGenerator.getInstance().load("$s0",this.addr, 0);
    MIPSGenerator.getInstance().bge(idx,"$s0",label_error);
    MIPSGenerator.getInstance().jump(label_end);
    MIPSGenerator.getInstance().label(label_error);
    MIPSGenerator.getInstance().la(t,"_acc_violation");
    MIPSGenerator.getInstance().print_string(t);
    MIPSGenerator.getInstance().li("$v0",10);
    MIPSGenerator.getInstance().syscall(); 
    MIPSGenerator.getInstance().label(label_end);
    
    
	}
}
