package GRAPH;

public class Node
{
  public NodeList before;
  public NodeList after;
  public int line;
  public int lastLine;
  public String label;
  
	public Node(int line, String label) {
    this.before = new NodeList(null, null);
    this.after = new NodeList(null, null);
    this.line = line;
	this.LastLine = line;
    this.label = label;
  }
}
