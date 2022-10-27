package function;

import java.security.CodeSource;

/**
 * Handles checking for whether or not this app is running as a .jar
 * 
 * @author wades39
 *
 */
public class JarChecker {

	public static boolean isRunningAsJar() {
		boolean inJar = false;
		
		try {
			CodeSource src = JarChecker.class.getProtectionDomain().getCodeSource();
			inJar = src.getLocation().toURI().getPath().endsWith(".jar");
		} catch (Exception e) {
			
		}
		
		return inJar;
	}
}
