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

public class IRcommand_LiToString extends IRcommand
{
	String dst;
	int imm;
	
	public IRcommand_LiToString(String dst,int imm)
	{
		this.dst      = dst;
		this.imm = imm;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().li(this.dst,this.imm);
	}
}
