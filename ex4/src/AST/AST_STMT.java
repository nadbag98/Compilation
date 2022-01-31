package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public abstract class AST_STMT extends AST_Node
{
  public TYPE visit(SYMBOL_TABLE sym_table, TYPE returnType) throws ArithmeticException {
    System.out.print("YOU ARE IN AST_STMT!!! VARY BAD!!!\n");
    return null;
  }

}
