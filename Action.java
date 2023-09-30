package cs211.puz;

/**
 * For common constants used through out this program.
 * <p>
 * Refer to this in your code.  Something like <code>Action.UP</code>.
 * </p>
 * <p>
 * When you need all possible actions,  use  <code>Action.ALL</code>.
 * </p>
 * @author mahiggs
 *
 */
public interface Action {
	
	public final static String UP = "up";
	public final static String DOWN = "down";
	public final static String LEFT = "left";
	public final static String RIGHT = "right";
	
	public final static String[] ALL = {UP, DOWN, LEFT, RIGHT};
	
}
