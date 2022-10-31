package ca.university.myapplication.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages a list of Games
 */
public class GameConfig implements Iterable<Game> {
	private String name;
	private int expectedGreatScore;
	private int expectedPoorScore;
	private List<Game> games;

	public GameConfig(String name, int expectedPoorScore, int expectedGreatScore) {
		setName(name);
		this.expectedPoorScore = expectedPoorScore;
		this.expectedGreatScore = expectedGreatScore;
		this.games = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

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
		if (index < 0 || index > this.games.size()) {
			throw new IllegalArgumentException("index out of bounds when trying to get game.");
		}

		return this.games.get(index);
	}

	public void addGame(int players, int totalScore) {
		Game game = new Game(players, totalScore, expectedPoorScore, expectedGreatScore);
		this.games.add(game);
	}

	public void removeGame(int index) {
		if (index < 0 || index > this.games.size()) {
			throw new IllegalArgumentException("index out of bounds when trying to remove game.");
		}

		this.games.remove(index);
	}

	public int totalGames() {
		return this.games.size();
	}

	@Override
	public Iterator<Game> iterator() {
		return this.games.iterator();
	}
}
