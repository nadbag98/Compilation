package AST;
import TEMP.*;
import IR.*;

/***********/
/* PACKAGE */
/***********/

public class Class_Field_List
{
	public Class_Field head;
	public Class_Field_List tail;

	Class_Field_List(Class_Field head, Class_Field_List tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void add(int offset, String str){
		if (this.head == null) {
			this.head = new Class_Field(offset, 0, str);
		}
		else if (this.tail != null){
			this.tail.add(offset, str);
		} else {
			Class_Field c = new Class_Field(offset, 0, str);
			this.tail = new Class_Field_List(c, null);
		}
	}
	
	public void add(int offset, int i){
		if (this.head == null) {
			this.head = new Class_Field(offset, i, null);
		}
		else if (this.tail != null){
			this.tail.add(offset, i);
		} else {
			Class_Field c = new Class_Field(offset, i, null);
			this.tail = new Class_Field_List(c, null);
		}
		
	}
	
	public Class_Field_List copy(){
		Class_Field_List res = new Class_Field_List(this.head.copy(), null);
		if (this.tail != null){
			res.tail = this.tail.copy();
		}
		return res;
	}

}
