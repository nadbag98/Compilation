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

public class IRcommand_AssignToVar extends IRcommand
{
	String address;
	TEMP src;
	
	public IRcommand_AssignToVar(String address,TEMP src)
	{
		this.src      = src;
		this.address = address;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().store(this.address, this.src);
	}
}
