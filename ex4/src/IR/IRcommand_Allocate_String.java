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



public class IRcommand_Allocate_String extends IRcommand
{
	String var_name;
	String value;
	
	public IRcommand_Allocate_String(String var_name, String value)
	{
		this.var_name = var_name;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocate_string(this.var_name, this.value);
	}
}
