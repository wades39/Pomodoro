package function;

import graphics.MainWindow;

/**
 * Handles the launching of the application.
 * 
 * @author wades39
 *
 */
public class Launcher {

	/* --| ENTRY POINT |-- */

	/**
	 * Instantiate the application
	 * 
	 * @param args - Command line arguments
	 */
	public static void main(String[] args) {

		// indicates whether the user has set their preferences or not.
		boolean hasPreferences = false;

		// try to load user preferences
		if (PreferencesLoader.DoesPreferenceFileExist(Preferences.USER_PREFERENCE_FILE_LOCATION)) {
			hasPreferences = true;
			PreferencesLoader.LoadPrefs();
		}

		// use the preferences to load the application correctly
		new MainWindow(hasPreferences);
	}
}
