package AST;

public class AST_BINOP extends AST_Node
{
  public int op;
  
  public AST_BINOP(int op, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    switch(op){
        case 1:
          System.out.print("====================== binop --> PLUS\n"); 
          break;
        case 2:
          System.out.print("====================== binop --> MINUS\n"); 
          break;
        case 3:
          System.out.print("====================== binop --> TIMES\n"); 
          break;
        case 4:
          System.out.print("====================== binop --> DIVIDE\n"); 
          break;
        case 5:
          System.out.print("====================== binop --> LT\n"); 
          break;
        case 6:
          System.out.print("====================== binop --> GT\n"); 
          break;
        case 7:
          System.out.print("====================== binop --> EQ\n"); 
          break;
    }


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.op = op;
		this.line = line - 1;
	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST BINOP */
		/**************************************/
		System.out.print("AST NODE BINOP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
        switch(this.op){
        case 1:
          System.out.print("PLUS\n"); 
          break;
        case 2:
          System.out.print("MINUS\n"); 
          break;
        case 3:
          System.out.print("TIMES\n"); 
          break;
        case 4:
          System.out.print("DIVIDE\n"); 
          break;
        case 5:
          System.out.print("LT\n"); 
          break;
        case 6:
          System.out.print("GT\n"); 
          break;
        case 7:
          System.out.print("EQ\n"); 
          break;
    }

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,"BINOP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
	}
  
}
