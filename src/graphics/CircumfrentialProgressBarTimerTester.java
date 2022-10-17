package graphics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * This class performs a litany of tests on the CircumfrentialProgressBarTimer
 * class.
 * 
 * This class extends CPBT in order to get access the private methods.
 * 
 * @author wades39
 *
 */
class CircumfrentialProgressBarTimerTester {

	/* --| VARIABLES |-- */
	private CircumfrentialProgressBarTimer testTimer;

	/* --| TESTS |-- */

	/**
	 * Test that the initialization is done as planned.
	 */
	@Test
	void classInstantiatesCorrectly() {
		testTimer = new CircumfrentialProgressBarTimer();
		assertEquals(testTimer.isUsingDefaultColors, true);
		assertEquals(testTimer.colorCodes, CircumfrentialProgressBarTimer.DEFAULT_COLORS);
		assertEquals(testTimer.targetTime, 1);
		assertEquals(testTimer.timeRemaining, 1);
	}

	/**
	 * Test to ensure that the timeRemaining is properly parsed as a string in
	 * human-readable format.
	 */
	@Test
	void timeStringsParseCorrectly() {

		testTimer = new CircumfrentialProgressBarTimer();
		// prevent exceptions being thrown
		testTimer.setTargetTime(90000000);

		HashMap<Long, String> timesEncoded = new HashMap<>();

		timesEncoded.put((long) 1, "00:00.001");
		timesEncoded.put((long) 10, "00:00.010");
		timesEncoded.put((long) 100, "00:00.100");
		timesEncoded.put((long) 1000, "00:01.000");
		timesEncoded.put((long) 10 * 1000, "00:10.000");
		timesEncoded.put((long) 60 * 1000, "01:00.000");
		timesEncoded.put((long) 10 * 60 * 1000, "10:00.000");
		timesEncoded.put((long) 60 * 60 * 1000, "00:00.000");
		timesEncoded.put((long) 10 * 60 * 60 * 1000, "00:00.000");
		timesEncoded.put((long) (59 * 60 * 1000) + (59 * 1000) + 999, "59:59.999");

		for (Long key : timesEncoded.keySet()) {
			testTimer.tick(key);
			assertEquals(timesEncoded.get(key), testTimer.parseTimeString());
		}

	}

	/**
	 * Test to ensure that the tick method overwrites the timeRemaining variable.
	 */
	@Test
	void testTickOverwritesTimeRemaining() {

		testTimer = new CircumfrentialProgressBarTimer();

		testTimer.setTargetTime(3700000);

		for (int i = 0; i < 3600000; i++) {
			testTimer.tick(i);
			assertEquals(i, testTimer.timeRemaining);
		}
	}

	/**
	 * Ensure that testTimer will throw exceptions when appropriate
	 */
	@Test
	void testTickThrowsErrorWhenPassedInvalidValues() {

		testTimer = new CircumfrentialProgressBarTimer();

		testTimer.setTargetTime(1);
		assertThrows(IllegalArgumentException.class, () -> {
			testTimer.tick(2);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			testTimer.tick(-2);
		});

		assertDoesNotThrow(() -> {
			testTimer.tick(1);
			testTimer.setTargetTime(7000);
			for (int i = 0; i < 7000; i++) {
				testTimer.tick(i);
			}
		});
	}

	/**
	 * Test to ensure that setTargetTime overwrites targetTime, that it returns true
	 * when targetTime is changed and false when it isn't.
	 */
	@Test
	void testSetTargetTime() {
		testTimer = new CircumfrentialProgressBarTimer();
		testTimer.tick(0);
		testTimer.setTargetTime(1l);

		for (long i = 2l; i < 3600000l; i++) {
			assertTrue(testTimer.setTargetTime(i));
			assertEquals(i, testTimer.targetTime);
			assertFalse(testTimer.setTargetTime(i));
		}
	}

	/**
	 * Test to ensure that setTargetTime will throw exceptions when appropriate.
	 */
	@Test
	void testSetTargetTimeThrowsErrorWhenPassedInvalidValues() {
		testTimer = new CircumfrentialProgressBarTimer();

		testTimer.setTargetTime(70);
		testTimer.tick(70);

		assertThrows(IllegalArgumentException.class, () -> {
			testTimer.setTargetTime(69);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			testTimer.setTargetTime(0);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			testTimer.setTargetTime(-70);
		});

		assertDoesNotThrow(() -> {
			for (int i = 70; i < 200; i++) {
				testTimer.setTargetTime(i);
			}
		});
	}

	
	// stagetext
	// colorTheme
	// fontSize
	// hexToInt
	// getColorFromProgress (gonna be difficult)
}
