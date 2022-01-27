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

}
