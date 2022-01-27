/***********/
/* PACKAGE */
/***********/
package AST;

public class Class_Field_List
{
	public Class_Field head;
	public Class_Field_List tail;

	Class_Field_List(Class_Field head, Class_Field_List tail)
	{
		this.head = head;
		this.tail = tail;
	}

}
