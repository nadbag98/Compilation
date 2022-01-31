/***********/
/* PACKAGE */
/***********/
package GRAPH;

import java.io.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class Graph
{


 public NodeList nodes;
	
	public Graph() {
    this.nodes = new NodeList(null, null);
  }
  
  public void createBlocks(){
	  int counter = 1;
   	 BufferedReader reader;
	 reader = new BufferedReader(new FileReader("..\..\output\MIPS.txt"));
	  String line = reader.readLine();
	  while(line != null){
		  if (line.contains(":") && !line.contains(".word") && !line.contains(".asciiz")) {
			this.nodes.add(counter, line.substring(0, line.length()-1));
		  }
		  
		  line = reader.readLine();
		  counter++;
	  }
	  reader.close();
  }
}
