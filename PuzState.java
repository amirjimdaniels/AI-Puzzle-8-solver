package cs211.puz;

import java.util.Arrays;

/**
 * Encapsulates the current state of a puzzle representing the tiles as a 2D array
 * of characters.  Some methods are conveniently defined to accept Strings as a 
 * courtesy to the programmer.
 * <p>
 * <ul>
 * <li><b>displayString()</b> can be used for dumping the state to the console for nice visualization.</li>
 * <li><b>toString()</b> can use used to flatten back to string for testing or other reasons.</li>
 * <li><b>toString()</b> can use used to flatten back to string for testing or other reasons.</li>
 * </ul>
 */
public class PuzState {

	/**
	 * Our puzzle is assumed to be a square shaped grid.   Here we use the constant
	 * to control the dimension of our puzzle.
	 */
	private final static int DIMENSION = 3;
	private final static char BLANK = '*';
	
	private char[][] tiles;
	

	

	/**
	 * @return a string representation of the current state, suitable for printing to 
	 * the console.  Surrounds with sides and corners characters.
	 */
	public String displayString() {
		
		StringBuffer buf = new StringBuffer("+---+\n");
		
		for (int r=0; r<DIMENSION; r++) {
			buf.append("|");
			for (int c=0; c<DIMENSION; c++) {
				buf.append(tiles[r][c]);
			}
			buf.append("|");
			buf.append("\n");
		}
		
		buf.append("+---+\n");

		return buf.toString();
	}
	

	/**
	 * Flattens the 2D char array into a string with the same characters
	 * in row major order for convenient testing.
	 * 
	 */
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		for (int r=0; r<DIMENSION; r++) {
			for (int c=0; c<DIMENSION; c++) {
				buf.append(tiles[r][c]);
			}
		}
		return buf.toString();
	}
	

	/**
	 * Convenient constructor for the programmer.
	 */
	public PuzState(String tStr) {
		super();
		setTilesFromString(tStr);
	}

	
	public PuzState(char[][] pTiles ) {
		this.setTiles(pTiles);
	}
	
	
	
	public char[][] getTiles() {
		return tiles;
	}

	public void setTiles(char[][] tiles) {
		this.tiles = tiles;
	}
	
	/**
	 * Uses a string to conveniently initialize our 2D array of
	 * characters.  For programmers convenience.
	 *  
	 * @param tStr
	 */
	public void setTilesFromString(String tStr) {
		this.setTiles(fromString(tStr));
	}

	
	
	/**
	 * Returns a 2D array built from a specified string.
	 * 
	 * @param tStr must be size compliant.
	 * 
	 * @return
	 */
	public char[][] fromString(String tStr) {
		
		/*
		 * make sure we have a NxN size string 
		 */
		assert( tStr.length() == DIMENSION*DIMENSION );
		
		/*
		 * We will generate an NxN char array
		 */
		char[][] t = new char[DIMENSION][DIMENSION];
		
		/*
		 * Copy each character over
		 */
		for (int r =0; r<DIMENSION; r++) {
			for (int c=0; c<DIMENSION; c++) {
				t[r][c] = tStr.charAt(r*DIMENSION+c);
			}
		}
		
		return t;
	}
	
	
	/**
	 * Searches the array and returns the current row position 
	 * of the blank.
	 * 
	 * @return int row index of the BLANK
	 */
	public int blankRow() {
		for (int r=0; r<DIMENSION; r++) {
			for (int c=0; c<DIMENSION; c++) {
				if (this.tiles[r][c] == BLANK) {
					return r;
				}
			}
		}
		
		return -1;
	}

	
	/**
	 * Searches the array and returns the current col position 
	 * of the blank.
	 * 
	 * @return int col index of the BLANK
	 */
		public int blankCol() {
		for (int r=0; r<DIMENSION; r++) {
			for (int c=0; c<DIMENSION; c++) {
				if (this.tiles[r][c] == BLANK) {
					return c;
				}
			}
		}
		
		return -1;
	}

	
	/**
	 * Does not modify the current state.  Instead we provide a completely new and separate
	 * state that results from the action specified.  If the specified action leads to an
	 * illegal state (like moving up when the blank is on top row), then throw an exeption.
	 * 
	 * @throws InvalidActionException 
	 * @see cs211.puz.Action
	 */
	public PuzState nextStateFromAction(String action) {
		
		PuzState thePuzState = new PuzState(getTiles());

		char [][] tempState = getTiles();

		try {

			if (action == Action.UP)
			{
				swap(tempState, blankRow(), blankCol(), blankRow()-1, blankCol());

			}

			else if (action == Action.DOWN)
			{
				swap(tempState, blankRow(), blankCol(), blankRow()+1, blankCol());

			}

			else if (action == Action.LEFT)
			{
				swap(tempState, blankRow(), blankCol(), blankRow(), blankCol()-1);

			}


			else if (action == Action.RIGHT)
			{
				swap(tempState, blankRow(), blankCol(), blankRow(), blankCol()+1);

			}
		}


		catch (Exception e) {

		}


		return new PuzState(tempState);    // stub ... replace this with a new appropriately configured state
				
	}


	/**
	 * Private helper method to shape the rows and columns in our puzzle state.
	 * 
	 * @param tt
	 * @param crow
	 * @param ccol
	 * @param nrow
	 * @param ncol
	 */
	private void swap(char[][] tt, int crow, int ccol, int nrow, int ncol) {

		char temp = tt[crow][ccol];
		tt[crow][ccol] = tt[nrow][ncol];
		tt[nrow][ncol] = temp;
		
	}


	/**
	 * Allows our puzzle state instance to work with hash table based sets and
	 * other collections that rely on a working hashCode function.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(tiles);
		return result;
	}


	/**
	 * Allows us to compare two puzzles by comparing their tiles/chars and
	 * return true if their tiles are configured the same.
	 * 
	 * @see Arrays.deepEquals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuzState other = (PuzState) obj;
		if (!Arrays.deepEquals(tiles, other.tiles))
			return false;
		return true;
	}
	




	
	
}
