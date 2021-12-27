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



public class IRcommand_Allocate_Word extends IRcommand
{
	String var_name;
	int value;
	
	public IRcommand_Allocate_Word(String var_name, int value)
	{
		this.var_name = var_name;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocate_word(this.var_name, this.value);
	}
}
