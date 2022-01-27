/***********/
/* PACKAGE */
/***********/
package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_CI
{
	private CI_Class head=null;
	private CI_Class_List tail=null;

	/******************/
	/* Add IR command */
	/******************/
	public void Add_CI_Class(CI_Class c)
	{
		if ((this.head == null) && (this.tail == null))
		{
			this.head = c;
		}
		else if ((this.head != null) && (this.tail == null))
		{
			this.tail = new CI_Class_List(c,null);
		}
		else
		{
			CI_Class_List it = this.tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new CI_Class_List(c,null);
		}
	}
	

	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static AST_CI instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected AST_CI() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static AST_CI getInstance()
	{
		if (this.instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			this.instance = new AST_CI();
		}
		return this.instance;
	}
}
