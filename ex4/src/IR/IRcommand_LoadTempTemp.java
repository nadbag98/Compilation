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

public class IRcommand_LoadTempTemp extends IRcommand
{
	TEMP dst;
	TEMP src;
	
	public IRcommand_LoadTempTemp(TEMP dst, TEMP src)
	{
		this.dst      = dst;
		this.src = src;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().load(dst,src);
	}
}
