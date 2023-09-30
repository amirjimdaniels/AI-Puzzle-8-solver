package cs211.puz;


/**
 * This interface helps us to establish a common call interface so we can easily
 * swap out the frontier implementation in our agent's seach/plan code without
 * having to make many manual edits.
 * 
 * @author mahiggs
 *
 */
public interface Frontier  {
	

	/**
	 * Returns true if the frontier is empty
	 * @return
	 */
	public boolean isEmpty();

	
	
	/**
	 * Appropriately adds the search treenode to the frontier collection;
	 * 
	 * @param n
	 */
	public void addNode(TreeNode n);
	
	/**
	 * Removes and returns the next appropriate node from the frontier.
	 * 
	 * @return
	 */
	public TreeNode removeNode();
	
	/**
	 * Returns true if the specified state is associated with a node in the frontier.
	 * 
	 * @param state
	 * @return
	 */
	public boolean containsState(PuzState state);
	
	
	/**
	 * Locates and returns the search tree node associated with the specified state.   If
	 * the state is not found, returns null.  Otherwise returns a handle on the node.
	 * 
	 * @param state
	 * @return a handle on the associated node
	 */
	public TreeNode findNode(PuzState state);
	

}
