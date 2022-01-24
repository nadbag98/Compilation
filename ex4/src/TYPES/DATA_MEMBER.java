package TYPES;

public class DATA_MEMBER
{
	public TYPE t;
	public String name;
	public int offset;
	public String defClass;
	
	public DATA_MEMBER(TYPE t, String name, int offset, String defClass)
	{
		this.t = t;
		this.name = name;
		this.offset = offset;
		this.defClass = defClass;
	}
	
	public DATA_MEMBER copy(){
		DATA_MEMBER d = new DATA_MEMBER(this.t, this.name, this.offset, this.defClass);
		return d;
	}
}
