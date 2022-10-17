package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.JFrame;

/**
 * A graphical timer whose progress is represented by a circular arc, the
 * percentage of which is drawn represents the percentage of the time that
 * remains on the timer.
 * 
 * @author wades39
 *
 */
public class CircumfrentialProgressBarTimer extends JFrame implements GraphicalTimer {

	/* --| CONSTANTS |-- */
	/**
	 * generated serialVersionUID
	 */
	protected static final long serialVersionUID = 979316319138638808L;

	protected static final int POINTS_PER_INCH = 72;

	protected static final double STAGE_TEXT_PERCENTAGE_OF_MIN_DIM = 0.3;

	protected static final double TIME_REMAINING_PERCENT_OF_MIN_DIM = 0.2;

	protected static final String[] DEFAULT_COLORS = { "#2d728f", "#f5cb5c", "#db324d" };

	/* --| VARIABLES |-- */

	/**
	 * The time remaining for this timer in ms
	 */
	protected long timeRemaining;

	/**
	 * The target time for this timer in ms
	 */
	protected long targetTime;

	/**
	 * The current StageText to render
	 */
	protected String stageText;

	/**
	 * Indicates whether or not to use default colors.
	 */
	protected boolean isUsingDefaultColors;

	/**
	 * Stores the color codes that will be used.
	 */
	protected String[] colorCodes;

	/**
	 * timeRemaining formatted into min:sec.ms format
	 */
	private String timeStr;

	private FontMetrics fm;

	private Graphics2D g2;

	/**
	 * Instantiates a CircumfrentialProgressBarTimer
	 */
	public CircumfrentialProgressBarTimer() {
		super();

		// instantiate to use default colors.
		colorCodes = new String[] {};
		setColorTheme(true);

		// these exist to prevent the code from crashing when instantiating the CPBT
		this.targetTime = 1;
		this.timeRemaining = 1;

		// set a preferred size to ensure we're always rendering at a size where
		// everything's legible.
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(new Dimension((int) res.getWidth() / 10, (int) res.getHeight() / 10));
	}

	/**
	 * The method that draws the graphics used in the timer.
	 */
	public void paint(Graphics g) {
		// get Graphics2D which will allow for anti-aliasing and stroke modulation
		g2 = (Graphics2D) g;

		// set g2 to draw with anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear the rendering field
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());

		// calculate the diameter to render with
		int minDimension = Math.min(this.getWidth(), this.getHeight());
		int diameter = (int) (0.8 * minDimension);
		// calculate the angle of the arc
		int arcAngle = (int) (365.0 * (timeRemaining / targetTime));

		// calculate font sizes
		int stageTextFontSize = fontSizeAsPercentOfDimension(minDimension, STAGE_TEXT_PERCENTAGE_OF_MIN_DIM);
		int timeRemainingFontSize = fontSizeAsPercentOfDimension(minDimension, TIME_REMAINING_PERCENT_OF_MIN_DIM);
		int strokeWidth = (int) (0.1 * minDimension);

		// set stroke
		g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		// find the color halfway between the background color and its opposite
		Color bgColor = g2.getBackground();
		Color oppositeBGColor = new Color(255 - bgColor.getRed() / 4, 255 - bgColor.getGreen() / 4,
				255 - bgColor.getBlue() / 4);

		// draw arcs
		g2.setColor(oppositeBGColor);
		g2.drawArc((int) ((this.getWidth() - diameter) / 2), (int) ((this.getHeight() - diameter) / 2), diameter,
				diameter, 90, 360);
		g2.setColor(getColorFromProgress());
		g2.drawArc((int) ((this.getWidth() - diameter) / 2), (int) ((this.getHeight() - diameter) / 2), diameter,
				diameter, 90, arcAngle);

		// draw text
		g2.setFont(new Font("Arial", Font.BOLD, stageTextFontSize));
		fm = g2.getFontMetrics();
		g2.drawString(stageText, (this.getWidth() - fm.stringWidth(stageText)) / 2,
				(this.getHeight() - fm.stringWidth(stageText)) / 2 - fm.getHeight());

		g2.setFont(new Font("Arial", Font.PLAIN, timeRemainingFontSize));
		fm = g2.getFontMetrics();
		timeStr = parseTimeString();
		g2.drawString(timeStr, (this.getWidth() - fm.stringWidth(timeStr)) / 2,
				(this.getHeight() - fm.stringWidth(timeStr)) / 2 + fm.getHeight());

	}

	/**
	 * Parse the timeRemaining into a string format.
	 * 
	 * @return Time-formatted String representation of timeRemaining.
	 */
	protected String parseTimeString() {
		int min = (int) ((timeRemaining % (60 * 60 * 1000)) / (60 * 1000));
		double sec = (timeRemaining % (60 * 1000)) / (1000.0);
		return String.format("%02d:", min, sec) + (new DecimalFormat("00.000").format(sec));
	}

	/**
	 * Calculates the color to use from the colorCodes, with 1/n being the
	 * percentage that the color appears at.
	 * 
	 * @return
	 */
	protected Color getColorFromProgress() {

		// start with the first color
		int lastRed = hexToInt(colorCodes[0].substring(1, 3));
		int lastGreen = hexToInt(colorCodes[0].substring(1, 3));
		int lastBlue = hexToInt(colorCodes[0].substring(1, 3));

		// go through the colors in the list until we reach the color i s.t. 1/i <
		// timeRemaining/targetTime
		int i;
		for (i = 1; i < (colorCodes.length - 1)
				&& (((double) timeRemaining / (double) targetTime) > 1.0 / ((double) i + 1)); i++) {
			lastRed = hexToInt(colorCodes[i].substring(1, 3));
			lastGreen = hexToInt(colorCodes[i].substring(1, 3));
			lastBlue = hexToInt(colorCodes[i].substring(1, 3));
		}

		// this is the next color, the one drawn at rem/targ == 1/i
		int nextRed = hexToInt(colorCodes[i].substring(1, 3));
		int nextGreen = hexToInt(colorCodes[i].substring(1, 3));
		int nextBlue = hexToInt(colorCodes[i].substring(1, 3));

		// find the percentage of each color to take into the calculation of the next
		// color
		double percentLastColor = (((double) timeRemaining / (double) targetTime) - (1.0 / i))
				/ ((1.0 / i) - (1.0 / (i - 1)));

		// calculate and return the new color
		return new Color((int) ((percentLastColor * lastRed) + ((1 - percentLastColor) * nextRed)),
				(int) ((percentLastColor * lastGreen) + ((1 - percentLastColor) * nextGreen)),
				(int) ((percentLastColor * lastBlue) + ((1 - percentLastColor) * nextBlue)));
	}

	/**
	 * Converts a hex string to an integer.
	 * 
	 * @param hex - Hex string
	 * @return Integer decoded from the hexadecimal string.
	 */
	protected int hexToInt(String hex) {
		return Integer.parseInt(hex, 16);
	}

	/**
	 * Returns a font size as a percentage of a dimension.
	 * 
	 * @param dimension - The dimension for the font size to be based on.
	 * @param percent   - The percent expressed in decimal format (30% == 0.3)
	 * @return Font Size
	 */
	protected int fontSizeAsPercentOfDimension(int dimension, double percent) {
		return (int) ((POINTS_PER_INCH * dimension * percent) / Toolkit.getDefaultToolkit().getScreenResolution());
	}

	@Override
	public void tick(long timeRemaining) {
		if (timeRemaining > targetTime)
			throw new IllegalArgumentException("timeRemaining cannot be larger than the target time!");
		if (timeRemaining < 0)
			throw new IllegalArgumentException("timeRemaining cannot be negative!");

		this.timeRemaining = timeRemaining;
		repaint();
	}

	@Override
	public boolean setTargetTime(long newTargetTime) {
		if (newTargetTime < timeRemaining)
			throw new IllegalArgumentException("newTargetTime cannot be less than timeRemaining!");
		if (newTargetTime <= 0)
			throw new IllegalArgumentException("targetTime cannot be <= 0!");

		if (this.targetTime == newTargetTime)
			return false;

		this.targetTime = newTargetTime;

		if (targetTime < timeRemaining)
			timeRemaining = targetTime;

		return true;
	}

	@Override
	public boolean setStageText(String newStageText) {
		if (this.stageText.equals(newStageText))
			return false;
		this.stageText = newStageText;
		return true;
	}

	@Override
	public boolean setColorTheme(boolean useDefaultColors, String... hexColorStrings) {
		if (isUsingDefaultColors && useDefaultColors)
			return false;

		if (colorCodes.equals(hexColorStrings))
			return false;

		isUsingDefaultColors = useDefaultColors;

		if (isUsingDefaultColors) {
			colorCodes = DEFAULT_COLORS;
		} else {
			colorCodes = hexColorStrings;
		}
		return true;
	}

}
