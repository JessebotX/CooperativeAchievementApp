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
public class Game {
	private static final int MIN_PLAYERS = 1;
	private static final int ACHIEVEMENT_LEVELS = 8; // not including level 0

	private String name;
	private int players;
	private int totalScore;
	private int expectedPoorScore;
	private int expectedGoodScore;

	public Game() {
		// default, requires manually calling setters
	}

	public Game(String name, int players, int totalScore, int expectedPoorScore, int expectedGoodScore) {
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

	// TODO test this out!
	public int getAchievementLevel() {
		double greatPoorDifference = expectedGoodScore - expectedPoorScore;

		if (totalScore < expectedPoorScore) {
			return 0; // level 0
		} else if (totalScore < expectedGoodScore) {
			for (int level = 1; level < ACHIEVEMENT_LEVELS; level++) {
				// eg. if level = 1; poor = 40; great = 100; then difference = 60
				// (60 / (8-1) * 1) + 40
				//   (60 / 7 * 1) + 40
				//   (8.5.. * 1) + 40
				//   = 48.5...
				// anything < 48.5... is considered level 1.
				// divide difference by 7 because between poor and great, have 7 separations
				// anything below poor = level 0, >= great means level 8

				double levelCap = (greatPoorDifference / (ACHIEVEMENT_LEVELS - 1) * level);

				if (totalScore < levelCap + expectedPoorScore) {
					return level;
				}
			}
		}

		return ACHIEVEMENT_LEVELS;
	}
}
