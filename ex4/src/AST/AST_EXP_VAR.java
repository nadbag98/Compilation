package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_EXP_VAR extends AST_EXP
{
  public AST_VAR v;
	public String s;
  public AST_EXP_LIST l;
  public int offset;
  
  public AST_EXP_VAR(AST_VAR v, String s, AST_EXP_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    		if (s == null) System.out.print("====================== exp --> var\n");
		if (l != null && s != null) System.out.print("====================== exp --> var id expList\n");
		if (l == null && s != null) System.out.print("====================== exp --> var id\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
		this.s = s;
    		this.l = l;
    		this.line = line;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE EXP_VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		
		TYPE t1 = this.v.visit(sym_table);
		
		if (this.s == null && this.l == null) {
			return t1;
		}
		
		if (t1 == null || !t1.isClass()){
			System.out.print("First error in visit AST_EXP_VAR");
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		TYPE_CLASS t1_class = (TYPE_CLASS) t1;
		TYPE t2 = sym_table.searchFamily(this.s, t1_class);
		
		if (t2 == null || !t2.isFunc()){
			System.out.print("Second error in visit AST_EXP_VAR");
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		TYPE_FUNCTION func = (TYPE_FUNCTION) t2;
		
		this.offset = t1_class.data_members.find(this.s).offset;
		
		if (this.l == null){
			if (func.params.head != null){
				System.out.print("Third error in visit AST_EXP_VAR");
				throw new ArithmeticException(String.format("%d", this.line));
			}			
		} else {
			TYPE_LIST lst = new TYPE_LIST(null, null);
			this.l.visit(sym_table, lst);
			if (!func.params.equalsOrFamily(lst)){
				System.out.print("Forth error in visit AST_EXP_VAR");
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}

		return func.returnType;
	}
	
	public TEMP IRme(){
		IR inst = IR.getInstance();
		TEMP obj = this.v.IRme();
		if (this.s == null) {
			return obj;
		}
		TEMP res = TEMP_FACTORY.getInstance().getFreshTEMP();
		if (this.l == null){
			inst.Add_IRcommand(new IRcommand_callMethodInClass(this.offset, obj));
		} else {
			this.l.IRme();
			inst.Add_IRcommand(new IRcommand_callMethodInClass(this.offset, obj));
			AST_EXP_LIST curr = this.e;
			while (curr != null){
				inst.Add_IRcommand(new IRcommand_addu("$sp", "$sp", 4));
				curr = curr.tail;
			}
		}
		inst.Add_IRcommand(new IRcommand_movStringToTemp(res, "$v0"));
		return res;
	}
  
}
