package AST;
import TEMP.*;
import IR.*;

/***********/
/* PACKAGE */
/***********/

public class Class_Field
{
  int offset;
  int int_val;
  String string_val;
  
  public Class_Field(int offset, int int_val, String string_val){
    this.offset = offset;
    this.int_val = int_val;
    this.string_val = string_val;
  }
  
  public Class_Field copy(){
		Class_Field d = new Class_Field(this.offset, this.int_val, this.string_val);
		return d;
	}
}
