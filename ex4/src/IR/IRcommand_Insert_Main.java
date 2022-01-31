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

public class IRcommand_Insert_Main extends IRcommand
{
	
	public IRcommand_Insert_Main()
	{
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
    MIPSGenerator.getInstance().label_main();
		MIPSGenerator.getInstance().jal("user_main");
    MIPSGenerator.getInstance().li("$v0", 10);
    MIPSGenerator.getInstance().syscall();
	}
}
