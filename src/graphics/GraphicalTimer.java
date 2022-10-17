package graphics;

/**
 * An interface that defines how graphical timers will function.
 * 
 * @author wades39
 *
 */
public interface GraphicalTimer {

	/**
	 * Performs a tick, updating the time remaining, and triggers a new render
	 * event.
	 * 
	 * @param timeRemaining - The time left in this stage, used in rendering the
	 *                      graphics.
	 */
	public void tick(long timeRemaining);

	/**
	 * Sets the target time for the graphical timer from which the graphics will be
	 * rendered.
	 * 
	 * @param newTargetTime - The new target time from which the graphics will be
	 *                      rendered.
	 * @return True if a change was made, false otherwise.
	 */
	public boolean setTargetTime(long newTargetTime);

	/**
	 * Sets the stage text for the graphical timer. This text tells the user what
	 * stage they are in.
	 * 
	 * @param newStageText - The text used to indicate the stage the user is in.
	 * @return True if a change was made, false otherwise.
	 */
	public boolean setStageText(String newStageText);

	/**
	 * Allows for the setting of colors to use when rendering the timer.
	 * 
	 * @param hexColorStrings - A comma-separated list of hex color codes in string
	 *                        format: e.g.
	 *                        setColorTheme("#FFFFFF","#000000","#7F7F7F")
	 * @return True if a change is made, false otherwise.
	 */
	public boolean setColorTheme(boolean useDefaultColors, String... hexColorStrings);

}
