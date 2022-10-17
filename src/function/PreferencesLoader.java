package function;

import java.io.File;

/**
 * Handles the loading of stored user preferences.
 * 
 * @author wades39
 *
 */
public class PreferencesLoader {

	/* --| METHODS |-- */

	/**
	 * Probes to see whether the user preferences have been saved.
	 * 
	 * @param fileLoc - The location where the preferences are stored.
	 * @return True if the preferences file exists, false if not.
	 */
	public static boolean DoesPrefenceFileExist(String fileLoc) {
		return ((new File(fileLoc)).exists());
	}

	/**
	 * Loads the preferences into the Preferences class.
	 */
	public static void LoadPrefs() {

	}
}
