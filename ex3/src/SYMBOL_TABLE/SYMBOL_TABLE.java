/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;

/****************/
/* SYMBOL TABLE */
/****************/
public class SYMBOL_TABLE
{
	private int hashArraySize = 26;
	
	/**********************************************/
	/* The actual symbol table data structure ... */
	/**********************************************/
	private SYMBOL_TABLE_ENTRY[] table = new SYMBOL_TABLE_ENTRY[hashArraySize];
	private SYMBOL_TABLE_ENTRY top;
	private int top_index = 0;
	
	/**************************************************************/
	/* A very primitive hash function for exposition purposes ... */
	/**************************************************************/
	private int hash(String s)
	{
		return Character.getNumericValue(s.charAt(0)) % 26;
	}

	/****************************************************************************/
	/* Enter a variable, function, class type or array type to the symbol table */
	/****************************************************************************/
	public void enter(String name,TYPE t, TYPE my_class)
	{
		/*************************************************/
		/* [1] Compute the hash value for this new entry */
		/*************************************************/
		int hashValue = hash(name);

		/******************************************************************************/
		/* [2] Extract what will eventually be the next entry in the hashed position  */
		/*     NOTE: this entry can very well be null, but the behaviour is identical */
		/******************************************************************************/
		SYMBOL_TABLE_ENTRY next = table[hashValue];
	
		/**************************************************************************/
		/* [3] Prepare a new symbol table entry with name, type, next and prevtop */
		/**************************************************************************/
		SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(name,t,hashValue,my_class,next,top,top_index++);

		/**********************************************/
		/* [4] Update the top of the symbol table ... */
		/**********************************************/
		top = e;
		
		/****************************************/
		/* [5] Enter the new entry to the table */
		/****************************************/
		table[hashValue] = e;
		
		/**************************/
		/* [6] Print Symbol Table */
		/**************************/
		PrintMe();
	}

	/***********************************************/
	/* Find the inner-most scope element with name */
	/***********************************************/
	public SYMBOL_TABLE_ENTRY find(String name)
	{
		SYMBOL_TABLE_ENTRY e;
				
		for (e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name))
			{
				return e;
			}
		}
		
		return null;
	}

	/***************************************************************************/
	/* begine scope = Enter the <SCOPE-BOUNDARY> element to the data structure */
	/***************************************************************************/
	public void beginScope()
	{
		/************************************************************************/
		/* Though <SCOPE-BOUNDARY> entries are present inside the symbol table, */
		/* they are not really types. In order to be ablt to debug print them,  */
		/* a special TYPE_FOR_SCOPE_BOUNDARIES was developed for them. This     */
		/* class only contain their type name which is the bottom sign: _|_     */
		/************************************************************************/
		enter(
			"SCOPE-BOUNDARY",
			new TYPE_FOR_SCOPE_BOUNDARIES("NONE"), null);

		/*********************************************/
		/* Print the symbol table after every change */
		/*********************************************/
		PrintMe();
	}
	
	public boolean searchCurrScope(String name){
		
		SYMBOL_TABLE_ENTRY entry = top;
		
		while (entry.name != "SCOPE-BOUNDARY")
		{
			if (entry.name.equals(name)){
				return true;
			}
			entry = entry.prevtop;
		}
		return false;
	}
	
	public TYPE searchFamily(String name, TYPE_CLASS my_class){
		TYPE_CLASS ancestor = my_class;
	   	DATA_MEMBER dup;
	   	while(ancestor != null)
	   	{
			dup = ancestor.data_members.find(name);
			if (dup != null)
			{
				return dup.t;
			}
			ancestor = ancestor.father;
	   	}
		return null;
	}
	
	public TYPE searchAll(String name){
		
		SYMBOL_TABLE_ENTRY e = this.top;
		
		while (e != null && !e.type.isClass())
		{
			if (name.equals(e.name)){
				if (e.type == TYPE_INSTANCE.getInstance()){
					return e.my_class;
				}
				return e.type;
			}
			e = e.prevtop;
		}
		
		if (e == null){
			return null;
		}
		
		TYPE_CLASS familyMember = (TYPE_CLASS) e.type;
		familyMember = familyMember.father;
		DATA_MEMBER dup;
		while (familyMember != null){
			dup = familyMember.data_members.find(name);
			if (dup != null){
				return dup.t;
			}
			familyMember = familyMember.father;
		}
		while (e != null)
		{
			if (name.equals(e.name)){
				if (e.type == TYPE_INSTANCE.getInstance()){
					return e.my_class;
				}
				return e.type;
			}
			e = e.prevtop;
		}
		return null;	
	}
	
	public TYPE searchAllNotClass(String name){
		
		SYMBOL_TABLE_ENTRY e = this.top;
		
		while (e != null && !e.type.isClass())
		{
			if (name.equals(e.name)){
				if (e.type == TYPE_INSTANCE.getInstance()){
					return e.my_class;
				}
				if (e.type == TYPE_STRING.getInstance() || e.type == TYPE_INT.getInstance()){
					return e.type;
				}
				return null;
			}
			e = e.prevtop;
		}
		
		if (e == null){
			return null;
		}
		
		TYPE_CLASS familyMember = (TYPE_CLASS) e.type;
		familyMember = familyMember.father;
		DATA_MEMBER dup;
		while (familyMember != null){
			dup = familyMember.data_members.find(name);
			if (dup != null){
				return dup.t;
			}
			familyMember = familyMember.father;
		}
		while (e != null)
		{
			if (name.equals(e.name)){
				if (e.type == TYPE_INSTANCE.getInstance()){
					return e.my_class;
				}
				if (e.type == TYPE_STRING.getInstance() || e.type == TYPE_INT.getInstance()){
					return e.type;
				}
				return null;
			}
			e = e.prevtop;
		}
		return null;	
	}
	
	
	public TYPE findType(String name){
		SYMBOL_TABLE_ENTRY e = find(name);
    if (e == null) {
      return null;
    }
    TYPE t1 = e.type;
		if (t1 == TYPE_INT.getInstance() && name.equals("int")){
			return t1;
		}
		if (t1 == TYPE_STRING.getInstance() && name.equals("string")){
			return t1;
		}
		if (t1 == TYPE_VOID.getInstance() && name.equals("void")){
			return t1;
		}
		if (t1.isClass()){
			return t1;
		}
		if (t1.isArray()){
			return t1;
		}
		return null;
	}
	
	public boolean checkInheritance(TYPE father, TYPE son){
		if (father == son){
			return true;
		}
		if (father == null || son == null) {
			return false;
		}
		if ((father.isClass() || father.isArray()) && son == TYPE_NIL.getInstance()){
			return true;
		}
		if (father.isClass() && son.isClass()){
			TYPE_CLASS son_class = (TYPE_CLASS) son;
			TYPE_CLASS father_class = (TYPE_CLASS) father;
			TYPE_CLASS familyMember = son_class.father;
			while (familyMember != null){
				if (father_class == familyMember){
					return true;
				}
				familyMember = familyMember.father;
			}
		}
		return false;
	}

	/********************************************************************************/
	/* end scope = Keep popping elements out of the data structure,                 */
	/* from most recent element entered, until a <NEW-SCOPE> element is encountered */
	/********************************************************************************/
	public void endScope()
	{
		/**************************************************************************/
		/* Pop elements from the symbol table stack until a SCOPE-BOUNDARY is hit */		
		/**************************************************************************/
		while (top.name != "SCOPE-BOUNDARY")
		{
			table[top.index] = top.next;
			top_index = top_index-1;
			top = top.prevtop;
		}
		/**************************************/
		/* Pop the SCOPE-BOUNDARY sign itself */		
		/**************************************/
		table[top.index] = top.next;
		top_index = top_index-1;
		top = top.prevtop;

		/*********************************************/
		/* Print the symbol table after every change */		
		/*********************************************/
		PrintMe();
	}
	
	public static int n=0;
	
	public void PrintMe()
	{
		int i=0;
		int j=0;
		String dirname="./output/";
		String filename=String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt",n++);

		try
		{
			/*******************************************/
			/* [1] Open Graphviz text file for writing */
			/*******************************************/
			PrintWriter fileWriter = new PrintWriter(dirname+filename);

			/*********************************/
			/* [2] Write Graphviz dot prolog */
			/*********************************/
			fileWriter.print("digraph structs {\n");
			fileWriter.print("rankdir = LR\n");
			fileWriter.print("node [shape=record];\n");

			/*******************************/
			/* [3] Write Hash Table Itself */
			/*******************************/
			fileWriter.print("hashTable [label=\"");
			for (i=0;i<hashArraySize-1;i++) { fileWriter.format("<f%d>\n%d\n|",i,i); }
			fileWriter.format("<f%d>\n%d\n\"];\n",hashArraySize-1,hashArraySize-1);
		
			/****************************************************************************/
			/* [4] Loop over hash table array and print all linked lists per array cell */
			/****************************************************************************/
			for (i=0;i<hashArraySize;i++)
			{
				if (table[i] != null)
				{
					/*****************************************************/
					/* [4a] Print hash table array[i] -> entry(i,0) edge */
					/*****************************************************/
					fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n",i,i);
				}
				j=0;
				for (SYMBOL_TABLE_ENTRY it=table[i];it!=null;it=it.next)
				{
					/*******************************/
					/* [4b] Print entry(i,it) node */
					/*******************************/
					fileWriter.format("node_%d_%d ",i,j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n",
						it.name,
						it.type.name,
						it.prevtop_index);

					if (it.next != null)
					{
						/***************************************************/
						/* [4c] Print entry(i,it) -> entry(i,it.next) edge */
						/***************************************************/
						fileWriter.format(
							"node_%d_%d -> node_%d_%d [style=invis,weight=10];\n",
							i,j,i,j+1);
						fileWriter.format(
							"node_%d_%d:f3 -> node_%d_%d:f0;\n",
							i,j,i,j+1);
					}
					j++;
				}
			}
			fileWriter.print("}\n");
			fileWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static SYMBOL_TABLE instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected SYMBOL_TABLE() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static SYMBOL_TABLE getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new SYMBOL_TABLE();

			/*****************************************/
			/* [1] Enter primitive types int, string */
			/*****************************************/
			instance.beginScope();
			instance.enter("int",   TYPE_INT.getInstance(), null);
			instance.enter("string",TYPE_STRING.getInstance(), null);
			instance.enter("void",TYPE_VOID.getInstance(), null);

			/*************************************/
			/* [2] How should we handle void ??? */
			/*************************************/

			/***************************************/
			/* [3] Enter library function PrintInt */
			/***************************************/
			instance.enter(
				"PrintInt",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintInt",
					new TYPE_LIST(
						TYPE_INT.getInstance(),
						null)),
				null);
		
			
			instance.enter(
				"PrintString",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintString",
					new TYPE_LIST(
						TYPE_STRING.getInstance(),
						null)),
				null);
			
			instance.enter(
			"PrintTrace",
			new TYPE_FUNCTION(
				TYPE_VOID.getInstance(),
				"PrintTrace",
				new TYPE_LIST(
					null,
					null)),
				null);
			
		}
		return instance;
	}
}
