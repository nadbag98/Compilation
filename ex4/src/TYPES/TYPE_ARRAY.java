
package TYPES;

public class TYPE_ARRAY extends TYPE
{
	public TYPE arrayType;

	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_ARRAY(TYPE arrayType, String name)
	{
		this.name = name;
		this.arrayType = arrayType;
	}
	
	public boolean isArray(){ return true;}
}
