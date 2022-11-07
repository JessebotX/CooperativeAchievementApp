package ca.university.myapplication.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages a list of Game Configurations
 */
public class GameConfigManager implements Iterable<GameConfig> {
	private static final String INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE = "index out of bounds.";
	private static GameConfigManager instance;
	private List<GameConfig> gameConfigs;

	public GameConfigManager() {
		gameConfigs = new ArrayList<>();
	}

	public static GameConfigManager getInstance() {
		if (instance == null) {
			instance = new GameConfigManager();
		}

		return instance;
	}

	public void addConfig(String name, int expectedPoorScore, int expectedGreatScore) {
		addConfig(new GameConfig(name, expectedPoorScore, expectedGreatScore));
	}

	public void addConfig(GameConfig gameConfig) {
		gameConfigs.add(gameConfig);
	}

	public GameConfig getConfig(int index) {
		if (index < 0 || index > gameConfigs.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		return gameConfigs.get(index);
	}

	public void removeConfig(int index) {
		if (index < 0 || index > gameConfigs.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		gameConfigs.remove(index);
	}

	public int totalConfigs() {
		return gameConfigs.size();
	}

	//NEW UTILITY METHOD------------------------
	public void setConfig(int index, String name, String poorScore, String greatScore){
		//input validation
		if(index < 0 || index > gameConfigs.size()){
			throw new IllegalArgumentException("index is out of bounds");
		}
		else {
			//get the config and set
			GameConfig editThisConfig = gameConfigs.get(index);
			editThisConfig.setName(name);
			int poorScoreInt = Integer.parseInt(poorScore);
			editThisConfig.setExpectedPoorScore(poorScoreInt);
			int greatScoreInt = Integer.parseInt(greatScore);
			editThisConfig.setExpectedGreatScore(greatScoreInt);
		}
	}
	//------------------------------------------

	@Override
	public Iterator<GameConfig> iterator() {
		return gameConfigs.iterator();
	}
}
