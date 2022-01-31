public class NodeList
{
  public Node head;
  public NodeList tail;
  
	public NodeList(Node head, NodeList tail) {
    this.head = head;
    this.tail = tail;
  }
	
	public Node add(int line, String name){
		if (this.head == null)
			Node n = New Node(line, name);{
			this.head = n;
			return n;
		}
		if (this.tail == null){
			Node n = New Node(line, name);
			this.tail = new NodeList(n, null);
			return n;
		}
		return this.tail.add(line, name);
	}
  

}
