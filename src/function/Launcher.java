package function;

/**
 * Handles the launching of the application.
 * 
 * @author wades39
 *
 */
public class Launcher {

	/* --| VARIABLES |-- */

	/* --| ENTRY POINT |-- */

	/**
	 * Instantiate the application
	 * 
	 * @param args - Command line arguments
	 */
	public static void main(String[] args) {

		// try to load user preferences
		if (PreferencesLoader.DoesPreferenceFileExist(Preferences.USER_PREFERENCE_FILE_LOCATION)) {
			PreferencesLoader.LoadPrefs();
		}

		// if the user preferences haven't been initialized yet, create the user
		// preferences file
		else {

		}

		// use the preferences to load the application correctly
	}
}
