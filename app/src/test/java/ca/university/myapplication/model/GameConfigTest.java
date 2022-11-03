package ca.university.myapplication.model;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

/**
 * Test out GameConfig
 */
public class GameConfigTest {
	@Test
	public void constructorThrowsError() {
		// Name cannot be blank
		assertThrows(IllegalArgumentException.class, () -> new GameConfig("", 1, 8));
		assertThrows(IllegalArgumentException.class, () -> new GameConfig(" ", 1, 8));
		assertThrows(IllegalArgumentException.class, () -> new GameConfig("     ", 1, 8));
	}

	@Test
	public void setNameThrowsError() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		assertThrows(IllegalArgumentException.class, () -> gameConfig.setName(""));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.setName(" "));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.setName("        "));
	}
}