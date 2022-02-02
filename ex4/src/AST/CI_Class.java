package AST;
import TEMP.*;
import IR.*;

/***********/
/* PACKAGE */
/***********/

public class CI_Class
{
  int c_size;
  String c_name;
  Class_Field_List c_list;
  
  public CI_Class(String c_name){
    this.c_size = 1;
    this.c_name = c_name;
    this.c_list = new Class_Field_List(null, null);
  }
  
  public void add_field(int offset, String str){
		this.c_list.add(offset, str);
	  	this.c_size = this.c_size + 1;
	}
	
	public void add_field(int offset, int i){
		this.c_list.add(offset, i);
		this.c_size = this.c_size + 1;
	}
	
	public Class_Field get_field(int offset){
		return c_list.get_field(offset);
	}
  
  
}
