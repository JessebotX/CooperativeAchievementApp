package ca.university.myapplication.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a single game.
 *
 * Stores: Game name; num players; Total score; Expected individual poor/great scores
 */
public class Game implements Comparable<Game> {
	private static final int MIN_PLAYERS = 1;
	private static final int ACHIEVEMENT_LEVELS = 8; // not including level 0

	private int players;
	private ArrayList<Integer> playerScores;
	private int expectedPoorScore;
	private int expectedGreatScore;
	private int[] achievementLevelRequiredScores;
	private CurrentDateTime timeOfCreation;

	/**
	 * Create a new Game under a GameConfig. Game creation should be done by GameConfig itself
	 * @param players number of players
	 * @param playerScores an arraylist to contain the scores for each player
	 * @param expectedPoorScore the expected poor score for a single individual
	 * @param expectedGoodScore the expected great score for a single individual
	 */
	public Game(int players, ArrayList<Integer> playerScores, int expectedPoorScore, int expectedGoodScore) {
		setPlayers(players);
		if (players != playerScores.size()) {
			throw new IllegalArgumentException("Number of Players must be equal to the number of" +
					" scores entered by the user.");
		}
		this.playerScores = playerScores;
		this.expectedPoorScore = expectedPoorScore;
		this.expectedGreatScore = expectedGoodScore;
		this.achievementLevelRequiredScores = new int[ACHIEVEMENT_LEVELS];
		this.timeOfCreation = new CurrentDateTime();

		initializeAchievementLevelThresholds();
	}

	public int getPlayers() {
		return players;
	}

	/**
	 * Set a new number of total players. Automatically reupdates the achievement level requirements
	 * list.
	 * @param players number of players >= 1
	 */
	public void setPlayers(int players) {
		if (players < MIN_PLAYERS) {
			throw new IllegalArgumentException("Players cannot be less than " + MIN_PLAYERS);
		}

		this.players = players;

		// reinitialize achievement levels
		initializeAchievementLevelThresholds();
	}

	public int getTotalScore() {
		int sum = 0;
		for (int i = 0; i < this.playerScores.size(); i++) {
			sum += this.playerScores.get(i);
		}
		return sum;
	}

	/***
	 * Edit the score for the player index i
	 * @param playerIndex the index of the player in the array
	 */
	public void setPlayerScore(int playerIndex, int newScoreValue) {
		if (playerIndex >= this.playerScores.size() || playerIndex < 0) {
			throw new IllegalArgumentException("Player Index out of range. Please enter an index" +
					" between " + 0 + "-" + (this.playerScores.size() -1));
		}
		this.playerScores.set(playerIndex, newScoreValue );
	}

	public void setPlayerScores(ArrayList<Integer> newPlayerScores) {
		this.playerScores = newPlayerScores;
		this.players = newPlayerScores.size();
	}

	public int getExpectedPoorScore() {
		return expectedPoorScore;
	}

	public void setExpectedPoorScore(int expectedPoorScore) {
		this.expectedPoorScore = expectedPoorScore;

		// reinitialize achievement levels
		initializeAchievementLevelThresholds();
	}

	public int getExpectedGreatScore() {
		return expectedGreatScore;
	}

	public void setExpectedGreatScore(int expectedGreatScore) {
		this.expectedGreatScore = expectedGreatScore;

		// reinitialize achievement levels
		initializeAchievementLevelThresholds();
	}

	/**
	 * Get a list of the required scores for each level from 1 to 8. Any total score below level 1
	 * (index 0) should be level 0 and any score greater or equal to level 8 (index 7) should be
	 * level 8
	 * @return A list of required scores for levels 1 to 8. NOTE: indices are zero indexed so level
	 * 1 scores are accessed by index 0.
	 */
	public int[] getAchievementLevelRequiredScores() {
		return achievementLevelRequiredScores;
	}

	/**
	 * Take the expected great/poor individual scores, the number of players, and the total score
	 * to determine the cooperative achievement level (from 0 to 8).
	 *
	 * @return an integer from 0 to 8 (inclusive) that represents achievement level. Level 0 is
	 * below the expected collective poor score while level 8 is >= expected collective great score
	 */
	public int getAchievementLevel() {
		for (int level = 0; level < ACHIEVEMENT_LEVELS; level++) {
			if (getTotalScore() < achievementLevelRequiredScores[level]) {
				return level;
			}
		}
		return ACHIEVEMENT_LEVELS;
	}

	public ArrayList<Integer> getPlayerScores() {
		return this.playerScores;
	}

	public int getPlayerScore(int playerIndex) {
		return this.playerScores.get(playerIndex);
	}

	public CurrentDateTime getTimeOfCreation() {
		return timeOfCreation;
	}

	private void initializeAchievementLevelThresholds() {
		this.achievementLevelRequiredScores = new int[ACHIEVEMENT_LEVELS];

		int collectiveGreatScore = expectedGreatScore * players;
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

		for (int i = 0; i < ACHIEVEMENT_LEVELS; i++) {
			double mark = (greatPoorDifference / (ACHIEVEMENT_LEVELS - 1) * i) + collectivePoorScore;
			achievementLevelRequiredScores[i] = (int)mark;
		}
	}

	@Override
	public int compareTo(Game game) {
		return this.timeOfCreation.compareTo(game.getTimeOfCreation());
	}
}
