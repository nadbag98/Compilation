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

public class IRcommand_movStringToTemp extends IRcommand
{
	TEMP dst;
	String src;
	
	public IRcommand_movStringToTemp(TEMP dst, String src)
	{
		this.src = src;
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
