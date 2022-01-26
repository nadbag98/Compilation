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

public class IRcommand_callMethodInClass extends IRcommand
{
	public int offset;
  public TEMP obj;

	public IRcommand_callMethodInClass(int offset, TEMP obj)
	{
		this.offset = offset;
		this.obj = obj;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{		
		MIPSGenerator.getInstance().subu(this.dst, 1);
	}
}
