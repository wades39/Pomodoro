package graphics;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class defines how a preference will be displayed in a list.
 * 
 * @author wades39
 *
 */
public class PreferenceListItem extends JPanel {

	/* --| CONSTANTS |-- */

	/**
	 * generated SerialVersionUID
	 */
	private static final long serialVersionUID = 1930381241448920446L;

	/* --| VARIABLES |-- */
	private JPanel leftSubPanel, rightSubPanel;
	private JLabel preferenceLabel;

	/* --| METHODS |-- */

	/**
	 * Instantiate a PreferenceListItem
	 * 
	 * @param preferenceName - The name of the preference represented by this
	 *                       ListItem
	 * @param preferenceType - The preferenceType of this preference
	 */
	public PreferenceListItem(String preferenceName, Class<?> preferenceType) {

		// set up this panel
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		// set the layout to be a row of items.
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// create and populate the leftSubPanel
		leftSubPanel = new JPanel();
		preferenceLabel = new JLabel(preferenceName);
		leftSubPanel.add(preferenceLabel);
		leftSubPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		// create and populate the rightSubPanel
		rightSubPanel = new JPanel();
		rightSubPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		// TODO : create branching code for the different types of Preferences

		// add the subPanels to this panel
		this.add(leftSubPanel);
		this.add(rightSubPanel);
	}

	/**
	 * Returns the current value of the preference in this ListItem
	 * 
	 * @return the current value of the preference this ListItem represents
	 */
	public Object getPreferenceValue() {
		// TODO : implement once further development is made
		return null;
	}
}
