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
	
	public DATA_MEMBER find(String name)
	{
		if (this.head == null)
		{
			return null;
		}
		if (this.head.name.equals(name))
		{
			return this.head;
		}
		if (this.tail == null)
		{
			return null;
		}
		return this.tail.find(name);
	}
}
