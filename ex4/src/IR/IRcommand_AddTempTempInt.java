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

public class IRcommand_AddTempTempInt extends IRcommand
{
	public TEMP dst;
	public TEMP t1;
	public int i;
	
	public IRcommand_AddTempTempInt(TEMP dst,TEMP t1,int i)
	{
		this.dst = dst;
		this.t1 = t1;
		this.i = i;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().add(this.dst,this.t1,this.i);
	}
}
