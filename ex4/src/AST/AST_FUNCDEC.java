package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_FUNCDEC extends AST_Node
{

  public AST_TYPE t;
  public String s;
	public AST_TYPE_LIST l1;
  public AST_STMT_LIST l2;
  public boolean in_class;
  public String class_name;
  
  public AST_FUNCDEC(AST_TYPE t, String s, AST_TYPE_LIST l1, AST_STMT_LIST l2, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (l1 != null) System.out.print("====================== funcDec --> 1/2\n");
		if (l1 == null) System.out.print("====================== funcDec --> 2/2\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.s = s;
    this.l1 = l1;
    this.l2 = l2;
    this.line = line;
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
		if (l1 != null) l1.PrintMe();
    if (l2 != null) l2.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, String.format("funcDec\n%s\n", s));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
    if (l1 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l1.SerialNumber);
    if (l2 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l2.SerialNumber);
	}
	
    public TYPE visit(SYMBOL_TABLE sym_table, TYPE_CLASS my_class, boolean in_class) throws ArithmeticException {
		this.in_class = in_class;
		if (sym_table.searchCurrScope(this.s))
		{
			System.out.print("Exception in AST_FUNCDEC - Failed searchCurrScope\n");
			throw new ArithmeticException(String.format("%d", t.line)); 
		}
		
		TYPE ret_val = sym_table.findType(this.t.s);
		if (ret_val == null)
		{
			System.out.print("Exception in AST_FUNCDEC - Failed findType\n");
			throw new ArithmeticException(String.format("%d", t.line));
		}
		
		TYPE_LIST param_list = new TYPE_LIST(null, null);
		TYPE_FUNCTION func = new TYPE_FUNCTION(ret_val, this.s, param_list);
		sym_table.enter(this.s, func, null);
		sym_table.beginScope();
		
		if (in_class){
			sym_table.top.offset = 2;
			this.class_name = my_class.name;
		}
		else{
			sym_table.top.offset = 1;
		}
		
		if (this.l1 != null) {
			l1.visit(sym_table, func.params);
		}
		
		if (in_class) {
			boolean inherited = false;
			TYPE_CLASS ancestor = my_class.father;
			DATA_MEMBER dup;
			while(ancestor != null) {
				dup = ancestor.data_members.find(this.s);
				if (dup != null && !func.equals(dup.t)) {
					System.out.print("Exception in AST_FUNCDEC - Failed dup memebers.find()\n");
					throw new ArithmeticException(String.format("%d", t.line));
				}
				if (dup != null){
					inherited = true;
				}
				ancestor = ancestor.father;
			}
			if (!inherited){
				DATA_MEMBER d = new DATA_MEMBER(func, this.s, my_class.data_members.getFuncOffset(), this.class_name);
				my_class.data_members.insert(d);
			}
			else{
				dup = my_class.data_members.find(this.s);
				dup.defClass = my_class.name;
			}
		}
		l2.visit(sym_table, func.returnType);
		sym_table.endScope();
		return null;
	}
	
	public TEMP IRme()
	{
		IR ir = IR.getInstance();
		//ir.Add_IRcommand(new IRcommand_New_Line());
		if (this.in_class){
			ir.Add_IRcommand(new IRcommand_Label(String.format("%s_%s", this.class_name, this.s)));
		} else {
			ir.Add_IRcommand(new IRcommand_Label(this.s));
		}
		
		ir.Add_IRcommand(new IRcommand_FuncPrologue());
		if (this.l2 != null) this.l2.IRme();
		ir.Add_IRcommand(new IRcommand_FuncEpilogue());
		
		return null;
	}
  
}
