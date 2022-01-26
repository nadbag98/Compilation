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

public class IRcommand_Li extends IRcommand
{
	TEMP dst;
	int imm;
	
	public IRcommand_Li(TEMP dst,int imm)
	{
		this.dst      = dst;
		this.imm = imm;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().li(dst,imm);
	}
}
