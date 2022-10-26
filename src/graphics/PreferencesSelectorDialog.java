package graphics;

import java.awt.Dialog;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JDialog;

/**
 * A window that allows the user to modify their preferences for the
 * application.
 * 
 * @author wades39
 *
 */
public class PreferencesSelectorDialog implements ComponentListener {

	/* --| CONSTANTS |-- */
	private static final String PREFERENCES_SELECTOR_WINDOW_TITLE = "Pomodoro Preferences";

	/* --| VARIABLES |-- */

	/**
	 * The root frame of this application.
	 */
	private JDialog frame;

	/* --| METHODS |-- */

	/**
	 * Instantiate and show the PreferencesSelector
	 * 
	 * @param parent - The parent of this dialog
	 */
	public PreferencesSelectorDialog(JDialog parent) {

		// boilerplate window setup
		frame = new JDialog(parent, PREFERENCES_SELECTOR_WINDOW_TITLE, Dialog.ModalityType.APPLICATION_MODAL);

		// handle closing the window
		frame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		frame.addComponentListener(this);

		// TODO : render listings for all preference options

		// show the window
		frame.pack();
		frame.setVisible(true);
	}

	/*	--| INTERFACE OVERRIDES |--	*/
	
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
		// the component is only hidden when the close button is pressed. If the close
		// button is pressed, dispose of this window.

		// TODO : implement a warning for when preferences are changed but not saved
		// before closing. Handle user input before closing the window.

		frame.dispose();
	}
}
