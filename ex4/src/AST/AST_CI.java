/***********/
/* PACKAGE */
/***********/
package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_CI
{
	public CI_Class_List c_list = null;

	

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
			instance.c_list = new CI_Class_List(null, null);
		}
		return instance;
	}
	
	public void add_field(int offset, String str){
		CI_Class curr = this.c_list.get_last();
		if (curr == null){
			System.out.println("ERROR come to add field in AST_CI\n");
		}
		curr.add_field(offset, str);
	}
	
	public void add_field(int offset, int i){
		CI_Class curr = this.c_list.get_last();
		if (curr == null){
			System.out.println("ERROR come to add field in AST_CI\n");
		}
		curr.add_field(offset, i);
	}
	
	public void add_class(String class_name){
		CI_Class new_c = new CI_Class(class_name);
		this.c_list.Add_CI_Class(new_c);
	}
	
	public CI_Class find_class(String class_name){
		return this.c_list.find_class(class_name);
	}
}
