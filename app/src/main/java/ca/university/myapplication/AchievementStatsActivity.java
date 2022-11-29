package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class AchievementStatsActivity extends AppCompatActivity {
	private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
	private GameConfigManager manager;
	private GameConfig gameConfig;
	private List<Game> gameList;
	private int gameConfigIndex;
	private int countLevel0, countLevel1, countLevel2, countLevel3, countLevel4
			, countLevel5, countLevel6, countLevel7, countLevel8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement_stats);

		manager = GameConfigManager.getInstance();
		extractGameConfigExtra();
		getStats();
	}

	public static Intent makeIntent(Context context, int gameConfigIndex) {
		Intent intent = new Intent(context, AchievementStatsActivity.class);
		intent.putExtra(EXTRA_GAME_CONFIG_INDEX, gameConfigIndex);
		return intent;
	}

	private void extractGameConfigExtra() {
		Intent intent = getIntent();
		gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX, 0);
		gameConfig = manager.getConfig(gameConfigIndex);
		gameList = gameConfig.getGames();
	}

	private void getStats() {
		for (int i = 0; i < gameConfig.totalGames(); i++) {
			int level = gameList.get(i).getAchievementLevel();
			switch(level) {
				case 0:
					countLevel0++;
					break;
				case 1:
					countLevel1++;
					break;
				case 2:
					countLevel2++;
					break;
				case 3:
					countLevel3++;
					break;
				case 4:
					countLevel4++;
					break;
				case 5:
					countLevel5++;
					break;
				case 6:
					countLevel6++;
					break;
				case 7:
					countLevel7++;
					break;
				case 8:
					countLevel8++;
					break;
			}
		}
	}
}