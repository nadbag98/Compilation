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
 	    MIPSGenerator.getInstance().bltz(this.idx,label_error);
	    MIPSGenerator.getInstance().load("$s0",this.addr, 0);
	    MIPSGenerator.getInstance().bge(this.idx,"$s0",label_error);
	    MIPSGenerator.getInstance().li("$s5", 0);
	    MIPSGenerator.getInstance().jump(label_end);
	    MIPSGenerator.getInstance().label(label_error);
	    MIPSGenerator.getInstance().la(this.idx,"_acc_violation");
	    MIPSGenerator.getInstance().subu("$sp","$sp", 4);
	    MIPSGenerator.getInstance().store("0($sp)",this.idx);
	    MIPSGenerator.getInstance().print_string();
	    MIPSGenerator.getInstance().li("$v0",10);
	    MIPSGenerator.getInstance().syscall(); 
	    MIPSGenerator.getInstance().label(label_end);
    
    
	}
}
