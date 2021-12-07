package TYPES;

public class DATA_MEMBER_LIST
{
	public DATA_MEMBER head;
	public DATA_MEMBER_LIST tail;
	
	public DATA_MEMBER_LIST(DATA_MEMBER head,DATA_MEMBER_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}	
}
