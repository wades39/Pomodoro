package function;

/**
 * Houses the user's preferences in a way that are accessible to other classes.
 * 
 * @author wades39
 *
 */
public class Preferences {

	/* --| PREFERENCE VARIABLES |-- */

	/**
	 * Indicates whether or not to force breaks by maximizing the window and
	 * preventing window movement during breaks.
	 */
	private static boolean FORCE_BREAKS;

	/**
	 * Indicates whether or not to render the application in windowed mode or
	 * undecorated.
	 * 
	 * True == render with frame decorations, false == undecorated.
	 */
	private static boolean RENDER_AS_WINDOWED;

	/**
	 * Indicates whether or not to use custom timing for the Pomodoro timer.
	 * 
	 * Custom timing means timing is set differently than the standard Pomodoro
	 * Technique times.
	 */
	private static boolean USE_CUSTOM_TIMING;
	
	/**
	 * Points to the Custom Timing File.
	 */
	private static String CUSTOM_TIMING_FILE_LOC;
}
