package ca.university.myapplication.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages a list of Game Configurations
 */
public class GameConfigManager implements Iterable<GameConfig> {
	private static final String INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE = "index out of bounds.";
	private List<GameConfig> gameConfigs;

	public GameConfigManager() {
		gameConfigs = new ArrayList<>();
	}

	public void addGameConfig(String name, int expectedPoorScore, int expectedGreatScore) {
		addGameConfig(new GameConfig(name, expectedPoorScore, expectedGreatScore));
	}

	public void addGameConfig(GameConfig gameConfig) {
		gameConfigs.add(gameConfig);
	}

	public GameConfig getGameConfig(int index) {
		if (index < 0 || index > gameConfigs.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		return gameConfigs.get(index);
	}

	public void removeGameConfig(int index) {
		if (index < 0 || index > gameConfigs.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		gameConfigs.remove(index);
	}

	public int totalGameConfigs() {
		return gameConfigs.size();
	}

	@Override
	public Iterator<GameConfig> iterator() {
		return gameConfigs.iterator();
	}
}
