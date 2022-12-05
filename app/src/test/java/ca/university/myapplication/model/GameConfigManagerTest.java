package ca.university.myapplication.model;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Basic testing for GameConfigManager
 */
public class GameConfigManagerTest {
	@Test
	public void useSingleton() {
		GameConfigManager manager = GameConfigManager.getInstance();

		manager.addConfig("Hello world", 0, 7);
		manager.getConfig(manager.totalConfigs() - 1).setName("Monopoly");
		manager.removeConfig(0);

		manager.addConfig("Monopoly", 0, Integer.MAX_VALUE);
		manager.addConfig("Placeholder board game", 0, Integer.MAX_VALUE);

		for (GameConfig config : manager) {
			ArrayList<Integer> playerScores = new ArrayList<Integer>();
			playerScores.add(100);
			config.addGame(1, playerScores);
		}

		for (int i = manager.totalConfigs() - 1; i >= 0; i--) {
			manager.removeConfig(i);
		}
	}

	@Test
	public void getConfigThrowsErrors() {
		// should probably use singleton in production
		GameConfigManager manager = new GameConfigManager();

		manager.addConfig("Snakes and Ladders", 40, 100);
		manager.getConfig(0).setName("Via Magica");

		assertThrows(IllegalArgumentException.class, () -> manager.getConfig(1));
		assertThrows(IllegalArgumentException.class, () -> manager.getConfig(-1));
		assertThrows(IllegalArgumentException.class, () -> manager.getConfig(Integer.MAX_VALUE));
	}

	@Test
	public void removeConfigThrowsErrors() {
		// should probably use singleton in production
		GameConfigManager manager = new GameConfigManager();

		manager.addConfig("Snakes and Ladders", 40, 100);

		assertThrows(IllegalArgumentException.class, () -> manager.removeConfig(-1));
		assertThrows(IllegalArgumentException.class, () -> manager.removeConfig(1));
		assertThrows(IllegalArgumentException.class, () -> manager.removeConfig(Integer.MAX_VALUE));

		manager.removeConfig(0);
	}
}