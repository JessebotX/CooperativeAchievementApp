package ca.university.myapplication.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Test the Game Model class
 */
public class GameTest {
	@Test
	public void constructorThrowsError() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(0);
		assertThrows(IllegalArgumentException.class, () -> new Game(0, playerScores, 0 ,0));
		assertThrows(IllegalArgumentException.class, () -> new Game(-1, playerScores, 0 ,0));
	}

	@Test
	public void setPlayersThrowsError() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(10);
		playerScores.add(0);
		Game game = new Game(playerScores.size(), playerScores, 10, 10);

		assertThrows(IllegalArgumentException.class, () -> game.setPlayers(0));
		assertThrows(IllegalArgumentException.class, () -> game.setPlayers(-1));
	}

	@Test
	public void getCorrectAchievementLevelRequirementsScores1to8() {
		ArrayList<Integer> playerScores = new ArrayList<>();
		playerScores.add(4);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(playerScores.size(), playerScores, 1, 8);

		for (int i = 0; i < 8; i++) {
			assertEquals(4 * (i + 1), game.getAchievementLevelRequiredScores()[i]);
		}
	}

	@Test
	public void getCorrectAchievementLevelRequirementsScores40to100() {
		ArrayList<Integer> playerScores = new ArrayList<>();
		playerScores.add(1);
		Game game = new Game(1, playerScores, 40, 100);

		int[] levelRequirements = { 40, 48, 57, 65, 74, 82, 91, 100 };
		for (int i = 0; i < levelRequirements.length; i++) {
			assertEquals(levelRequirements[i], game.getAchievementLevelRequiredScores()[i]);
		}
	}



	@Test
	public void getCorrectAchievementLevel0Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		assertEquals(0, game.getAchievementLevel());

		game.setPlayerScore(0, -1);
		assertEquals(0, game.getAchievementLevel());

		game.setPlayerScore(0, 3);
		assertEquals(0, game.getAchievementLevel());

		game.setPlayerScore(0, 4);
		assertEquals(1, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel1Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		game.setPlayerScore(0, 4);
		assertEquals(1, game.getAchievementLevel());

		game.setPlayerScore(0, 7);
		assertEquals(1, game.getAchievementLevel());

		game.setPlayerScore(0, 8);
		assertEquals(2, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel2Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		game.setPlayerScore(0, 8);
		assertEquals(2, game.getAchievementLevel());

		game.setPlayerScore(0, 11);
		assertEquals(2, game.getAchievementLevel());

		game.setPlayerScore(0, 12);
		assertEquals(3, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel3Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		game.setPlayerScore(0, 12);
		assertEquals(3, game.getAchievementLevel());

		game.setPlayerScore(0, 15);
		assertEquals(3, game.getAchievementLevel());

		game.setPlayerScore(0, 16);
		assertEquals(4, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel4Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		game.setPlayerScore(0, 16);
		assertEquals(4, game.getAchievementLevel());

		game.setPlayerScore(0, 19);
		assertEquals(4, game.getAchievementLevel());

		game.setPlayerScore(0, 20);
		assertEquals(5, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel5Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		game.setPlayerScore(0, 20);
		assertEquals(5, game.getAchievementLevel());

		game.setPlayerScore(0, 23);
		assertEquals(5, game.getAchievementLevel());

		game.setPlayerScore(0, 24);
		assertEquals(6, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel6Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		game.setPlayerScore(0, 24);
		assertEquals(6, game.getAchievementLevel());

		game.setPlayerScore(0, 27);
		assertEquals(6, game.getAchievementLevel());

		game.setPlayerScore(0, 28);
		assertEquals(7, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel7And8Scores1to8with4Players() {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		Game game = new Game(4, playerScores, 1, 8);

		game.setPlayerScore(0, 28);
		assertEquals(7, game.getAchievementLevel());

		game.setPlayerScore(0, 31);
		assertEquals(7, game.getAchievementLevel());

		game.setPlayerScore(0, 32);
		assertEquals(8, game.getAchievementLevel());

		game.setPlayerScore(0, Integer.MAX_VALUE);
		assertEquals(8, game.getAchievementLevel());
	}

	@Test
	public void testSetPlayerScore() {
		ArrayList<Integer> playerScores5 = new ArrayList<>();
		playerScores5.add(0);
		playerScores5.add(0);
		playerScores5.add(0);
		playerScores5.add(0);
		Game game = new Game(4, playerScores5, 1, 8);

		assertEquals(0, game.getPlayerScore(0));

		game.setPlayerScore(0,100);
		assertEquals(100, game.getPlayerScore(0));

		game.setPlayerScore(0,100);
		game.setPlayerScore(1,100);
		game.setPlayerScore(2,100);
		game.setPlayerScore(3,100);
		assertEquals(100, game.getPlayerScore(0));
		assertEquals(100, game.getPlayerScore(1));
		assertEquals(100, game.getPlayerScore(2));
		assertEquals(100, game.getPlayerScore(3));

	}

	@Test
	public void testGetTotalScore() {
		ArrayList<Integer> playerScores5 = new ArrayList<Integer>();
		playerScores5.add(0);
		playerScores5.add(0);
		playerScores5.add(0);
		playerScores5.add(0);
		Game game = new Game(4, playerScores5, 1, 8);

		assertEquals(0, game.getTotalScore());

		game.setPlayerScore(0,100);
		assertEquals(100, game.getTotalScore());

		game.setPlayerScore(0,100);
		game.setPlayerScore(1,100);
		game.setPlayerScore(2,100);
		game.setPlayerScore(3,100);
		assertEquals(400, game.getTotalScore());

	}
}