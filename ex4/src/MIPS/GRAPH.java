/***********/
/* PACKAGE */
/***********/
package MIPS;

import java.io.*;
import java.util.Arrays;

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
  public int[][] interMat;
  public int[][] interMat2;
  public int[] colors;
  public NodeList nodes;
  
  public GRAPH() {
    this.nodes = new NodeList(null, null);
    this.tempCounter = 0;
  }
  
  public void createBlocks() throws IOException, FileNotFoundException{
    Node prevNode = null;
    Node newNode = null;
    int counter = 1;
    BufferedReader reader;
    reader = new BufferedReader(new FileReader("output/MIPS_PRE_OPT.txt")); 
    String line = reader.readLine();
    while(line != null){
      if (line.contains(":") && !line.contains(".word") && !line.contains(".asciiz")) {
        if (prevNode != null){
          prevNode.lastLine = counter-1;
          newNode = new Node(counter, line.substring(0, line.length()-1));
          this.nodes.add(newNode);
          prevNode.after.add(newNode);
          newNode.before.add(prevNode);
          prevNode = newNode;
        }
        else {
          prevNode = new Node(counter, line.substring(0, line.length()-1));
          this.nodes.add(prevNode);
        }
      }
      else if(line.contains("\tblt") || line.contains("\tbgt") || line.contains("\tbge") ||
        line.contains("\tble") || line.contains("\tbne") || line.contains("\tbeq")){
        line = reader.readLine();
          counter++;
          if (prevNode != null){
            prevNode.lastLine = counter-1;
            prevNode = null;
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
      else if(line.contains("\tj ") || line.contains("\tjr")){
        if (prevNode != null){
          prevNode.lastLine = counter;
          prevNode = null;    
        }
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
    reader = new BufferedReader(new FileReader("output/MIPS_PRE_OPT.txt"));
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
      this.liveArr[counter-1] = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

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
        case "sw":
          this.tempArr[counter] = new int[]{-1, temps[0], temps[1]};
          break;

        default:
          this.tempArr[counter] = temps;
      }
    
    return; 
  }


  public int[] getTemps(String line){
      int[] temps = new int[]{-1, -1, -1};

      int first_comma_idx = line.indexOf(",");
      int second_comma_idx = line.substring(first_comma_idx+1, line.length()).indexOf(",") + first_comma_idx+1;
      if (second_comma_idx <= first_comma_idx){
        second_comma_idx = line.length();
      }

      int temp_idx = line.indexOf("Temp_");
    
      if (temp_idx < first_comma_idx){    
        temps[0] = Integer.parseInt(line.substring(temp_idx+5, first_comma_idx));
        if (temps[0] > this.tempCounter){
          this.tempCounter = temps[0];
        }
      }

      temp_idx = line.substring(first_comma_idx+1, line.length()).indexOf("Temp_") + first_comma_idx+1;
      if ((temp_idx > first_comma_idx) && (temp_idx < second_comma_idx)){
        int finish_idx = line.indexOf(")");
        if (finish_idx == -1){
          finish_idx = second_comma_idx;
        }
        temps[1] = Integer.parseInt(line.substring(temp_idx+5, finish_idx));
        if (temps[1] > this.tempCounter){
          this.tempCounter = temps[1];
        }
      }

      temp_idx = line.substring(second_comma_idx, line.length()).indexOf("Temp_") + second_comma_idx ;
      if (temp_idx > second_comma_idx){
        temps[2] = Integer.parseInt(line.substring(temp_idx+5, line.length()));
        if (temps[2] > this.tempCounter){
          this.tempCounter = temps[2];
        }
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
//     System.out.println(Arrays.toString(liveArr[68]));
//     System.out.println(Arrays.toString(liveArr[69]));
//     System.out.println(Arrays.toString(liveArr[70]));
//     Node n = this.nodes.findByLabel("Label_7_copy_str2_loop");
//     System.out.println(Arrays.toString(n.IN));
//     System.out.println(n.line);
//     System.out.println(Arrays.toString(this.tempArr[90]));
//     System.out.println(Arrays.toString(this.liveArr[90]));
//     System.out.println(Arrays.toString(this.liveArr[89]));
//     System.out.println(Arrays.toString(this.liveArr[88]));
//     System.out.println(Arrays.toString(this.liveArr[87]));
//     System.out.println(Arrays.toString(this.liveArr[86]));
  }


  public boolean liveNode(Node node){
    boolean changed = false;
    NodeList curr = node.after;

    while (curr != null && curr.head != null){
      for (int i : this.liveArr[curr.head.line-1]){
        for (int j=0; j<10; j++){
          if (node.IN[j] == i){
            break;
          }
          if (node.IN[j] == -1){
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


  public void tempGraph(){
    this.tempCounter += 1;
    this.interMat = new int[this.tempCounter][this.tempCounter];
    this.interMat2 = new int[this.tempCounter][this.tempCounter];
    for (int[] line : this.liveArr){
      for (int u : line){
        for (int v : line){
          if (u != -1 && v != -1){
            this.interMat[u][v] = 1;
            this.interMat[v][u] = 1;
            this.interMat2[u][v] = 1;
            this.interMat2[v][u] = 1;
          }
        }
      }
    }

    for (int i=0; i<this.tempCounter; i++){
      this.interMat[i][i] = 1;
      this.interMat2[i][i] = 1;  
    }
  }


  public void colorGraph(){
    int[] stack = new int[this.tempCounter];
    int stack_idx = 0;
    int prev = 0;
    while (stack_idx < this.tempCounter){
      prev = findNode2Remove(prev);
      stack[stack_idx] = prev;
      stack_idx++;
      for (int i=0; i<this.tempCounter; i++){
        if (prev == -1){
          System.out.println(Arrays.deepToString(interMat));
          System.out.println(Arrays.deepToString(interMat2));
          System.out.println(String.format("%d", stack_idx));
        }
        this.interMat2[i][prev] = 0;
        this.interMat2[prev][i] = 0;
      }
    }
    System.out.println(Arrays.deepToString(interMat));
    System.out.println(Arrays.deepToString(interMat2));
    this.colors = new int[this.tempCounter];
    boolean[] takenColors;
    for (int i=stack_idx-1; i>=0; i--){
        takenColors = new boolean[10];

        //update interMat2
        this.interMat2[i][i] = 1;
        for (int j=0; j<this.tempCounter; j++){
          int is_neighbor = this.interMat2[j][j] * this.interMat[i][j];
          this.interMat2[i][j] = is_neighbor;
          this.interMat2[j][i] = is_neighbor;
          if (is_neighbor == 1 && (i != j)){
            takenColors[this.colors[j]] = true;
          }
        }
        for (int j=0; j<10; j++){
          if (!takenColors[j]){
            this.colors[i] = j;
            break;
          }
        }
    }
  }


  public int findNode2Remove(int idx){
    for (int i=0; i<this.tempCounter; i++){
      int degree = 0;
      int j = (i + idx) % this.tempCounter;
      for (int k=0; k<this.tempCounter; k++){
        degree += this.interMat2[j][k];
      }
      if (degree > 0 && degree <= 10){
        return j;
      }
    }
    return -1;
  }


  public void optimizeTemps(PrintWriter fileWriter) throws IOException, FileNotFoundException{
    int counter = 0;
    int[] temps;

    BufferedReader reader;
    reader = new BufferedReader(new FileReader("output/MIPS_PRE_OPT.txt")); 
    String line = reader.readLine();

    while(line != null){ 
      if (!line.contains("Temp_")){
        fileWriter.print(line + "\n");
        counter++;
        line = reader.readLine();
        continue;
      }
      temps = this.tempArr[counter];
      for (int i=0; i<3; i++){
        if (temps[i] != -1){
          line = line.replace(String.format("Temp_%d", temps[i]), String.format("$t%d", this.colors[temps[i]]));
        }
      }
      fileWriter.print(line + "\n");

      counter++;
      line = reader.readLine();
    }

    reader.close();
  }
  
  public void finalize(PrintWriter fileWriter)throws IOException, FileNotFoundException{
    
    this.createBlocks();
    System.out.println("finished createBlocks()");
    this.createEdges();
    System.out.println("finished createEdges()");
    this.liveAnalysis();
    System.out.println("finished liveAnalysis()");
    this.tempGraph();
    System.out.println("finished tempGraph()");
    this.colorGraph();
    System.out.println("finished colorGraph()");
    this.optimizeTemps(fileWriter);
    System.out.println("finished optimizeTemps()");
    return;
  }
}
