package TYPES;

public class TYPE_LIST
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void insert(TYPE t)
	{
		if (this.head == null)
		{
			this.head = t;
			return;
		}
		if (this.tail == null)
		{
			this.tail = new TYPE_LIST(t, null);
			return;
		}
		this.tail.insert(t);
	}
	
	public TYPE find(String name)
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
	
	public boolean equals(TYPE_LIST other){
		
		TYPE_LIST L1 = this;
		TYPE_LIST L2 = other;
		
		if (L1.head != L2.head){
			return false;
		}
		
		while (L1.head != null && L2.head != null){
			if (L1.head != L2.head){
				return false;
			}
			L1 = L1.tail;
			L2 = L2.tail;
		}
		
		if (L1.head == L2.head){
			return true;
		}
		
		return false;
		
	}
}
