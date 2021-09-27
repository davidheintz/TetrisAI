package gamepackage;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Stack{
	
	// 
	public Byte[] basearray= {0,1,2,3,4,5,6};
	Node first = null;
	
	// get_node method 
	public Node get_node() {
		// remove first
		Node get = first;
		if(get != null) {
			first = first.next;
		}	
		return get;
	}
	
	public void add_node(Node newnode) {
		// add first
		if(first != null) {
			newnode.next = first;
		}
		first = newnode;
	}
	
	public void empty() {
		first = null;
	}
	
	public Stack make_order_shuffle() {
		Stack neworder = new Stack();
		List<Byte> baselist = Arrays.asList(neworder.basearray);
		Collections.shuffle(baselist);
		baselist.toArray(neworder.basearray);
		return neworder;
	}
		
	public Stack make_order_random() {
		
		Random rand = new Random();
		Stack neworder = new Stack();
		for(int i = 0; i < 7; i++) {
			int pos = rand.nextInt(7-i) + i;
			byte addition = basearray[pos];
			neworder.add_node(new Node(addition));
			basearray[pos] = basearray[i];
			basearray[i] = addition;
		}
		return neworder;
	}
}