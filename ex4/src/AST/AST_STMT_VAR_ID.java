package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_STMT_VAR_ID extends AST_STMT
{
  public AST_VAR v;
  public String s;
  public AST_EXP_LIST e;
  public int offset;
  
  public AST_STMT_VAR_ID(AST_VAR v, String s, AST_EXP_LIST e, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (e == null) System.out.print("====================== stmt --> var id\n");
    if (e != null) System.out.print("====================== stmt --> var id expList\n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
	this.v = v;
    this.s = s;
    this.e = e;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST AST_STMT_VAR_ID */
		/**************************************/
		System.out.print("AST NODE AST_STMT_VAR_ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		    System.out.print(s);
		    if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "AST_STMT_VAR_ID\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
    		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
		System.out.print("Visiting AST_STMT_VAR_ID\n");
		
		TYPE t1 = this.v.visit(sym_table);
// 		if (t1 == null || !t1.isClass()){
// 			System.out.print("Exception in AST_STMT_VAR_ID - t1 == null || t1 is not class\n");
// 			throw new ArithmeticException(String.format("%d", this.line));
// 		}
		if (t1 == null){
			System.out.print("Exception in AST_STMT_VAR_ID - t1 == null\n");
			throw new ArithmeticException(String.format("%d", this.line));
		}
		if (!t1.isClass()){
			System.out.print("Exception in AST_STMT_VAR_ID - t1 isn't class\n");
			System.out.print(t1.name);
			throw new ArithmeticException(String.format("%d", this.line));
		}
		TYPE_CLASS t1_class = (TYPE_CLASS) t1;
		TYPE t2 = sym_table.searchFamily(this.s, t1_class);
		
		if (t2 == null || !t2.isFunc()){
			System.out.print("Exception in AST_STMT_VAR_ID - t2 == null || t2 is not func\n");
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		this.offset = t1_class.data_members.find(this.s).offset;
		
		TYPE_FUNCTION func = (TYPE_FUNCTION) t2;
		
		if (this.e == null){
			if (func.params.head != null){
				System.out.print("Exception in AST_STMT_VAR_ID - func.param.head != null\n");
				throw new ArithmeticException(String.format("%d", this.line));
			}			
		} else {
			TYPE_LIST lst = new TYPE_LIST(null, null);
			this.e.visit(sym_table, lst);
			if (!func.params.equalsOrFamily(lst)){
				System.out.print("Exception in AST_STMT_VAR_ID - param lists not equal\n");
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}

		return func.returnType;
	}
	
	public TEMP IRme(){
		TEMP obj = this.v.IRme();
		IR.
		getInstance().Add_IRcommand(new IRcommand_LoadTempTemp(obj, obj));
		IR.
		getInstance().Add_IRcommand(new IRcommand_Check_Init(obj));
		if (this.e == null){
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_callMethodInClass(this.offset, obj));
		} else {
			this.e.IRme();
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_callMethodInClass(this.offset, obj));

			AST_EXP_LIST curr = this.e;
			while (curr != null){
				IR.
				getInstance().
				Add_IRcommand(new IRcommand_addu("$sp", "$sp", 4));
				curr = curr.tail;
			}
		}
		return null;
	}
}
