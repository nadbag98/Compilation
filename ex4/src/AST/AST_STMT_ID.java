package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_ID extends AST_STMT
{
  public String s;
  public AST_EXP_LIST l;
  public boolean is_global;
  public int offset;
  
  public AST_STMT_ID(String s, AST_EXP_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (s == null && l == null) System.out.print("====================== stmt --> RETURN SEMICOLON\n");
    if (s != null && l == null) System.out.print("====================== stmt --> ID();\n");
    if (s != null && l != null) System.out.print("====================== stmt --> ID(expList);\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
    this.s = s;
    this.l = l;
    this.is_global = false;

	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST AST_STMT_ID */
		/**************************************/
		System.out.print("AST NODE AST_STMT_ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
    System.out.print(s);
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "AST_STMT_ID\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);

	}
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
		System.out.print("Visiting AST_STMT_ID\n");
		
		if (this.s == null && this.l == null){
			if (returnType != TYPE_VOID.getInstance()){
				System.out.print("Exception in AST_STMT_ID - Failed getInstance\n");
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}
		
		if (this.s != null) { 				
			TYPE t1 = sym_table.searchAll(this.s);
			if (t1 == null || !t1.isFunc()){
				if (t1 == null){System.out.print("Exception in AST_STMT_ID - t1 == null\n");}
				else{ System.out.print("Exception in AST_STMT_ID - Failed isFunc\n");}
				throw new ArithmeticException(String.format("%d", this.line));
			}
			TYPE_FUNCTION func = (TYPE_FUNCTION) t1;
			
			if (this.l == null){
				if (func.params.head != null){
					System.out.print("Exception in AST_STMT_ID - func.params.head != null\n");
					throw new ArithmeticException(String.format("%d", this.line));
				}			
			} else {
				TYPE_LIST lst = new TYPE_LIST(null, null);
				this.l.visit(sym_table, lst);
				if (!func.params.equalsOrFamily(lst)){
					System.out.print("Exception in AST_STMT_ID - lst != func.params\n");
					throw new ArithmeticException(String.format("%d", this.line));
				}
			}
			
			this.offset = sym_table.offsetFuncToObject(this.s);
			if (this.offset == -1){
				this.is_global = true;
			}
			
			return func.returnType;
		}
		
		return null;
	}
	
	public TEMP IRme(){
		IR inst = IR.getInstance();
		if (this.s == null && this.l == null){
			inst.Add_IRcommand(new IRcommand_funcEpilogue());
		}
		
		if (this.s != null && this.l == null){
			if (this.is_global){
				inst.Add_IRcommand(new IRcommand_Jal(this.s));
			} else {
				TEMP obj = TEMP_FACTORY.getInstance().getFreshTEMP();
				inst.Add_IRcommand(new IRcommand_load(obj, "8($fp)"));
				inst.Add_IRcommand(new IRcommand_callMethodInClass(this.offset, obj));
			}
		}
		
		if (this.s != null && this.l != null){
			this.l.IRme();
			if (this.is_global){
				if (this.s.equals("PrintString")){ 
					inst.Add_IRcommand(new IRcommand_PrintString());
				}
				else if (this.s.equals("PrintInt")){ 
					inst.Add_IRcommand(new IRcommand_PrintInt());
				}
				else {
					inst.Add_IRcommand(new IRcommand_Jal(this.s));
				}
			} else {
				TEMP obj = TEMP_FACTORY.getInstance().getFreshTEMP();
				inst.Add_IRcommand(new IRcommand_load(obj, "8($fp)"));
				inst.Add_IRcommand(new IRcommand_callMethodInClass(this.offset, obj));
			}
			
			AST_EXP_LIST curr = this.l;
			while (curr != null){
				inst.Add_IRcommand(new IRcommand_addu("$sp", "$sp", 4));
				curr = curr.tail;
			}
		}
		return null;
	}
  
}
