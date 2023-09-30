package cs211.puz;

import java.util.List;

/**
 * For grading,  we will use this class to launch our application.
 * 
 * @author mahiggs
 *
 */
public class MainDriver {

	public static void main(String[] args) throws InvalidActionException {

		/*
		 * Give our agent a chance to think...make a plan.
		 */
		PuzState start = new PuzState("23615*478");
		PuzState goal =  new PuzState("12345678*");
		
		Agent agent = new AstarAgent();
		// Agent agent = new BfsAgent();
	//	Agent agent = new GreedyBFS();
		
		agent.setStartState(start);
		agent.setGoalState(goal);
		
		System.err.println("Starting to plan....");
		List<String> actions = agent.makePlan();
		System.err.println("\n\n....ok.  I got it.\n\n");
		
		
		/*
		 * Now that our agent has a plan.  Let's see how
		 * it can be used.   We apply the plan, action by 
		 * action, hoping the results lead us to our intended
		 * goal state (a solved puzzle).
		 */
		PuzState current = start;
		System.err.println(current.displayString());	
		
		/*
		 * Iterate over each action in the plan....
		 */
		for (String a : actions ) {
				System.err.println(a);	// dislay the action.
				
				// now use the action to generate the state in our
				// environment and display so we can see the puzzle
				// changing.
				try {
					current = current.nextStateFromAction(a);
					System.err.println(current.displayString());
					
				} catch (Exception e) {
					System.err.println("\n !! OUCH !! The plan failed.");
					e.printStackTrace();
					System.exit(-1);
				}			

		}
		
		System.err.println("\n\nThat's the dream!");
		
	}

}

