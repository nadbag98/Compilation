public class NodeList
{
  public Node head;
  public NodeList tail;
  
	public NodeList(Node head, NodeList tail) {
    this.head = head;
    this.tail = tail;
  }
	
	public void add(int line, String name){
		if (this.head == null)
			Node n = New Node(line, name);{
			this.head = n;
			return;
		}
		if (this.tail == null){
			Node n = New Node(line, name);
			this.tail = new NodeList(n, null);
			return;
		}
		this.tail.add(line, name);
	}
  

}
