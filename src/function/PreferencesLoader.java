package function;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	public static boolean DoesPreferenceFileExist(String fileLoc) {
		return ((new File(fileLoc)).exists());
	}

	/**
	 * Loads the preferences into the Preferences class.
	 */
	public static void LoadPrefs() {

		Preferences.initPreferences();

		// Use a Scanner to read the preferences file
		if (DoesPreferenceFileExist(Preferences.USER_PREFERENCE_FILE_LOCATION)) {

			try (Scanner fileIn = new Scanner(new File(Preferences.USER_PREFERENCE_FILE_LOCATION))) {

				String line = "";
				while (fileIn.hasNextLine()) {
					line = fileIn.nextLine();

					Preferences.setPreference(line.split("|")[0], line.split("|")[1]);

				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
