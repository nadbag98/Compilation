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

public class IRcommand_Check_Range extends IRcommand
{
	public TEMP dst;

	public IRcommand_Check_Range(TEMP dst)
	{
		this.dst = dst;
	}
	
	/***************/
	/* MIPS me !!!!!!!!!!!!!!!!!! */
	/***************/
	public void MIPSme()
	{
		/*******************************/
		/* [1] Allocate 2 fresh labels */
		/*******************************/
		String label_end        = getFreshLabel("end");
		String label_gt_max  = getFreshLabel("gt_max");
		String label_lt_min = getFreshLabel("lt_min");
		
		MIPSGenerator.getInstance().bge(dst,32768,label_gt_max);
		MIPSGenerator.getInstance().ble(dst,-32769,label_lt_min);
    MIPSGenerator.getInstance().jump(label_end);

		MIPSGenerator.getInstance().label(label_gt_max);
		MIPSGenerator.getInstance().li(dst,32767);
		MIPSGenerator.getInstance().jump(label_end);

		MIPSGenerator.getInstance().label(label_lt_min);
		MIPSGenerator.getInstance().li(dst,-32768);
		MIPSGenerator.getInstance().jump(label_end);

		/******************/
		/* [5] label_end: */
		/******************/
		MIPSGenerator.getInstance().label(label_end);
	}
}
