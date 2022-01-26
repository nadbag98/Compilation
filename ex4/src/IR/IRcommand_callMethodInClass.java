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
		MIPSGenerator.getInstance().subu("$sp", "$sp", 4);
		MIPSGenerator.getInstance().store("0($sp)", this.obj);
		MIPSGenerator.getInstance().load("$s0", this.obj, 0);
		MIPSGenerator.getInstance().load("$s1", "$s0", this.offset*4);
		MIPSGenerator.getInstance().jalr("$s1");
		MIPSGenerator.getInstance().addu("$sp", "$sp", 4);
		
	}
}
