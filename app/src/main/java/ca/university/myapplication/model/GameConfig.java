package ca.university.myapplication.model;

/**
 * Represents a single game configuration.
 *
 * Stores
 * - Game name
 * - Players
 * - Total score
 * - Date and time of creation
 * - Expected scores
 * - Achievement level
 */
public class GameConfig {
	public static final int MIN_PLAYERS = 1;
	private String name;
	private int players;
	private int totalScore;
	private int expectedPoorScore;
	private int expectedGoodScore;

	public GameConfig() {
		// default, requires manually calling setters
	}

	public GameConfig(String name, int players, int totalScore, int expectedPoorScore, int expectedGoodScore) {
		setName(name);
		setPlayers(players);
		this.totalScore = totalScore;
		this.expectedPoorScore = expectedPoorScore;
		this.expectedGoodScore = expectedGoodScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException("Game name cannot be empty.");
		}

		this.name = name;
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		if (players < MIN_PLAYERS) {
			throw new IllegalArgumentException("Players cannot be less than " + MIN_PLAYERS);
		}

		this.players = players;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getExpectedPoorScore() {
		return expectedPoorScore;
	}

	public void setExpectedPoorScore(int expectedPoorScore) {
		this.expectedPoorScore = expectedPoorScore;
	}

	public int getExpectedGoodScore() {
		return expectedGoodScore;
	}

	public void setExpectedGoodScore(int expectedGoodScore) {
		this.expectedGoodScore = expectedGoodScore;
	}
}
