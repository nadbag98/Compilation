/***********/
/* PACKAGE */
/***********/
package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_CI
{
	private CI_Class_List c_list = null;

	

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
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new AST_CI();
		}
		return instance;
	}
	
	public void add_field(int offset, String str){
		CI_Class curr = this.c_list.get_last();
		if (cur == null){
			System.out.println("ERROR come to add field in AST_CI\n");
		}
		curr.add_field(int offset, String str);
	}
	
	public void add_field(int offset, int i){
		CI_Class curr = this.c_list.get_last();
		if (cur == null){
			System.out.println("ERROR come to add field in AST_CI\n");
		}
		curr.add_field(int offset, String str);
	}
	
	public void add_class(String class_name){
		CI_Class new_c = new CI_Class(class_name);
		this.c_list.Add_CI_Class(new_c);
	}
}
