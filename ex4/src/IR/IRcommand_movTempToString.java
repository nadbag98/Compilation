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

public class IRcommand_movTempToString extends IRcommand
{
	String dst;
	TEMP src;
	
	public IRcommand_movTempToString(String dst,TEMP src)
	{
		this.src      = src;
		this.dst = dst;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().mov(this.dst, this.src);
	}
}
