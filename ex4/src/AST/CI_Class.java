/***********/
/* PACKAGE */
/***********/
package AST;

public class CI_Class
{
  int c_size;
  String c_name;
  Class_Fields_List c_list;
  
  public CI_Class(String c_name){
    this.c_size = 1;
    this.c_name = c_name;
    this.c_list = new Class_Fields_List(null, null);
  }
  
  
}
