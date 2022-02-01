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
  public int tempCounter;

  public int[][] liveArr;
  public int[][] tempArr;

  public NodeList nodes;
  
  public GRAPH() {
    this.nodes = new NodeList(null, null);
    this.tempArr = null;
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
    this.tempArr = new int[this.lineCounter][3];
    this.liveArr = new int[this.lineCounter][10];
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
      this.liveArr[counter] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

      line = reader.readLine();
      counter++;
    }
    reader.close();
  }


  public void initLine(int counter, String line){
      if (!line.contains("Temp_") || line.contains(".") || line.contains(":") || line.contains("syscall")){
        this.tempArr[counter][0] = -1;
        this.tempArr[counter][1] = -1;
        this.tempArr[counter][2] = -1;
        return;
      }

      int op_idx = line.indexOf(" ");
      String op = line.substring(1, op_idx);
      int[] temps = this.getTemps(line);

      switch(op){
        case "bnez":
        case "beqz":
        case "beq":
        case "bne":
        case "ble":
        case "bge":
        case "bgt":
        case "bltz":
        case "blt":
        case "store":
          this.tempArr[counter] = {-1, temps[0], temps[1]};
          break;

        default:
          this.tempArr[counter] = temps;
      }
    
    return; 
  }


  public int[] getTemps(String line){
      int[] temps = {-1, -1, -1};

      int first_comma_idx = line.indexOf(",");
      int second_comma_idx = line.substring(first_comma_idx+1, line.length()).indexOf(",") + first_comma_idx+1;
      if (second_comma_idx <= first_comma_idx){
        second_comma_idx = line.length();
      }

      int temp_idx = line.indexOf("Temp_");
    
      if (temp_idx < first_comma_idx){    
        temps[0] = Integer.parseInt(line.substring(temp_idx+5, first_comma_idx));
      }

      temp_idx = line.substring(first_comma_idx+1, line.length()).indexOf("Temp_") + first_comma_idx+1;
      if ((temp_idx > first_comma_idx) && (temp_idx < second_comma_idx)){
        temps[1] = Integer.parseInt(line.substring(temp_idx+5, second_comma_idx));
      }

      temp_idx = line.substring(second_comma_idx, line.length()).indexOf("Temp_") + second_comma_idx ;
      if (temp_idx > second_comma_idx){
        temps[2] = Integer.parseInt(line.substring(temp_idx+5, line.length()));
      }
      return temps;
  }

  public void liveAnalysis(){
    boolean changed = true;
    while(changed){
      changed = false;
      NodeList curr = this.nodes;
      while (curr != null){
        changed = changed || this.liveNode(curr.head);
        curr = curr.tail;
      }
    }
  }


  public boolean liveNode(Node node){
    boolean changed = false;
    NodeList curr = node.after;

    while (curr != null){
      for (int i : this.liveArr[curr.head.line-1]){
        for (int j=0; j<10; j++){
          if (node.IN[j] == i){
            break;
          }
          if (node.IN[j] != -1){
            node.IN[j] = i;
            changed = true;
            break;
          }
        }
      }
      curr = curr.tail;      
    }

    int[] prev = node.IN;
    for (int i=node.lastLine; i>=node.line; i--){
      for (int k : prev){
        if (k == this.tempArr[i-1][0]){
          continue;
        }
        for (int j=0; j<10; j++){
          if (k == this.liveArr[i-1][j]){
            break;
          }
          if (this.liveArr[i-1][j] == -1){
            this.liveArr[i-1][j] = k;
            changed = true;
            break;
          }
        }
      }

      for (int r = 1; r<3; r++){
        int k = this.tempArr[i-1][r];
        for (int j=0; j<10; j++){
          if (k == this.liveArr[i-1][j]){
            break;
          }
          if (this.liveArr[i-1][j] == -1){
            this.liveArr[i-1][j] = k;
            changed = true;
            break;
          }
        }
      }
      prev = this.liveArr[i-1];
    }
    return changed;
  }

}
