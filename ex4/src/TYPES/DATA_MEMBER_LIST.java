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
	
	public void insert(DATA_MEMBER d)
	{
		if (this.head == null)
		{
			this.head = d;
			return;
		}
		if (this.tail == null)
		{
			this.tail = new DATA_MEMBER_LIST(d, null);
			return;
		}
		this.tail.insert(d);
	}
	
	public DATA_MEMBER_LIST copy(){
		DATA_MEMBER_LIST res = new DATA_MEMBER_LIST(this.head.copy(), null);
		if (this.tail != null){
			res.tail = this.tail.copy();
		}
		return res;
	}
	
	public int getVarOffset(){
		DATA_MEMBER_LIST tmp = this;
		int counter = 1;
		while(tmp != null){
			if (tmp.head != null && !tmp.head.t.isFunc()){
				counter++;
			}
			tmp = tmp.tail;
		}
		return counter;
	}
	
	public int getFuncOffset(){
		DATA_MEMBER_LIST tmp = this;
		int counter = 0;
		while(tmp != null){
			if (tmp.head != null && tmp.head.t.isFunc()){
				counter++;
			}
			tmp = tmp.tail;
		}
		return counter;
	}
}
