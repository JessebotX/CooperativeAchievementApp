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

	@Test
	public void getGameThrowsError() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		gameConfig.addGame(4, 100);
		gameConfig.addGame(100, 100000);

		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(-1));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(2));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(3));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(Integer.MAX_VALUE));

	}

	@Test
	public void removeGameThrowsError() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		gameConfig.addGame(4, 100);
		gameConfig.addGame(100, 100000);

		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(-1));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(2));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(3));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(Integer.MAX_VALUE));

		gameConfig.removeGame(1);
		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(1));
	}
}