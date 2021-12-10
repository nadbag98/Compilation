package TYPES;

public class TYPE_FUNCTION extends TYPE
{
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST params;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String name,TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
	}
	
	public boolean isFunc(){ return true;}
	
	public boolean equals(TYPE other){
		if (!other.isFunc()){
			return false;
		}
		
		TYPE_FUNCTION otherFunc = (TYPE_FUNCTION)other;
		
		if (this.returnType != otherFunc.returnType){
			return false;
		}
		
		TYPE_LIST L1 = this.params;
		TYPE_LIST L2 = otherFunc.params;
		
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
