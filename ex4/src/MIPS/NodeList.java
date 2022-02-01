package MIPS;

public class NodeList
{
  	public Node head;
  	public NodeList tail;
  
	public NodeList(Node head, NodeList tail) {
    this.head = head;
    this.tail = tail;
 	}
	
	public void add(Node n){
		if (this.head == null){
			this.head = n;
			return;
		}
		if (this.tail == null){
			this.tail = new NodeList(this.head, null);
			this.head = n;
			return;
		}
		this.tail.add(this.head);
		this.head = n;
		return;
	}

	public Node findByLast(int line){
		if (this.head == null){
			System.out.println("help me nodelis last\n");
			return null;
		}
		if (this.head.lastLine == line){
			return this.head;
		}
		else if(this.tail != null){
			return this.tail.findByLast(line);
		}
		else{
			System.out.println("help me nodelist no tail last :(\n");
			return null;
		}
	}

	public Node findByFirst(int line){
		if (this.head == null){
			System.out.println("help me nodelist first\n");
			return null;
		}
		if (this.head.line == line){
			return this.head;
		}
		else if(this.tail != null){
			return this.tail.findByFirst(line);
		}
		else{
			System.out.println("help me nodelist no tail first :(\n");
			return null;
		}
	}

	public Node findByLabel(String label){
		//System.out.println(label);
		if (this.head == null){
			System.out.println("help me nodelist label\n");
			return null;
		}
		if (this.head.label != null && this.head.label.equals(label)){
			return this.head;
		}
		else if (this.tail != null){
			return this.tail.findByLabel(label);
		}
		else{
			System.out.println("help me nodelist no tail label :(\n");
			return null;
		}
	}
}
