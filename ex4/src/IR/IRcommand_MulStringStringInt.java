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

public class IRcommand_MulStringStringInt extends IRcommand
{
	public String dst;
	public String op1;
	public int op2;
	
	public IRcommand_MulStringStringInt(String dst, String op1, int op2)
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
