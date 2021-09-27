package gamepackage;

public class Node {
	
	// each Node (which will be in a Stack) will point to a value and the next Node
	public Node next;
	public byte value;
	
	// constructor which assigns the Nodes value to the explicit parameter
	public Node(byte val)
	{
		value = val;
	}
	
	// set the value to a new byte
	public void set_value(byte set) {
		value = set;
	}
	
	// set next to a new Node
	public void set_next(Node set) {
		next = set;
	}
}
