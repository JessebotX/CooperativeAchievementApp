package ca.university.myapplication.model;

import java.time.LocalDateTime;

/**
 * Represents a single game.
 *
 * Stores: Game name; num players; Total score; Expected individual poor/great scores
 */
public class Game {
	private static final int MIN_PLAYERS = 1;
	private static final int ACHIEVEMENT_LEVELS = 8; // not including level 0

	private int players;
	private int totalScore;
	private int expectedPoorScore;
	private int expectedGoodScore;

	public Game() {
		// default, requires manually calling setters
	}

	public Game(int players, int totalScore, int expectedPoorScore, int expectedGoodScore) {
		setPlayers(players);
		this.totalScore = totalScore;
		this.expectedPoorScore = expectedPoorScore;
		this.expectedGoodScore = expectedGoodScore;
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

	/**
	 * Take the expected great/poor individual scores, the number of players, and the total score
	 * to determine the cooperative achievement level (from 0 to 8).
	 *
	 * TODO testing required.
	 *
	 * @return an integer from 0 to 8 (inclusive) that represents achievement level
	 */
	public int getAchievementLevel() {
		int collectiveGreatScore = expectedGoodScore * players;
		int collectivePoorScore = expectedPoorScore * players;
		double greatPoorDifference = collectiveGreatScore - collectivePoorScore;

		// eg. if level = 1; poor = 40; great = 100; then difference = 60
		// (60 / (8-1) * 1) + 40
		//   (60 / 7 * 1) + 40
		//   (8.5.. * 1) + 40
		//   = 48.5...
		// anything < 48.5... is considered level 1.
		// divide difference by 7 because between poor and great, have 7 separations
		// anything below poor = level 0, >= great means level 8
		if (totalScore < collectivePoorScore) {
			return 0;
		} else if (totalScore < collectiveGreatScore) {
			for (int level = 1; level < ACHIEVEMENT_LEVELS; level++) {
				double thresholdMark = (greatPoorDifference / (ACHIEVEMENT_LEVELS - 1) * level);

				if (totalScore < (thresholdMark + collectivePoorScore)) {
					return level;
				}
			}
		}

		return ACHIEVEMENT_LEVELS;
	}
}
