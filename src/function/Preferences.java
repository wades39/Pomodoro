package function;

import java.util.HashMap;

/**
 * Houses the user's preferences in a way that are accessible to other classes.
 * 
 * @author wades39
 *
 */
public class Preferences {

	/**
	 * HashMap housing the user's preferences
	 */
	private static HashMap<String, Object> preferencesMap = new HashMap<>();

	/* --| CONSTANTS |-- */

	/**
	 * The location of the user's set preferences.
	 */
	public static final String USER_PREFERENCE_FILE_LOCATION = "prefs/pomodoro_user_prefs.pref";

	/**
	 * The location of the default preferences that come with the app.
	 */
	protected static final String DEFAULT_USER_PREFERENCE_FILE_LOCATION = "prefs/default_user_prefs.pref";

	/* --| PREFERENCE KEYS |-- */

	/**
	 * Key for the FORCE_BREAKS preference
	 */
	public static final String FORCE_BREAKS = "FORCE_BREAKS";

	/**
	 * Key for the RENDER_AS_WINDOWED preference
	 */
	public static final String RENDER_AS_WINDOWED = "RENDER_AS_WINDOWED";

	/**
	 * Key for the USE_CUSTOM_TIMING preference
	 */
	public static final String USE_CUSTOM_TIMING = "USE_CUSTOM_TIMING";

	/**
	 * Key for the CUSTOM_TIMING_FILE_LOC preference
	 */
	public static final String CUSTOM_TIMING_FILE_LOC = "CUSTOM_TIMING_FILE_LOC";

	/* --| FUNCTIONS |-- */

	/**
	 * Initiates the preferences map with all of the available preferences.
	 */
	public static void initPreferences() {
		setPreference(FORCE_BREAKS, null);
		setPreference(RENDER_AS_WINDOWED, null);
		setPreference(USE_CUSTOM_TIMING, null);
		setPreference(CUSTOM_TIMING_FILE_LOC, null);

	}

	/**
	 * Retrieves the value for a preference.
	 * 
	 * @param key - The preference to search for
	 * @return True if the preference is instantiated in the map, false otherwise.
	 */
	public static boolean doesPrefExist(String key) {
		return preferencesMap.containsKey(key);
	}

	/**
	 * Sets the value of a given preference.
	 * 
	 * @param key   - The preference to set.
	 * @param value - The value of the preference.
	 * @return The last value associated with the preference.
	 */
	public static Object setPreference(String key, Object value) {
		if (!preferencesMap.containsKey(key))
			throw new IllegalArgumentException(String.format(
					"PREFERENCE NOT FOUND:  \"%s\"\nThe provided key does not match any existing Preference.", key));
		return preferencesMap.put(key, value);
	}

	/**
	 * Gets the value of a given preference.
	 * 
	 * @param key - The preference to retrieve.
	 * @return The current value of the requested preference.
	 */
	public static Object getPreference(String key) {
		return preferencesMap.get(key);
	}
}
