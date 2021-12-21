package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_TYPE_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_TYPE head;
	public AST_TYPE_LIST tail;
  public String s;
  

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_TYPE_LIST(AST_TYPE head,AST_TYPE_LIST tail, String s, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== stmts -> stmt stmts\n");
		if (tail == null) System.out.print("====================== stmts -> stmt      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
    		this.s = s;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE TYPE LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"TYPE\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE_LIST params) throws ArithmeticException {
		
		if (sym_table.searchCurrScope(this.s)){
			throw new ArithmeticException(String.format("%d", this.line)); 
		}
		
		TYPE t1 = sym_table.findType(this.head.s);
		
		if (null == t1 || t1 == TYPE_VOID.getInstance()){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		if (t1 == TYPE_INT.getInstance() || t1 == TYPE_STRING.getInstance())
		{
			sym_table.enter(this.s, t1, null);
		} 
		else {
			sym_table.enter(this.s, TYPE_INSTANCE.getInstance(), t1);
		}
		
		params.insert(t1);
		if (this.tail != null){
			this.tail.visit(sym_table, params);
		}
		return null;				
		
	}
	
}
