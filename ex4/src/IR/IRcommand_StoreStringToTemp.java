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

public class IRcommand_StoreStringToTemp extends IRcommand
{
	String src;
	TEMP dst;
  int offset;
	
	public IRcommand_StoreStringToTemp(String src,TEMP dst, int offset)
	{
		this.src      = src;
		this.dst = dst;
    this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().store(this.dst, this.src, this.offset);
	}
}
