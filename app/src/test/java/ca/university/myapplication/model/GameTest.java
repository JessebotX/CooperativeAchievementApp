package ca.university.myapplication.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the Game Model class
 */
public class GameTest {
	@Test
	public void constructorThrowsError() {
		assertThrows(IllegalArgumentException.class, () -> new Game(0, 0, 0 ,0));
		assertThrows(IllegalArgumentException.class, () -> new Game(-1, 0, 0 ,0));
	}

	@Test
	public void setPlayersThrowsError() {
		Game game = new Game(2, 10, 10, 10);

		assertThrows(IllegalArgumentException.class, () -> game.setPlayers(0));
		assertThrows(IllegalArgumentException.class, () -> game.setPlayers(-1));
	}

	@Test
	public void getCorrectAchievementLevelRequirementsScores1to8() {
		Game game = new Game(4, 4, 1, 8);

		for (int i = 0; i < 8; i++) {
			assertEquals(4 * (i + 1), game.getAchievementLevelThresholds()[i]);
		}
	}

	@Test
	public void getCorrectAchievementLevelRequirementsScores40to100() {
		Game game = new Game(1, 1, 40, 100);

		int[] levelRequirements = { 40, 48, 57, 65, 74, 82, 91, 100 };

		for (int i = 0; i < levelRequirements.length; i++) {
			assertEquals(levelRequirements[i], game.getAchievementLevelThresholds()[i]);
		}
	}

	@Test
	public void getCorrectAchievementLevel0Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(0);
		assertEquals(0, game.getAchievementLevel());

		game.setTotalScore(-1);
		assertEquals(0, game.getAchievementLevel());

		game.setTotalScore(3);
		assertEquals(0, game.getAchievementLevel());

		game.setTotalScore(4);
		assertEquals(1, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel1Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(4);
		assertEquals(1, game.getAchievementLevel());

		game.setTotalScore(7);
		assertEquals(1, game.getAchievementLevel());

		game.setTotalScore(8);
		assertEquals(2, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel2Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(8);
		assertEquals(2, game.getAchievementLevel());

		game.setTotalScore(11);
		assertEquals(2, game.getAchievementLevel());

		game.setTotalScore(12);
		assertEquals(3, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel3Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(12);
		assertEquals(3, game.getAchievementLevel());

		game.setTotalScore(15);
		assertEquals(3, game.getAchievementLevel());

		game.setTotalScore(16);
		assertEquals(4, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel4Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(16);
		assertEquals(4, game.getAchievementLevel());

		game.setTotalScore(19);
		assertEquals(4, game.getAchievementLevel());

		game.setTotalScore(20);
		assertEquals(5, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel5Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(20);
		assertEquals(5, game.getAchievementLevel());

		game.setTotalScore(23);
		assertEquals(5, game.getAchievementLevel());

		game.setTotalScore(24);
		assertEquals(6, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel6Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(24);
		assertEquals(6, game.getAchievementLevel());

		game.setTotalScore(27);
		assertEquals(6, game.getAchievementLevel());

		game.setTotalScore(28);
		assertEquals(7, game.getAchievementLevel());
	}

	@Test
	public void getCorrectAchievementLevel7And8Scores1to8with4Players() {
		Game game = new Game(4, 0, 1, 8);

		game.setTotalScore(28);
		assertEquals(7, game.getAchievementLevel());

		game.setTotalScore(31);
		assertEquals(7, game.getAchievementLevel());

		game.setTotalScore(32);
		assertEquals(8, game.getAchievementLevel());

		game.setTotalScore(Integer.MAX_VALUE);
		assertEquals(8, game.getAchievementLevel());
	}
}