/***********/
/* PACKAGE */
/***********/
package AST;

public class CI_Class_List
{
	public CI_Class head;
	public CI_Class_List tail;

	CI_Class_List(CI_Class head, CI_Class_List tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
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
	
	public CI_Class get_last(){
		if (this.tail != null){
			return this.tail.get_last();
		} else {
			return this.head;
		}
	}
	
	public CI_Class find_class(String class_name){
		if (class_name.equals(this.head.c_name)){
			return this.head;
		}
		return this.tail.find_class(class_name);
	}
}
