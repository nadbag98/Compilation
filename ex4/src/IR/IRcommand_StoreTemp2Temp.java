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

public class IRcommand_StoreTemp2Temp extends IRcommand
{
	TEMP dst;
	TEMP src;
	
	public IRcommand_StoreTemp2Temp(TEMP dst,TEMP src)
	{
		this.src      = src;
		this.dst = dst;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().store(this.dst, this.src);
	}
}
