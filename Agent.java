package cs211.puz;

import java.util.ArrayList;
import java.util.List;


/**
 * An instance of this class is smart.  Uses artificial intelligence (classic search) to make a plan in order
 * to achieve its purpose.  It's purpose is to solve the puzzle-8 by deriving a sequence of actions that 
 * transforms the puzzle-8 back to its goal state.
 * <p>
 * The agent could use the plan to act appropriately in his current environment.   We will just focus on 
 * making the plan.  
 * 
 * @author mahiggs
 *
 */
public abstract class Agent {
	

	/**
	 * A handle on the current worlds start state.
	 */
	protected PuzState start;
	
	/**
	 * A handle on the current worlds goal state...only one for this problem.
	 */
	protected PuzState goal;
	
	public Agent() {
		
	}
	
		
	/**
	 * Use this method to configure the start state.
	 * @param fromState handle
	 * @return the current agent so we can use the fluent style if desired
	 */
	public Agent setStartState(PuzState fromState) {
		this.start = fromState;
		return this;
	}
	
	
	
	/**
	 * Use this method to configure the goal state. 
	 * 
	 * @param anyState handle
	 * @return the current agent so we can use the fluent style if desired
	 */
	public Agent setGoalState(PuzState anyState) {
		this.goal = anyState;
		return this;
	}
	

	
	/**
	 * This method using an uniformed search to search for a list of actions
	 * from the start state to the goal state.   Once found, it returns that
	 * list in the proper order.
	 * 
	 * @return
	 * @throws InvalidActionException 
	 */
	public abstract List<String> makePlan() throws InvalidActionException;


	/**
	 * Given a configured and hooked-up search tree, our agent can walk
	 * back up the parent chain to recover the current plan.
	 * 
	 * @param current
	 * @return
	 */
	public List<String> pathFrom(TreeNode current) {
	List<String> actionList = new ArrayList<>();
	List<String> tempReverseList  = new ArrayList<>();
	
	while (current.getParent()!= null)
	{	actionList.add(current.getAction());
		current = current.getParent();
	
	}
	System.out.println(actionList.size());

	for (int i = actionList.size()-1; i >= 0; i-- )
	{
		tempReverseList.add(actionList.get(i));

	}
		
		return tempReverseList;
	}
	

	
}

