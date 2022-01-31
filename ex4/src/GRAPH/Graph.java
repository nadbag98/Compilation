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
	  Node prevNode = null;
	  int counter = 1;
   	 BufferedReader reader;
	 reader = new BufferedReader(new FileReader("..\..\output\MIPS.txt"));
	  String line = reader.readLine();
	  while(line != null){
		  if (line.contains(":") && !line.contains(".word") && !line.contains(".asciiz")) {
			  if (prevNode != null){
				  prevNode.lastLine = counter-1;
			  }
			prevNode = this.nodes.add(counter, line.substring(0, line.length()-1));
		  }
		  else if(line.contains("\tj ") || line.contains("\tjal") || line.contains("\tblt") || line.contains("\tbgt") || line.containts("\tbge") ||
			  line.contains("\tble") || line.contains("\tbne") || line.contains("\tbeq")){
				line = reader.readLine();
			  	counter++;
			  	if (prevNode != null){
				  prevNode.lastLine = counter-1;
				  }
			  	if (line.contains(":"){
					prevNode = this.nodes.add(counter, line.substring(0, line.length()-1));
				}
				else{
					prevNode = this.nodes.add(counter, null);	
				}
		  }
		  else if(line.contains("\tjr")){
			  prevNode.lastLine = counter;
			prevNode = null;	  
		  }
		  
		  line = reader.readLine();
		  counter++;
	  }
	  reader.close();
  }
}
