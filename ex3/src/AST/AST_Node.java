package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

import SYMBOL_TABLE.*;
import TYPES.*;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	public int line;
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		System.out.print("AST NODE ABSTRACT VISIT, ERROR ERROR ERROR ERROR !!!!!!!!!! ERROR ERROR ERROR ERRORRRRRR! !!! !!#$$%)#$(6");
		return null;
	}
}
