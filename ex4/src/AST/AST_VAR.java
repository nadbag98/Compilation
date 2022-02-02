package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_VAR extends AST_Node
{
  	public AST_VAR v;
  	public String s;
	public AST_EXP e;
	public int offsetTo; // 0: Function (FP) , 1: Object, 2: Global
	public int offset;
  
  public AST_VAR(AST_VAR v, String s, AST_EXP e, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (v == null && e == null && s != null) System.out.print("====================== var --> ID\n");
		if (v != null && e == null && s != null) System.out.print("====================== var --> var.ID\n");
		if (v != null && e != null && s == null) System.out.print("====================== var --> var exp\n");

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
		/* AST NODE TYPE = AST VAR */
		/**************************************/
		System.out.print("AST NODE VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,"VAR\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		System.out.print("Visiting AST_VAR\n");
	
		if (this.v == null && this.e == null) {
			TYPE t1 = sym_table.searchAllNotClass(this.s);
			if (t1 == null) {
				System.out.print("Exception in AST_VAR - t1 == null\n");
				throw new ArithmeticException(String.format("%d", this.line));
			}
			else{
				this.offset = sym_table.offsetToFunc(this.s);
				this.offsetTo = 0;
				if (this.offset == 0){
					this.offset = sym_table.offsetToObject(this.s);
					this.offsetTo = 1;
					if (this.offset == 0){
						this.offsetTo = 2;
					}
				}
			}
			return t1;
		}
		
		TYPE t1 = this.v.visit(sym_table);
		if (this.e == null) {
			if (!t1.isClass()) {
				System.out.print("Exception in AST_VAR: t1 is not class\n");
				throw new ArithmeticException(String.format("%d", this.line));
			}
			TYPE_CLASS t1_class = (TYPE_CLASS) t1;
			TYPE t2 = sym_table.searchFamily(this.s, t1_class);
			if (t2 == null) {
				System.out.print("Exception in AST_VAR: t2 == null\n");
				throw new ArithmeticException(String.format("%d", this.line));
			}
			this.offset = t1_class.data_members.find(this.s).offset;
			return t2;
		}
		
		if (!t1.isArray()) {
			System.out.print("Exception in AST_VAR: t1 is not array\n");
			throw new ArithmeticException(String.format("%d", this.line));
		}
		TYPE t2 = this.e.visit(sym_table);
		if (t2 != TYPE_INT.getInstance()) {
			System.out.print("Exception in AST_VAR: t2 is not instace\n");
			throw new ArithmeticException(String.format("%d", this.line));
		}
		TYPE_ARRAY t1_array = (TYPE_ARRAY) t1;
		return t1_array.arrayType;
		
	}
	
	public TEMP IRme(){
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR inst = IR.getInstance();
		
		if (this.v == null && this.e == null) {
			if (this.offsetTo == 0){
				inst.Add_IRcommand(new IRcommand_movStringToTemp(dst, "$fp"));
				inst.Add_IRcommand(new IRcommand_AddTempTempInt(dst, dst, this.offset * 4));
			}
			else if (this.offsetTo == 1){
				inst.Add_IRcommand(new IRcommand_Load(dst, "8($fp)"));
				inst.Add_IRcommand(new IRcommand_AddTempTempInt(dst, dst, this.offset * 4));
			}
			else {
				inst.Add_IRcommand(new IRcommand_La(dst, String.format("global_%s", this.s)));
			}
		}
		
		if (this.v != null && this.e == null && this.s != null){
			dst = this.v.IRme();
			inst.Add_IRcommand(new IRcommand_LoadTempTemp(dst, dst));
			inst.Add_IRcommand(new IRcommand_Check_Init(dst));
			inst.Add_IRcommand(new IRcommand_AddTempTempInt(dst, dst, this.offset * 4));
		}
		
		if (this.v != null && this.e != null && this.s == null){
			TEMP index = this.e.IRme();
			dst = this.v.IRme();
			inst.Add_IRcommand(new IRcommand_LoadTempTemp(dst, dst));
			inst.Add_IRcommand(new IRcommand_Check_index(dst, index));
			inst.Add_IRcommand(new IRcommand_AddTempTempInt(index, index, 1));
			inst.Add_IRcommand(new IRcommand_MulTempTempInt(index, index, 4));
			inst.Add_IRcommand(new IRcommand_AdduTempTempTemp(dst, index, dst));
			//inst.Add_IRcommand(new IRcommand_LoadTempTemp(dst, index));
		}
		
		return dst;
	}
  
}
