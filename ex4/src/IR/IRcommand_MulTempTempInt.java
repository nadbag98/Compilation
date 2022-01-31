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

public class IRcommand_MulTempTempInt extends IRcommand
{
	public TEMP dst;
	public TEMP op1;
	public int op2;
	
	public IRcommand_MulTempTempInt(TEMP dst, TEMP op1, int op2)
	{
		this.dst = dst;
		this.op1 = op1;
		this.op2 = op2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().li("$s0", this.op2);
		MIPSGenerator.getInstance().mul(this.dst,this.op1,"$s0");
	}
}
