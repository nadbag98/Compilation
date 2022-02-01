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

  public int lineCounter;
  public int[][] liveArr;
  public NodeList nodes;
  
  public GRAPH() {
    this.nodes = new NodeList(null, null);
    this.liveArr = null;
  }
  
  public void createBlocks() throws IOException, FileNotFoundException{
    Node prevNode = null;
    int counter = 1;
    BufferedReader reader;
   reader = new BufferedReader(new FileReader("output/MIPS.txt")); 
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

    this.lineCounter = counter-1;
    this.liveArr = new int[this.lineCounter][3];
    reader.close();
  }
            
  public void createEdges() throws IOException, FileNotFoundException{
    int counter = 1;
    BufferedReader reader;
    reader = new BufferedReader(new FileReader("output/MIPS.txt"));
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
      
      // Init liveness array
      this.initLine(counter-1, line);

      line = reader.readLine();
      counter++;
    }
    reader.close();
  }


  public void initLine(int counter, String line){
      if (!line.contains("Temp_") || line.contains(".") || line.contains(":") || line.contains("syscall")){
        this.liveArr[counter][0] = -1;
        this.liveArr[counter][1] = -1;
        this.liveArr[counter][2] = -1;
        return;
      }
      int[] temps = {-1, -1, -1};
      int opIdx = line.indexOf(" ");
      String op = line.substring(1, opIdx);
      int first_comma_idx = line.indexOf(",");
      int second_comma_idx = line.substring(first_comma_idx+1, line.length).indexOf(",");
      int first_temp_idx = line.indexOf("Temp_");
    
      if (first_temp_idx < first_comma_idx){
        temps[0] = Integer.parseInt(line.substring(first_temp_idx+5, first_comma_idx));
      }
      first_temp_idx = line.substring(first_comma_idx+1, line.length()).indexOf("Temp_");
      if (first_temp_idx < second_comma_idx){
        temps[1] = Integer.parseInt(line.substring(first_temp_idx+5, second_comma_idx));
      }
      if (second_comma_idx != -1){
        first_temp_idx = line.substring(second_comma_idx+1, line.length()).indexOf("Temp_");
        if (first_temp_idx != -1){
          temps[2] = Integer.parseInt(line.substring(first_temp_idx+5, second_comma_idx));
        }
      }
      
      switch(op){
          
          
      }
  }


}
