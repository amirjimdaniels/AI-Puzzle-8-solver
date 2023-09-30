package cs211.puz;

import java.util.LinkedList;

/**
 * An instance of this class offers the basic methods we need to add and remove
 * nodes (while searching).   It also adds a method to check if a state is 
 * currently residing in the queue.  
 * 
 * @author mahiggs
 *
 */
public class QueueFrontier implements Frontier {
	
	protected LinkedList<TreeNode> myList;

	public QueueFrontier() {
		super();
		myList = new LinkedList<TreeNode>();
	}
	
	/**
	 * For FIFO queue, we append to the end of the linked list. 
	 */
	@Override
	public void addNode(TreeNode n) {
		myList.add(n);
	}

	
	/**
	 * For FIFO queue, we remove from the beginning of the linked list.
	 */
	@Override
	public TreeNode removeNode() {
		return myList.remove();
	}

	
	/**
	 * For checking for membership based on the state INSIDE the tree
	 * nodes,  we will naively perform sequential search.  However, this
	 * could be improved with the help of a hash table using the state
	 * as the key and the node handle as the value. (key,value) pairs.
	 * <p>
	 * Using a side hash table will greatly increase our speed.
	 */
	@Override
	public boolean containsState(PuzState state) {
		return this.findNode(state) != null;
	}

	
	/**
	 * For checking for matching state based on the state INSIDE the tree
	 * nodes,  we will naively perform sequential search.  
	 * <p>
	 * Using a side hash table will greatly increase our speed.
	 */
	@Override
	public TreeNode findNode(PuzState state) {

		for (TreeNode tn : myList) {
			if (tn.getState().equals(state)) return tn;
		}

		return null;
	}

	/**
	 * Returns true if the collection we use to implement the
	 * frontier is empty. 
	 */
	@Override
	public boolean isEmpty() {
		return myList.isEmpty();
	}

	
	
	

}
