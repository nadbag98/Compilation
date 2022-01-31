/***********/
/* PACKAGE */
/***********/
package MIPS;

import java.io.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class GRAPH
{


 public NodeList nodes;
  
  public GRAPH() {
    this.nodes = new NodeList(null, null);
  }
  
  public void createBlocks(){
    Node prevNode = null;
    int counter = 1;
     BufferedReader reader;
   reader = new BufferedReader(new FileReader("../../output/MIPS.txt"));
    String line = reader.readLine();
    while(line != null){
      if (line.contains(":") && !line.contains(".word") && !line.contains(".asciiz")) {
        if (prevNode != null){
          prevNode.lastLine = counter-1;
        }

        prevNode = new Node(counter, line.substring(0, line.length()-1));
        this.nodes.add(prevNode);

      }
      else if(line.contains("\tj ") || line.contains("\tblt") || line.contains("\tbgt") || line.contains("\tbge") ||
        line.contains("\tble") || line.contains("\tbne") || line.contains("\tbeq")){
        line = reader.readLine();
          counter++;
          if (prevNode != null){
          prevNode.lastLine = counter-1;
          }
          if (line.contains(":")){
            prevNode = new Node(counter, line.substring(0, line.length()-1));
            this.nodes.add(prevNode);
        }
        else{
          prevNode = new Node(counter, null);
          this.nodes.add(prevNode); 

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
            
  public void createEdges(){
    int counter = 1;
    BufferedReader reader;
    reader = new BufferedReader(new FileReader("../../output/MIPS.txt"));
    String line = reader.readLine();
    while(line != null){

      if(line.contains("\tblt") || line.contains("\tbgt") || line.contains("\tbge") ||
        line.contains("\tble") || line.contains("\tbne") || line.contains("\tbeq")){
        Node src = this.nodes.findByLast(counter);
        int idx = line.lastIndexOf(",");
        String label = line.substring(idx+1, line.length());
        Node dst = this.nodes.findByLabel(label);
        src.after.add(dst);
        dst.before.add(src);

        dst = this.nodes.findByFirst(counter+1);
        src.after.add(dst);
        dst.before.add(src);
      }

      else if(line.contains("\tj ")){
        Node src = this.nodes.findByLast(counter);
        String label = line.substring(3, line.length());
        Node dst = this.nodes.findByLabel(label);

        src.after.add(dst);
        dst.before.add(src);
      }
      
      line = reader.readLine();
      counter++;
    }
    reader.close();
  }
}
