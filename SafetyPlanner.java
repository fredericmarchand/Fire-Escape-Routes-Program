/**Assignment#4: SafetyPlanner
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.util.*;
import java.awt.Point;

public class SafetyPlanner {
	private static final int UNTRAVERSED = 99999999;

	private FloorPlan floorPlan;
	private int[][] distances;
	private int[][] exitIndexes;

	public SafetyPlanner(FloorPlan f) {
		floorPlan = f;
		exitIndexes = new int[f.size()][f.size()];
		distances = new int[f.size()][f.size()];
	}

	// Reset all distances
	public void resetDistances() {
		for (int r=0; r<floorPlan.size(); r++)
			for (int c=0; c<floorPlan.size(); c++)
				distances[r][c] = UNTRAVERSED;
	}

	// Return the floor plan on which this planner is working
	public FloorPlan getFloorPlan() { return floorPlan; }

	// Return the distance of the floor location (r,c) from the nearest exit
	public int getDistance(int r, int c) { return distances[r][c]; }

	// Set the distance of the floor location (r,c) from the nearest exit
	public void setDistance(int r, int c, int newCount) { distances[r][c] = newCount; }

	// Return whether or not this floor location was reached during propagation
	public boolean hasBeenTraversed(int r, int c) { return distances[r][c]!=UNTRAVERSED;}

	// Return the closest exit for the location (r,c)
	public int getExitIndex(int r, int c) { return exitIndexes[r][c]; }

	// Set the closest exit for location (r,c) to be exitIndex
	public void setExitIndex(int r, int c, int value) { exitIndexes[r][c] = value; }

	// Traverse the floor plan starting from the exit at (r,c) with the exit
	// being specified as exitIndex until all floor tiles have been reached.
	private void propagateFromHelp(int r, int c, int exitIndex, int value){
		if(!floorPlan.valid(r, c))
			return;
		if(distances[r][c] < value)
			return;
		if(floorPlan.wallAt(r, c))
			return;
		if(floorPlan.hasExitAt(r, c)){
			if((floorPlan.getExits().indexOf(floorPlan.exitAt(r, c)))!= exitIndex){
				return;
			}
		}
		distances[r][c] = value;
		exitIndexes[r][c] = exitIndex;

		propagateFromHelp(r+1, c, exitIndex, value+1);
		propagateFromHelp(r, c-1, exitIndex, value+1);
		propagateFromHelp(r, c+1, exitIndex, value+1);
		propagateFromHelp(r-1, c, exitIndex, value+1);
	}

	public void propagateFrom(int r, int c, int exitIndex) {
		propagateFromHelp(r, c, exitIndex, 0);
	}

	// Return a list of Point objects representing the shortest path
	// from the location (r,c) to its closest exit. This should only
	// work properly AFTER the floor plan has been propagated.
	public ArrayList<Point> pathFrom(int r, int c) {
		ArrayList<Point> list = new ArrayList<Point>();

		list.add(new Point(r, c));

		if(floorPlan.wallAt(r, c))
			return new ArrayList<Point>();
		if(getDistance(r, c) == 0)
			return list;
		if((getDistance(r, c)>(getDistance(r+1, c))) && ((getExitIndex(r, c)) == (getExitIndex(r+1, c))))
			list.addAll(pathFrom(r+1, c));
		else if((getDistance(r, c)>(getDistance(r-1, c))) && ((getExitIndex(r, c)) == (getExitIndex(r-1, c))))
			list.addAll(pathFrom(r-1, c));
		else if((getDistance(r, c)>(getDistance(r, c+1))) && ((getExitIndex(r, c)) == (getExitIndex(r, c+1))))
			list.addAll(pathFrom(r, c+1));
		else if((getDistance(r, c)>(getDistance(r, c-1))) && ((getExitIndex(r, c)) == (getExitIndex(r, c-1))))
			list.addAll(pathFrom(r, c-1));

		return list;
	}
}