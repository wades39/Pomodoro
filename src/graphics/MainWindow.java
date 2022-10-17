package graphics;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JDialog;

/**
 * This class declares and defines the function of the main window of the
 * program.
 * 
 * @author wades39
 *
 */
public class MainWindow implements ComponentListener {

	/* --| CONSTANTS |-- */
	private static String WINDOW_TITLE = "Pomodoro Timer";

	/* --| VARIABLES |-- */

	/**
	 * The parent component of the main GUI.
	 * 
	 * Using JDialog to prevent users from being able to minimize the app.
	 */
	private JDialog windowFrame;

	/**
	 * Indicates whether the app should force breaks to be taken by going full
	 * screen and preventing users from working.
	 */
	private boolean breaksAreForced;

	/* --| METHODS |-- */

	/**
	 * Instantiate a MainWindow
	 */
	public MainWindow() {
		// boilerplate setup
		windowFrame = new JDialog();
		windowFrame.setTitle(WINDOW_TITLE);

		// set up how to handle the window being closed
		windowFrame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		windowFrame.addComponentListener(this);

		// set up how to handle forced breaks
		if (breaksAreForced) {

		}

		// pack the window and display it to the user
		windowFrame.pack();
		windowFrame.setVisible(true);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// do nothing
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// do nothing
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// do nothing
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// since this will only be applied to the windowFrame, we know that when the
		// window is hidden, the app is being closed. Ergo, we exit.

		System.exit(0);
	}
}
