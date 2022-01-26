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
	public String str1_len_loop; 
	public String str1_len_end; 
	public String str2_len_loop; 
	public String str2_len_end; 
	public String copy_str1_loop;
	public String copy_str1_end; 
	public String copy_str2_loop; 
	public String copy_str2_end;
	
	public IRcommand_Binop_Add_Strings(TEMP dst,TEMP t1,TEMP t2, String str1_len_loop, String str1_len_end, 
				String str2_len_loop, String str2_len_end, String copy_str1_loop,
			 	String copy_str1_end, String copy_str2_loop, String copy_str2_end)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
		this.str1_len_loop = str1_len_loop; 
		this.str1_len_end = str1_len_end; 
		this.str2_len_loop = str2_len_loop; 
		this.str2_len_end = str2_len_end; 
		this.copy_str1_loop = copy_str1_loop;
		this.copy_str1_end = copy_str1_end; 
		this.copy_str2_loop = copy_str2_loop; 
		this.copy_str2_end = copy_str2_end;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		
		MIPSGenerator.getInstance().addStrings(this.dst,this.t1,this.t2, this.str1_len_loop, this.str1_len_end, 
				this.str2_len_loop, this.str2_len_end, this.copy_str1_loop,
			 	this.copy_str1_end, this.copy_str2_loop, this.copy_str2_end);
	}
}
