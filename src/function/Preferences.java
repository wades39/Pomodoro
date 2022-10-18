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
	public static final String USER_PREFERENCE_FILE_LOCATION = "pomodoro_user_prefs.pref";

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
	
	public static void initPreferences() {
		setPreference(FORCE_BREAKS, null);
		setPreference(RENDER_AS_WINDOWED, null);
		setPreference(USE_CUSTOM_TIMING, null);
		setPreference(CUSTOM_TIMING_FILE_LOC, null);
		
	}

	public static boolean doesPrefExist(String key) {
		return preferencesMap.containsKey(key);
	}

	public static Object setPreference(String key, Object value) {
		return preferencesMap.put(key, value);
	}

	public static Object getPreference(String key) {
		return preferencesMap.get(key);
	}
}
