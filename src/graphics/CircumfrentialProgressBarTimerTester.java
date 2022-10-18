package graphics;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Random;

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
		assertEquals(testTimer.colorsToUse, CircumfrentialProgressBarTimer.DEFAULT_COLORS);
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

	/**
	 * Test to ensure that setStageText works as expected.
	 */
	@Test
	void testSetStageTextOverwritesStageText() {
		testTimer = new CircumfrentialProgressBarTimer();

		// default stageText should be empty String
		assertEquals("", testTimer.stageText);

		for (int i = 0; i < 20; i++) {
			assertTrue(testTimer.setStageText(i + ""));
			assertEquals(i + "", testTimer.stageText);
			assertFalse(testTimer.setStageText(i + ""));
		}
	}

	/**
	 * Test to ensure that the colorTheme can be updated with the setColorTheme
	 * method
	 */
	@Test
	void testSetColorTheme() {
		testTimer = new CircumfrentialProgressBarTimer();

		// default colorTheme is to use the default colors.
		assertEquals(CircumfrentialProgressBarTimer.DEFAULT_COLORS, testTimer.colorsToUse);

		// test that usingDefaultColors doesn't change colorsToUse from default, even
		// when colors are provided.
		{
			testTimer.setColorTheme(true);
			assertEquals(CircumfrentialProgressBarTimer.DEFAULT_COLORS, testTimer.colorsToUse);

			testTimer.setColorTheme(true, new Color(255, 255, 255));
			assertEquals(CircumfrentialProgressBarTimer.DEFAULT_COLORS, testTimer.colorsToUse);

			testTimer.setColorTheme(true, new Color(255, 255, 255), new Color(0, 0, 0));
			assertEquals(CircumfrentialProgressBarTimer.DEFAULT_COLORS, testTimer.colorsToUse);
		}

		// test that when not usingDefaultColors, they're overwritten and that an error
		// is thrown when no colors are provided.
		{
			Color[] cols;

			cols = new Color[] { new Color(255, 255, 255) };
			testTimer.setColorTheme(false, cols);

			assertEquals(cols.length, testTimer.colorsToUse.length);
			for (int i = 0; i < cols.length; i++)
				assertTrue(cols[i].equals(testTimer.colorsToUse[i]));

			cols = new Color[] { new Color(255, 255, 255), new Color(0, 0, 0) };
			testTimer.setColorTheme(false, new Color(255, 255, 255), new Color(0, 0, 0));

			assertEquals(cols.length, testTimer.colorsToUse.length);
			for (int i = 0; i < cols.length; i++)
				assertTrue(cols[i].equals(testTimer.colorsToUse[i]));

			// test that, when overwriting the colorsToUse, exceptions are thrown when no
			// colors are provided.
			assertThrows(IllegalArgumentException.class, () -> {
				testTimer.setColorTheme(false, new Color[] {});
			});

			assertThrows(IllegalArgumentException.class, () -> {
				testTimer.setColorTheme(false);
			});

		}

		testTimer.setColorTheme(true);

		// Test to see that setColorTheme returns the appropriate value when used.

		Color colorToAdd;
		Random rand = new Random();

		for (int i = 0; i < 100; i++) {
			colorToAdd = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

			assertTrue(testTimer.setColorTheme(false, colorToAdd));
			assertFalse(testTimer.setColorTheme(false, colorToAdd));

		}
	}

	/**
	 * Test that fontSizeAsAPercentOfDimension works properly.
	 */
	@Test
	void testFontSizeAsPercentOfDimension() {
		int ppi = 72;
		int res = Toolkit.getDefaultToolkit().getScreenResolution();
		int dimHeight = 1000;

		// formula for font size in terms of a pixel count
		// (ppi * heightInPixels) / screenResolution

		for (int i = 1; i < 1280; i++) {

			// i = ppi * dim * perc / res
			// res * i = ppi * dim * perc
			// res * i / ppi / dim = perc
			double perc = (double) (res * i) / (double) (ppi * dimHeight);

			assertEquals(i, CircumfrentialProgressBarTimer.fontSizeAsPercentOfDimension(dimHeight, perc));
		}
	}

	/**
	 * Tests that getColorFromProgress can find the correct color between two colors
	 * based on the proportion of the timeRemaining/targetTime.
	 */
	@Test
	void testGetColorFromProgress() {

		testTimer = new CircumfrentialProgressBarTimer();

		long target = 1000;

		testTimer.setTargetTime(target);

		// test with default colors
		{
			/*
			 * CircumfrentialProgressBarTimer has 3 default colors.
			 * 
			 * Color progression is as follows: c0:c1 / [100%:50%) c1:c2 / [50%:25%) c2 /
			 * [25%:0%]
			 */

			// test for transformation between the first and second colors.
			for (long i = target; i > target / 2; i--) {
				// update the timeRemaining
				testTimer.tick(i);

				Color anticipatedCol = testTimer.getColorFromProgress();

				double ratio = (i - (target / 2d)) / (double) (target - (target / 2d));

				int anticipatedRed = (int) ((ratio * CircumfrentialProgressBarTimer.DEFAULT_COLORS[0].getRed())
						+ ((1 - ratio) * CircumfrentialProgressBarTimer.DEFAULT_COLORS[1].getRed()));
				int anticipatedGreen = (int) ((ratio * CircumfrentialProgressBarTimer.DEFAULT_COLORS[0].getGreen())
						+ ((1 - ratio) * CircumfrentialProgressBarTimer.DEFAULT_COLORS[1].getGreen()));
				int anticipatedBlue = (int) ((ratio * CircumfrentialProgressBarTimer.DEFAULT_COLORS[0].getBlue())
						+ ((1 - ratio) * CircumfrentialProgressBarTimer.DEFAULT_COLORS[1].getBlue()));

				assertTrue(new Color(anticipatedRed, anticipatedGreen, anticipatedBlue).equals(anticipatedCol));
			}

			// test for transformation between the second and third colors.
			for (long i = target / 2; i > target / 4; i--) {
				// update the timeRemaining
				testTimer.tick(i);

				Color anticipatedCol = testTimer.getColorFromProgress();

				double ratio = (i - (target / 4d)) / (double) ((target / 2d) - (target / 4d));

				int anticipatedRed = (int) ((ratio * CircumfrentialProgressBarTimer.DEFAULT_COLORS[1].getRed())
						+ ((1 - ratio) * CircumfrentialProgressBarTimer.DEFAULT_COLORS[2].getRed()));
				int anticipatedGreen = (int) ((ratio * CircumfrentialProgressBarTimer.DEFAULT_COLORS[1].getGreen())
						+ ((1 - ratio) * CircumfrentialProgressBarTimer.DEFAULT_COLORS[2].getGreen()));
				int anticipatedBlue = (int) ((ratio * CircumfrentialProgressBarTimer.DEFAULT_COLORS[1].getBlue())
						+ ((1 - ratio) * CircumfrentialProgressBarTimer.DEFAULT_COLORS[2].getBlue()));

				assertTrue(new Color(anticipatedRed, anticipatedGreen, anticipatedBlue).equals(anticipatedCol));

			}

			// test that color doesn't change on last color
			for (long i = target / 4; i > -1; i--) {
				testTimer.tick(i);

				Color anticipatedCol = testTimer.getColorFromProgress();

				assertTrue(CircumfrentialProgressBarTimer.DEFAULT_COLORS[2].equals(anticipatedCol));
			}

			// test that the color doesn't change when it only has one color
			{
				testTimer.setColorTheme(false, new Color(255, 0, 0));

				for (long i = target; i > -1; i--) {
					testTimer.tick(i);

					Color anticipated = testTimer.getColorFromProgress();

					assertTrue(new Color(255, 0, 0).equals(anticipated));
				}
			}

			// test that transitioning between two identical colors doesn't result in issues
			{
				target = 10;
				testTimer.setTargetTime(target);

				testTimer.setColorTheme(false, new Color(255, 0, 0), new Color(255, 0, 0));

				for (long i = target; i > -1; i--) {
					testTimer.tick(i);

					Color anticipated = testTimer.getColorFromProgress();

					assertTrue(new Color(255, 0, 0).equals(anticipated));
				}
			}

			// test that transitioning between two different colors locks color at halfway
			// point
			{
				target = 1000;
				testTimer.setTargetTime(target);

				testTimer.setColorTheme(false, new Color(255, 0, 0), new Color(255, 255, 0));

				for (long i = target; i > target / 2; i--) {
					testTimer.tick(i);

					Color anticipated = testTimer.getColorFromProgress();

					double ratio = (i - (target / 2d)) / (double) (target - (target / 2d));

					int anticipatedRed = (int) ((ratio * 255) + ((1 - ratio) * 255));
					int anticipatedGreen = (int) ((ratio * 0) + ((1 - ratio) * 255));
					int anticipatedBlue = (int) ((ratio * 0) + ((1 - ratio) * 0));

					assertTrue(new Color(anticipatedRed, anticipatedGreen, anticipatedBlue).equals(anticipated));
				}

				for (long i = target / 2; i > -1; i--) {
					testTimer.tick(i);

					Color anticipated = testTimer.getColorFromProgress();

					assertTrue(new Color(255, 255, 0).equals(anticipated));
				}
			}
		}
	}
}
