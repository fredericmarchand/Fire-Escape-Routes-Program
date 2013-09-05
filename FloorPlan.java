/**Assignment#4: FloorPlan
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.io.*;
import java.util.*;
import java.awt.Color;

public class FloorPlan implements java.io.Serializable {
	// Colors to be used to represent the exits;
	public static final int MAXIMUM_EXITS = 8;

	private int 			size;	// # of rows and columns in table
	private boolean[][]		walls;	// Grid indicating whether a wall is there or not
	private ArrayList<Exit>	exits;	// List of available building exits

	// Yep, this is a constructor.  It assumes that floorplans are always square in shape
	public FloorPlan(int rw) {
		size = rw;
		exits = new ArrayList<Exit>();
		walls = new boolean[size][size];

		// set the grid to have empty space inside, but walls around border
		for (int r=0; r<size; r++)
			for (int c=0; c<size; c++)
				if ((r==0)||(r==size-1)||(c==0)||(c==size-1))
					walls[r][c] = true;
				else
					walls[r][c] = false;
	}

	// Returns the size of the floor plan (i.e., the number of rows or columns)
	public int size() { return size; }

	// Return the exits of the building (as an ArrayList of (r,c) Point objects)
	public ArrayList<Exit> getExits() { return exits; }

	// returns whether or not there is a wall at the given location in the floor plan
	public boolean wallAt(int r, int c) { return walls[r][c]; }

	// Add or removes a wall at the given location
	public void setWallAt(int r, int c, boolean wall) { walls[r][c] = wall; }

	// Return the exit ID (i.e., its index) that is at the given location
	public Exit exitAt(int r, int c) {
		for (Exit ex: exits)
			if (ex.isAt(r,c)) return ex;
		return null;
	}

	// Return whether or not the building has an exit at the given location
	public boolean hasExitAt(int r, int c) {
		for (Exit ex: exits)
			if (ex.isAt(r,c)) return true;
		return false;
	}

	// Return the exit at the given index
	public Exit getExit(int i) { return exits.get(i); }

	// Return whether or not the given location is a valid location
	public boolean valid(int r, int c) {
		return (r>=0)&&(r<size)&&(c>=0)&&(c<size);
	}

	// Add an exit to the building at the given location (maximum of 8 exits)
	public boolean addExit(int r, int c) {
		if (exits.size() < MAXIMUM_EXITS) {
			exits.add(new Exit(r,c));
			walls[r][c] = false; // exits may not be walls.
			return true;
		}
		return false;
	}

	// Remove an exit from the floor plan at the given location
	public void removeExit(int r, int c) {
		exits.remove(new Exit(r,c));
	}

	// Display the floor plan in the console window
	public void display() {
		for (int r=0; r<size; r++) {
			for (int c=0; c<size; c++) {
				if (exitAt(r,c) != null)
					System.out.print("X");
				else if (wallAt(r,c))
					System.out.print("*");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	public void saveTo(String aFileName) {
		try {
			ObjectOutputStream aFile = new ObjectOutputStream(new FileOutputStream(aFileName));
			aFile.writeObject(this);
			aFile.close();
		}
		catch (java.io.IOException e) {
			System.err.println("Error writing to file " + aFileName);
		}
	}

	public static FloorPlan loadFrom(String aFileName) {
		try {
			ObjectInputStream aFile = new ObjectInputStream(new FileInputStream(aFileName));
			FloorPlan fp = (FloorPlan)aFile.readObject();
			aFile.close();
			return fp;
		}
		catch (java.io.IOException e) {
			System.err.println("Error reading from file " + aFileName);
		}
		catch (ClassNotFoundException e) {
			System.err.println("Class Error reading from file " + aFileName);
		}
		return null;
	}

	// Create and return an example of a FloorPlan object
	public static FloorPlan example1() {
		FloorPlan fp = new FloorPlan(20);

		int[][] tiles = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,1,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,1},
			{1,0,1,0,1,0,1,1,1,0,1,1,0,0,0,1,0,0,0,1},
			{1,0,1,1,1,0,0,0,0,0,1,1,0,0,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,1,0,1,1},
			{1,0,0,0,1,1,1,0,0,0,1,1,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,0,1,1,1,1,1,0,0,1,1,0,1,1},
			{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,1,1,0,0,1,1,0,0,1,0,0,0,1},
			{1,1,1,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1},
			{1,0,1,0,0,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1},
			{1,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1},
			{1,0,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
		for (int r=0; r<20; r++)
			for (int c=0; c<20; c++)
				fp.setWallAt(r,c,tiles[r][c]==1);

		// Add some exits
		fp.addExit(0,13);
		fp.addExit(8,0);
		fp.addExit(13,19);
		fp.addExit(19,11);

		return fp;
	}
}
