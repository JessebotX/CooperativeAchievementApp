package ca.university.myapplication.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Game Configuration that manages a list of Games
 */
public class GameConfig implements Iterable<Game> {
	public static final String DEFAULT_PHOTO_BASE64 = "";
	private static final String INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE = "index out of bounds.";

	private String name;
	private int expectedGreatScore;
	private int expectedPoorScore;
	private List<Game> games;
	private String photoAsBase64;

	/**
	 * Create a new Game Configuration
	 * @param name the game name (eg. Monopoly, Via Magica, etc.)
	 * @param expectedPoorScore the expected poor performance of an individual
	 * @param expectedGreatScore the expected great performance of an individual
	 */
	public GameConfig(String name, int expectedPoorScore, int expectedGreatScore) {
		this(name, expectedPoorScore, expectedGreatScore, DEFAULT_PHOTO_BASE64);
	}

	public GameConfig(String name, int expectedPoorScore, int expectedGreatScore, String photoAsBase64) {
		setName(name);
		this.expectedPoorScore = expectedPoorScore;
		this.expectedGreatScore = expectedGreatScore;
		this.games = new ArrayList<>();
		this.photoAsBase64 = photoAsBase64;
	}

	public String getName() {
		return name;
	}

	public String getPhotoAsBase64() {
		return photoAsBase64;
	}

	public void setPhotoAsBase64(String photoAsBase64) {
		this.photoAsBase64 = photoAsBase64;
	}

	/**
	 * Set a new game name.
	 * @param name A non-empty non-blank string value.
	 */
	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException("Game configuration name cannot be blank.");
		}

		this.name = name;
	}

	public int getExpectedGreatScore() {
		return expectedGreatScore;
	}

	public void setExpectedGreatScore(int expectedGreatScore) {
		this.expectedGreatScore = expectedGreatScore;
	}

	public int getExpectedPoorScore() {
		return expectedPoorScore;
	}

	public void setExpectedPoorScore(int expectedPoorScore) {
		this.expectedPoorScore = expectedPoorScore;
	}

	public Game getGame(int index) {
		if (index < 0 || index >= games.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		return games.get(index);
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public void editGame(int gameIndex, ArrayList<Integer> newPlayerScores, double difficultyModifier) {
		editGame(gameIndex, newPlayerScores, difficultyModifier, Game.DEFAULT_PHOTO_BASE64);
	}

	public void editGame(int gameIndex, ArrayList<Integer> newPlayerScores, double difficultyModifier, String base64Photo) {
		if (gameIndex < 0 || gameIndex >= this.games.size()) {
			throw new IllegalArgumentException("Player Index out of range. Please enter an index" +
					" between " + 0 + "-" + (newPlayerScores.size() - 1));
		}
		Game gameToEdit = this.games.get(gameIndex);
		gameToEdit.initializeAchievementLevelThresholds();
		gameToEdit.setPlayerScores(newPlayerScores);
		gameToEdit.setPlayers(newPlayerScores.size());
		gameToEdit.setDifficultyModifier(difficultyModifier);
		gameToEdit.setPhotoAsBase64(base64Photo);
	}

	public void addGame(int players, ArrayList<Integer> playerScores) {
		addGame(players, playerScores, Game.NORMAL_DIFFICULTY);
	}

	public void addGame(int players, ArrayList<Integer> playerScores, double difficultyModifier) {
		addGame(players, playerScores, difficultyModifier, Game.DEFAULT_PHOTO_BASE64);
	}

	public void addGame(int players, ArrayList<Integer> playerScores, double difficultyModifier, String base64Photo) {
		if (players > playerScores.size() || players < 0) {
			throw new IllegalArgumentException("Player Index out of range. Please enter an index" +
					" between " + 0 + "-" + (playerScores.size() - 1));
		}
		Game game = new Game(players, playerScores, expectedPoorScore, expectedGreatScore, difficultyModifier, base64Photo);
		games.add(game);
	}

	public void removeGame(int index) {
		if (index < 0 || index >= games.size()) {
			throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE);
		}

		games.remove(index);
	}

	/**
	 * Get the amount of games added to this game configuration
	 * @return >= 0 amount of games
	 */
	public int totalGames() {
		return this.games.size();
	}

	@Override
	public Iterator<Game> iterator() {
		return this.games.iterator();
	}
}
