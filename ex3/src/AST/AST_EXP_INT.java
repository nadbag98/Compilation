package AST;

public class AST_EXP_INT extends AST_EXP
{
  public int i;
  public int is_neg;
  
  
  public AST_EXP_INT(int i, int is_neg, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (is_neg == 0) System.out.print("====================== exp --> INT\n");
    if (is_neg == 1) System.out.print("====================== exp --> MINUS INT \n");


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.i = i;
		this.is_neg = is_neg;
		this.line = line - 1;
		if (i == 0){
			this.isZero = 1;
		}

	}
  
  public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP_INT */
		/**************************************/
		System.out.print("AST NODE EXP_INT\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (is_neg == 0) System.out.print(String.format("%d\n", i));
    if (is_neg == 1) System.out.print(String.format("-%d\n", i));

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber, "EXP_INT\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
	}
  
}
