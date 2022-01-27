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

public class IRcommand_AdduTempTempTemp extends IRcommand
{
	public TEMP op2;
  	public TEMP  op1;
    public TEMP dst;

	public IRcommand_AdduTempTempTemp(TEMP dst, TEMP op1, TEMP op2)
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
