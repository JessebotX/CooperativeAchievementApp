package ca.university.myapplication.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Game Configuration that manages a list of Games
 */
public class GameConfig implements Iterable<Game> {
	private static final String INDEX_OUT_OF_BOUNDS_ERROR_MESSAGE = "index out of bounds.";

	private String name;
	private int expectedGreatScore;
	private int expectedPoorScore;
	private List<Game> games;

	/**
	 * Create a new Game Configuration
	 * @param name the game name (eg. Monopoly, Via Magica, etc.)
	 * @param expectedPoorScore the expected poor performance of an individual
	 * @param expectedGreatScore the expected great performance of an individual
	 */
	public GameConfig(String name, int expectedPoorScore, int expectedGreatScore) {
		setName(name);
		this.expectedPoorScore = expectedPoorScore;
		this.expectedGreatScore = expectedGreatScore;
		this.games = new ArrayList<>();
	}

	public String getName() {
		return name;
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
		if (gameIndex < 0 || gameIndex >= this.games.size()) {
			throw new IllegalArgumentException("Player Index out of range. Please enter an index" +
					" between " + 0 + "-" + (newPlayerScores.size() - 1));
		}
		Game gameToEdit = this.games.get(gameIndex);
		gameToEdit.setPlayerScores(newPlayerScores);
		gameToEdit.setPlayers(newPlayerScores.size());
		gameToEdit.setDifficultyModifier(difficultyModifier);
	}

	public void addGame(int players, ArrayList<Integer> playerScores) {
		addGame(players, playerScores, Game.NORMAL_DIFFICULTY);
	}

	public void addGame(int players, ArrayList<Integer> playerScores, double difficultyModifier) {
		if (players > playerScores.size() || players < 0) {
			throw new IllegalArgumentException("Player Index out of range. Please enter an index" +
					" between " + 0 + "-" + (playerScores.size() - 1));
		}
		Game game = new Game(players, playerScores, expectedPoorScore, expectedGreatScore, difficultyModifier);
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
