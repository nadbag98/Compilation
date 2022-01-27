package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_CLASSDEC extends AST_Node
{
  public String s1;
  public String s2;
  public AST_CFIELD_LIST l;
  public AST_CLASS_DUMMY cd;
  public TYPE_CLASS type;
  
  
  public AST_CLASSDEC(AST_CLASS_DUMMY cd, String s1, String s2, AST_CFIELD_LIST l, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (s2 != null) System.out.print("====================== varDec --> 1/2\n");
		if (s2 == null) System.out.print("====================== varDec --> 2/2\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s1 = s1;
		this.s2 = s2;
    		this.l = l;
		this.line = line;
		this.cd = cd;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST CLASSDEC */
		/**************************************/
		System.out.print("AST NODE CLASSDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (l != null) l.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "CLASSDEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		if (sym_table.searchCurrScope(this.s1)){
			throw new ArithmeticException(String.format("%d", this.cd.line)); 
		}
		TYPE_CLASS t1 = new TYPE_CLASS(null, this.s1, null);
		if (this.s2 != null){
			SYMBOL_TABLE_ENTRY father_entry = sym_table.find(this.s2);
			if (father_entry == null || !father_entry.type.isClass()) {
				throw new ArithmeticException(String.format("%d", this.cd.line));
			}
			t1.father = (TYPE_CLASS) father_entry.type;
			t1.data_members = t1.father.data_members.copy();
		}
		else{
			t1.data_members = new DATA_MEMBER_LIST(null, null);
		}
		sym_table.enter(this.s1, t1, null);	
		sym_table.beginScope();
		this.l.visit(sym_table, t1);
		sym_table.endScope();
		
		this.type = t1;
		
		return null;
	}
  	
	public TEMP IRme(){
	
		IR ir = IR.getInstance();
		
		ir.Add_IRcommand(new IRcommand_Label(".data"));
		ir.Add_IRcommand(new IRcommand_Label(String.format("vt_%s:", this.s1)));
		
		DATA_MEMBER_LIST data_members = this.type.data_members;
		
		while (data_members != null){
			if (data_members.head != null && data_members.head.t.isFunc()){
				ir.Add_IRcommand(new IRcommand_Label(String.format(".word %s_%s", data_members.head.defClass, data_members.head.name)));
			}
			
			data_members = data_members.tail;
		}
		
		ir.Add_IRcommand(new IRcommand_Label(".text"));
		if (this.l != null) {
			this.l.IRme();
		}
		return null;
	}
  
}
