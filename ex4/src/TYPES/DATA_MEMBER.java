package TYPES;

public class DATA_MEMBER
{
	public TYPE t;
	public String name;
	public int offset;
	
	public DATA_MEMBER(TYPE t, String name, int offset)
	{
		this.t = t;
		this.name = name;
		this.offset = offset;
	}
}
