package cs211.puz;

import java.util.Hashtable;
import java.util.List;

public class BfsAgent extends Agent {

	@Override
	public List<String> makePlan() throws InvalidActionException {


		TreeNode currentNode = new TreeNode(start);
		QueueFrontier theFringe = new QueueFrontier();
		Hashtable<TreeNode, PuzState> visited = new Hashtable();
	
		theFringe.addNode(currentNode);
		
		while (!theFringe.isEmpty())
		{ 	

			//If the currentNode is equal to the goal, get the path and end the function
			if (currentNode.getState().equals(goal))
			{
				return pathFrom(currentNode);
			}
			
			
			visited.put(currentNode, currentNode.getState());
			currentNode = theFringe.removeNode();
			
			for (int i =0; i< Action.ALL.length; i++)
			{

					//Current node state changes when it should not

				TreeNode child = new TreeNode(new PuzState(currentNode.getState().toString()).nextStateFromAction(Action.ALL[i]));



				child.setAction(Action.ALL[i]);
				//If the current child state is not in the visited OR the fringe, then add it to the fringe

			if (theFringe.containsState(child.getState()) && visited.contains(child.getState()))
			{
				System.out.println("Oops, already in there");

			}
			else {
				theFringe.addNode(child);
				child.setParent(currentNode);
			}

				
			}
		

			
			
		}
		
		
		return null;
		
		
	}

}
