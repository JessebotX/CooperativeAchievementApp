package ca.university.myapplication.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Test out GameConfig
 */
public class GameConfigTest {
	@Test
	public void constructorThrowsError() {
		// Name cannot be blank
		assertThrows(IllegalArgumentException.class, () -> new GameConfig("", 1, 8));
		assertThrows(IllegalArgumentException.class, () -> new GameConfig(" ", 1, 8));
		assertThrows(IllegalArgumentException.class, () -> new GameConfig("     ", 1, 8));
	}

	@Test
	public void setNameThrowsError() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		assertThrows(IllegalArgumentException.class, () -> gameConfig.setName(""));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.setName(" "));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.setName("        "));
	}

	@Test
	public void getGameThrowsError() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(100);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		gameConfig.addGame(4, playerScores);

		ArrayList<Integer> playerScores2 = new ArrayList<Integer>();
		playerScores2.add(100000);
		playerScores2.add(0);
		playerScores2.add(0);
		playerScores2.add(0);
		playerScores2.add(0);
		gameConfig.addGame(5, playerScores2);

		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(-1));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(2));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(3));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.getGame(Integer.MAX_VALUE));
	}

	@Test
	public void accessingGame() {
		GameConfig gameConfig = new GameConfig("Placeholder Game", 1, 8);

		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(100);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);
		gameConfig.addGame(4, playerScores);

		gameConfig.getGame(0).setPlayers(5);

		Game game = gameConfig.getGame(0);
		int level = game.getAchievementLevel();
		int players = game.getPlayers();
		int score = game.getTotalScore();

		assertEquals(5, players);
		assertEquals(40, game.getAchievementLevelRequiredScores()[7]);
		assertEquals(8, level);
		assertEquals(100, score);
	}

	@Test
	public void removeGameThrowsError() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		playerScores.add(100);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);

		gameConfig.addGame(4, playerScores);

		ArrayList<Integer> playerScores2 = new ArrayList<Integer>();
		playerScores2.add(10000);
		playerScores2.add(0);
		playerScores2.add(0);
		playerScores2.add(0);
		assertThrows(IllegalArgumentException.class, () -> gameConfig.addGame(100, playerScores2));

		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(-1));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(2));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(3));
		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(Integer.MAX_VALUE));

		assertThrows(IllegalArgumentException.class, () -> gameConfig.removeGame(1));
	}

	@Test
	public void editGameTest() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		ArrayList<Integer> playerScores = new ArrayList<>();
		playerScores.add(100);
		playerScores.add(0);
		playerScores.add(0);
		playerScores.add(0);

		gameConfig.addGame(4, playerScores);

		ArrayList<Integer> newPlayerScores = new ArrayList<>();
		newPlayerScores.add(5);
		newPlayerScores.add(0);
		newPlayerScores.add(0);
		newPlayerScores.add(0);

		gameConfig.editGame(0, newPlayerScores, Game.HARD_DIFFICULTY);

		assertEquals(newPlayerScores, gameConfig.getGame(0).getPlayerScores());
	}

	@Test
	public void totalGamesTest() {
		GameConfig gameConfig = new GameConfig("Snakes & Ladders", 40, 100);

		ArrayList<Integer> playerScores1 = new ArrayList<>();
		playerScores1.add(100);
		playerScores1.add(0);
		playerScores1.add(0);

		ArrayList<Integer> playerScores2 = new ArrayList<>();
		playerScores2.add(5);
		playerScores2.add(0);
		playerScores2.add(0);

		gameConfig.addGame(playerScores1.size(), playerScores1);
		gameConfig.addGame(playerScores2.size(), playerScores2);

		assertEquals(2, gameConfig.totalGames());
	}

}