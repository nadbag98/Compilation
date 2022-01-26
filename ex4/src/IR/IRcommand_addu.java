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

public class IRcommand_addu extends IRcommand
{
	public int op2;
  	public String op1;
    public String dst;

	public IRcommand_addu(String dst, String op1, int op2)
	{
		this.op1 = op1;
    this.op2 = op2;
    this.dst = dst;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{		
		MIPSGenerator.getInstance().addu(this.dst, this.op1, this.op2);
		
	}
}
