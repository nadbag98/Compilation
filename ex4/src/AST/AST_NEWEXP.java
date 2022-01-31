package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_NEWEXP extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_TYPE t;
  	public AST_EXP e;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_NEWEXP(AST_TYPE t, AST_EXP e, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (e == null) System.out.print("====================== newExp --> NEW TYPE\n");
    		if (e != null) System.out.print("====================== newExp --> NEW TYPE[exp]\n");
		
		
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
    		this.e = e;
		this.line = line;
    
    }

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST NEWEXP */
		/**************************************/
		System.out.print("AST NODE NEWEXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
    		if (t != null) t.PrintMe();
		if (e != null) e.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "NEWEXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
	public TYPE visit(SYMBOL_TABLE sym_table) throws ArithmeticException {
		TYPE t1 = sym_table.findType(this.t.s);
		
		if (null == t1){
			throw new ArithmeticException(String.format("%d", this.line));
		}
		
		if (this.e != null) {
			TYPE t2 = this.e.visit(sym_table);
			if (t2 != TYPE_INT.getInstance() || !this.e.isValidForArray) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
			
			
			TYPE_ARRAY array = new TYPE_ARRAY(t1, "dummy");
			return array;
			
		}
		else {
			if (!t1.isClass()) {
				throw new ArithmeticException(String.format("%d", this.line));
			}
		}
		
		return t1;			
	}
	
	public TEMP IRme(){
		IR inst = IR.getInstance();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		if (this.e == null){
			AST_CI ci = AST_CI.getInstance();
			CI_Class c_class = ci.find_class(this.t.s);
			inst.Add_IRcommand(new IRcommand_LiToString("$v0", 9));
			inst.Add_IRcommand(new IRcommand_LiToString("$a0", c_class.c_size));
			inst.Add_IRcommand(new IRcommand_Syscall());
			inst.Add_IRcommand(new IRcommand_movStringToTemp(dst, "$v0"));
			inst.Add_IRcommand(new IRcommand_LaToString("$s0", String.format("vt_%s", this.t.s)));
			inst.Add_IRcommand(new IRcommand_StoreStringToTemp("$s0", dst, 0));
			
			Class_Field_List curr = c_class.c_list;
			while (curr != null){
				if (curr.head == null) break;
				if (curr.head.string_val != null){					
					String str_lab = IRcommand.getFreshLabel("str"); 
					inst.Add_IRcommand(new IRcommand_Allocate_String(str_lab, curr.head.string_val));
					inst.Add_IRcommand(new IRcommand_LaToString("$s0", str_lab));
					inst.Add_IRcommand(new IRcommand_StoreStringToTemp("$s0", dst, curr.head.offset * 4));

				}
				else {
					inst.Add_IRcommand(new IRcommand_LiToString("$s0", curr.head.int_val));
					inst.Add_IRcommand(new IRcommand_StoreStringToTemp("$s0", dst, curr.head.offset * 4));
				}
				curr = curr.tail;
			}
		}
		
		else {
			TEMP array_size = this.e.IRme();
			inst.Add_IRcommand(new IRcommand_LiToString("$v0", 9));
			inst.Add_IRcommand(new IRcommand_movTempToString("$a0", array_size));
			inst.Add_IRcommand(new IRcommand_AddStringStringInt("$a0", "$a0", 1));
			inst.Add_IRcommand(new IRcommand_MulStringStringInt("$a0", "$a0", 4));
			inst.Add_IRcommand(new IRcommand_Syscall());
			inst.Add_IRcommand(new IRcommand_movStringToTemp(dst, "$v0"));
			inst.Add_IRcommand(new IRcommand_StoreTemp2Temp(dst, array_size));
		}
		
		return dst;
	}
	
}
