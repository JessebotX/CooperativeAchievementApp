package ca.university.myapplication.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages a list of Game Configurations
 */
public class GameConfigManager implements Iterable<GameConfig> {
	public static final int NUM_THEMES = 3;
	public static final int THEME_ANIMALS = 0;
	public static final int THEME_RESOURCES = 1;
	public static final int THEME_WEAPONS = 2;

	private static final String INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE = "index out of bounds.";

	private static GameConfigManager instance;
	private List<GameConfig> gameConfigs;
	private int theme;

	public GameConfigManager() {
		gameConfigs = new ArrayList<>();
		theme = 0;
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
		if (index < 0 || index >= gameConfigs.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		return gameConfigs.get(index);
	}

	public void removeConfig(int index) {
		if (index < 0 || index >= gameConfigs.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		gameConfigs.remove(index);
	}

	public List<GameConfig> getGameConfigs() {
		return gameConfigs;
	}

	public void setGameConfigs(List<GameConfig> gameConfigs) {
		this.gameConfigs = gameConfigs;
	}

	public int totalConfigs() {
		return gameConfigs.size();
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		if (theme > NUM_THEMES || theme < 0) {
			throw new IllegalArgumentException("Theme does not exist.");
		}
		this.theme = theme;
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
