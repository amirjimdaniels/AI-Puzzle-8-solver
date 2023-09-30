package cs211.puz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a node in the search tree that results from searching our state space.
 * For any given node, we know/track the prior node from which we came in the search,
 * as well as additional meta-data.
 * <p>
 * Because we are constructing a n-ary tree, will manage a list of children nodes that
 * are possible when we take a specified action. 
 *  
 * @author mahiggs
 *
 */
public class TreeNode implements Comparable {

	/**
	 * A handle on the parent from which we arrived in the search.
	 */
	private TreeNode parent = null;
	
	/**
	 * Handles on all next possible nodes we could search.
	 */
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	
	/**
	 * The current state associated with this node.
	 */
	private PuzState state;
	
	/**
	 * The action we used to arrive at the current state from the parent state.
	 * @see cs211.puz.Action
	 */
	private String action;
	
	/**
	 * A representation of the overall cost to get to this node from the 
	 * start of our search
	 */
	private double cost;
	
	
	
	/**
	 * Primary constructor.   A TreeNode is always built upon a state.
	 * 
	 * @param state
	 */
	public TreeNode(PuzState state) {
		super();
		this.state = state;
	}


	
	/*
	 * GETTERS and SETTERS
	 * 
	 */
	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public PuzState getState() {
		return state;
	}

	public void setState(PuzState state) {
		this.state = state;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}


	/**
	 * Allows our search tree node instance to work with hash table based sets and
	 * other collections that rely on a working hashCode function.   We are basing
	 * our hash key on the search tree state.  In other words, we lookup a matching
	 * node based on its puzzle 8 state. 
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}


	/**
	 * Allows our search tree node instance to be compared with other nodes to 
	 * determine if they are "equivalent" (ie, they hold the same state).  
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	Comparator<Double> CompareFValues = new Comparator<Double>() {
		@Override
		public int compare(Double o1, Double o2) {
			if (o1 < o2)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	};


	public int CompareFValues(double TreeNode1, double Treenode2) {
		return CompareFValues.compare(TreeNode1, Treenode2);
	}


	@Override
	public int compareTo(Object o) {
		return 0;
	}
}

