package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_VARDEC extends AST_Node
{
  	public AST_TYPE t;
	public AST_Node exp;
  	public String s;
  	public int is_new;
	public boolean is_global;
  
  
  public AST_VARDEC(AST_TYPE t, String s, AST_Node exp, int is_new, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (exp != null){
			if (is_new == 0) System.out.print("====================== varDec --> type ID := exp;");
			if (is_new == 1) System.out.print("====================== varDec --> type ID := newExp;");
		}
		if (exp == null) System.out.print("====================== varDec --> type ID;");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.s = s;
	    this.exp = exp;
	    this.is_new = is_new;
	    this.is_global = false;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VARDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (t != null) t.PrintMe();
		System.out.print(s);
		if (exp != null){
			System.out.print(" := ");
			exp.PrintMe();
		}
		System.out.print(";");
		

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("VARDEC\n%s\n", s));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		System.out.print("Visiting AST_VARDEC\n");
		
		this.is_global = sym_table.is_global();
		
		if (sym_table.searchCurrScope(this.s)){
			System.out.print("Exception in AST_FUNCDEC - Failed searchCurrScope\n");
			throw new ArithmeticException(String.format("%d", this.line)); 
		}
		
		TYPE t1 = sym_table.findType(this.t.s);
		
		if (null == t1){
			System.out.print("Exception in AST_VARDEC - null == t1\n");
			throw new ArithmeticException(String.format("%d", this.line));
		}
  		 if (t1 == TYPE_VOID.getInstance()) {
    			 System.out.print("Exception in AST_VARDEC - tried initializing void var\n");
    			 throw new ArithmeticException(String.format("%d", this.line));
  		 }
		
		if (null != this.exp){
			TYPE t2 = this.exp.visit(sym_table);
			if (this.is_new == 1){
				if (t1.isArray() && !t2.isArray()){
					throw new ArithmeticException(String.format("%d", this.line));
				}
				else if (!t1.isArray() && t2.isArray()){
					throw new ArithmeticException(String.format("%d", this.line));
				}
				else if (t1.isArray() && t2.isArray()){
					TYPE_ARRAY arr1 = (TYPE_ARRAY) t1;
					TYPE_ARRAY arr2 = (TYPE_ARRAY) t2;
					if (!sym_table.checkInheritance(arr1.arrayType, arr2.arrayType)){
						throw new ArithmeticException(String.format("%d", this.line));
					}
				}
				else {
					if (!sym_table.checkInheritance(t1, t2)){
						System.out.print("Exception in AST_FUNCDEC - Failed checkInheritance\n");
						throw new ArithmeticException(String.format("%d", this.line));
					}
				}
				
			}
			else 
			{
				if (!sym_table.checkInheritance(t1, t2)){
					System.out.print("Exception in AST_FUNCDEC - Failed checkInheritance\n");
					throw new ArithmeticException(String.format("%d", this.line));
				}
			}
			
		}
		
		if (t1 == TYPE_INT.getInstance() || t1 == TYPE_STRING.getInstance())
		   {
			sym_table.enter(this.s, t1, null);
		   } 
		   else {
			sym_table.enter(this.s, TYPE_INSTANCE.getInstance(), t1);
		   }
		return null;			
	}
	
	
	public TEMP IRme()
	{
	
		if (this.is_global){
			if (this.t.s == "int"){
				if (this.exp != null){
					AST_EXP_INT int_exp = (AST_EXP_INT)this.exp;
					int value = int_exp.i;
					if(int_exp.is_neg){
						value = -value;
					}
					IR.getInstance().Add_IRcommand(new IRcommand_Allocate_Word(this.s, value));
				}
				else {
					IR.getInstance().Add_IRcommand(new IRcommand_Allocate_Word(this.s, 0));
				}
			}
			else if (this.t.s == "string"){
				if (this.exp != null){
					AST_EXP_REST my_exp = (AST_EXP_REST)this.exp;
					IR.getInstance().Add_IRcommand(new IRcommand_Allocate_String(this.s, my_exp.s));
				}
				else {
					IR.getInstance().Add_IRcommand(new IRcommand_Allocate_String(this.s, "Default str"));
				}
			}
			else {
				IR.getInstance().Add_IRcommand(new IRcommand_Allocate_Word(this.s, 0));
			}
		}
		
		else {
			
		}
				
// 		if (initialValue != null)
// 		{
// 			IR.getInstance().Add_IRcommand(new IRcommand_Store(name,initialValue.IRme()));
// 		}
// 		return null;
	}
  
}
