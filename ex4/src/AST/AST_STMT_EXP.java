package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_STMT_EXP extends AST_STMT
{
  public AST_EXP e;
  public AST_STMT_LIST l;
  public int is_while;
  
  public AST_STMT_EXP(AST_EXP e, AST_STMT_LIST l, int is_while, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (l == null) System.out.print("====================== stmt --> RETURN exp SEMICOLON\n");
    if (l != null && is_while == 0) System.out.print("====================== stmt --> IF (exp) {stmtList}\n");
    if (l != null && is_while == 1) System.out.print("====================== stmt --> WHILE (exp) {stmtList}\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
	    this.e = e;
	    this.l = l;
	    this.is_while = is_while;
	}
  
  	public void PrintMe()
	{
			/**************************************/
			/* AST NODE TYPE = AST AST_STMT_EXP */
			/**************************************/
			System.out.print("AST NODE AST_STMT_EXP\n");

			/*************************************/
			/* RECURSIVELY PRINT HEAD + TAIL ... */
			/*************************************/
			if (l != null) l.PrintMe();
	    if (e != null) e.PrintMe();

			/**********************************/
			/* PRINT to AST GRAPHVIZ DOT file */
			/**********************************/
			AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber, "AST_STMT_EXP\n");

			/****************************************/
			/* PRINT Edges to AST GRAPHVIZ DOT file */
			/****************************************/
			if (l != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,l.SerialNumber);
	    if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);

	}
	
	public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
		TYPE t1 = this.e.visit(sym_table);
		if (this.l == null){
			if (!sym_table.checkInheritance(returnType, t1) || (t1 == TYPE_VOID.getInstance())){
				throw new ArithmeticException(String.format("%d", this.line)); 
			}			
		}
		
		else {
			if (!(t1==TYPE_INT.getInstance())){
				throw new ArithmeticException(String.format("%d", e.line));
			}
			sym_table.beginScope();
			sym_table.top.offset = sym_table.top.prevTop.offset;
			this.l.visit(sym_table, returnType);
			sym_table.endScope();
		}		
		
		return null;
	}
	
	public TEMP IRme()
	{
	
		if (this.l != null && this.is_while == 1){
			/*******************************/
			/* [1] Allocate 2 fresh labels */
			/*******************************/
			String label_end   = IRcommand.getFreshLabel("end");
			String label_start = IRcommand.getFreshLabel("start");

			/*********************************/
			/* [2] entry label for the while */
			/*********************************/
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Label(label_start));

			/********************/
			/* [3] cond.IRme(); */
			/********************/
			TEMP cond_temp = this.e.IRme();

			/******************************************/
			/* [4] Jump conditionally to the loop end */
			/******************************************/
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(cond_temp,label_end));		

			/*******************/
			/* [5] body.IRme() */
			/*******************/
			this.l.IRme();

			/******************************/
			/* [6] Jump to the loop entry */
			/******************************/
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Jump_Label(label_start));		

			/**********************/
			/* [7] Loop end label */
			/**********************/
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Label(label_end));

			/*******************/
			/* [8] return null */
			/*******************/
			return null;
		}
		
		if (this.l != null && this.is_while == 0){
			/*******************************/
			/* [1] Allocate 2 fresh labels */
			/*******************************/
			String label_end   = IRcommand.getFreshLabel("end");

			/********************/
			/* [3] cond.IRme(); */
			/********************/
			TEMP cond_temp = this.e.IRme();

			/******************************************/
			/* [4] Jump conditionally to the loop end */
			/******************************************/
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(cond_temp,label_end));		

			/*******************/
			/* [5] body.IRme() */
			/*******************/
			this.l.IRme();	

			/**********************/
			/* [7] Loop end label */
			/**********************/
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Label(label_end));

			/*******************/
			/* [8] return null */
			/*******************/
			return null;
		}
		
		if (this.l == null){
			TEMP res = this.e.IRme();
			
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_movTempToString("$v0", res));
			
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_FuncEpilogue());

		}
		return null;
	}
  
}
