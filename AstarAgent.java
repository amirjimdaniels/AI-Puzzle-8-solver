package cs211.puz;

import java.util.*;

public class AstarAgent extends Agent {

	/*
		This is a helper function which is used to calculate the F value of TreeNodes after they have been removed from the fringe
		for processing.


	 */
	double CalculateFValue (TreeNode TreeNodeinput)
	{

		double calculateManhattan = Math.abs(TreeNodeinput.getState().blankCol() - goal.blankCol()) + Math.abs(TreeNodeinput.getState().blankRow()) - goal.blankRow(); //Gets manhattan distance of blank
		List<String> estimatedCostArray = pathFrom(TreeNodeinput); //Gets size of current NODE's actionList to estimate cost so far
		int estimateCostSoFar = estimatedCostArray.size();
		double Fvalue = calculateManhattan + estimateCostSoFar; //F value


		return Fvalue;

	}

	@Override
	public List<String> makePlan() throws InvalidActionException{

		PriorityQueue<TreeNode> theFringe = new PriorityQueue<>(); //Fringe priority queue
		Hashtable<TreeNode, PuzState> visited = new Hashtable();
		TreeNode currentNode = new TreeNode(start); // Current TreeNode in the loop. It starts with the goal puzstate and is added to the Fringe


		theFringe.add(currentNode);
		List<TreeNode> children = new ArrayList<>();


		while (!theFringe.isEmpty())
		{
			if (currentNode.getState().equals(goal))
			{
				return pathFrom(currentNode);
			}

			//Puts head of Fringe in visited and removes it from the fringe for comparison
			visited.put(currentNode, currentNode.getState());
			currentNode = theFringe.remove();

			for (int i =0; i < Action.ALL.length; i++)
			{
					//Make heuristic
					// |x1 - x2| + |y1 - y2|
					//Calculates distance of the blank

				//Tries all four actions from A* star and makes 4 children each with one action action
				TreeNode child = new TreeNode(new PuzState(currentNode.getState().toString()).nextStateFromAction(Action.ALL[i]));
				child.setCost(CalculateFValue(child)); //Cost is set to the f value to make it easier to access
				child.setParent(currentNode);
				child.setAction(Action.ALL[i]);
				children.add(child);  //ArrayList holds all new child values

			}	
				TreeNode[] AllAddedChildren = children.toArray(new TreeNode[0]); //ArrayList of all child values

				/*
					Uses comparator helper object to compare F values of nodes and adds them to the Fringe based on their priority.
					Was not able to affect priority queue comparator due to getting an error code when attempting to
				 */
			try {
					for (int i =0; i < AllAddedChildren.length; i++)
					{
						for (int j = i+1; j < AllAddedChildren.length; j++)
						{
							if (AllAddedChildren[i].CompareFValues(AllAddedChildren[i].getCost(), AllAddedChildren[j].getCost()) < 1)
							{	TreeNode temp1 = AllAddedChildren[i];
								TreeNode temp2 = AllAddedChildren[j];

								AllAddedChildren[i] = temp2;
								AllAddedChildren[i] = temp1;

							}

						}

					}
				}
				catch (IndexOutOfBoundsException e)
				{

				}

						//Only adds nodes to the Fringe if the current PuzState is not in the Fringe or the Visited to avoid revisiting the same state
					for (int i = 0; i < AllAddedChildren.length; i++ )
					{
						if (!theFringe.contains(AllAddedChildren[i].getState()) && !visited.contains(AllAddedChildren[i].getState()))
						{	System.out.println(AllAddedChildren[i].getState().toString());
							theFringe.add(AllAddedChildren[i]);

						}

					}
		}
		return null;
	}




}
