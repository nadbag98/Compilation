package MIPS;

public class Node
{
  public NodeList before;
  public NodeList after;
  public int line;
  public int lastLine;
  public String label;
  public int[] IN;
  
	public Node(int line, String label) {
    this.before = new NodeList(null, null);
    this.after = new NodeList(null, null);
    this.line = line;
	this.lastLine = line;
    this.label = label;
   this.IN = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
}
