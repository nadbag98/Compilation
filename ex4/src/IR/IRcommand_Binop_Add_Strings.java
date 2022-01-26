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

public class IRcommand_Binop_Add_Strings extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Add_Strings(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		String str1_len_loop= IRcommand.getFreshLabel("str1_len_loop"); 
		String str1_len_end= IRcommand.getFreshLabel("str1_len_end"); 
		String str2_len_loop= IRcommand.getFreshLabel("str2_len_loop"); 
		String str2_len_end= IRcommand.getFreshLabel("str2_len_end"); 
		String copy_str1_loop= IRcommand.getFreshLabel("copy_str1_loop");
		String copy_str1_end= IRcommand.getFreshLabel("copy_str1_end"); 
		String copy_str2_loop= IRcommand.getFreshLabel("copy_str2_loop"); 
		String copy_str2_end= IRcommand.getFreshLabel("copy_str2_end");
		MIPSGenerator.getInstance().addStrings(dst,t1,t2, str1_len_loop, str1_len_end, str2_len_loop, str2_len_end, copy_str1_loop,
			 	copy_str1_end, copy_str2_loop, copy_str2_end);
	}
}
