package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public abstract class AST_EXP extends AST_Node
{
  int isZero = 0;
  boolean isValidForArray = true;
  
  public TEMP IRme(){
    return null;
  }
}
