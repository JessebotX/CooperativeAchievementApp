package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class AchievementScreenActivity extends AppCompatActivity {
	private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
	private static final String EXTRA_GAME_INDEX = "extra_game_index";
	private GameConfigManager manager;
	private GameConfig gameConfig;
	private Game currentGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement_screen);

		getSupportActionBar().setTitle("Achievement Screen");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		manager = GameConfigManager.getInstance();
		extractGameConfigExtra();
		display();
	}

	private void extractGameConfigExtra() {
		Intent intent = getIntent();
		int gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX, 0);
		int gameIndex = intent.getIntExtra(EXTRA_GAME_INDEX, 0);
		gameConfig = manager.getConfig(gameConfigIndex);
		currentGame = gameConfig.getGame(gameIndex);
	}

	public static Intent makeIntent(Context context, int gameConfigIndex, int gameIndex) {
		Intent intent = new Intent(context, AchievementScreenActivity.class);
		intent.putExtra(EXTRA_GAME_CONFIG_INDEX, gameConfigIndex);
		intent.putExtra(EXTRA_GAME_INDEX, gameIndex);
		return intent;
	}

	private void display() {
		//image
		ImageView imageView = findViewById(R.id.iv_achievement_image);
		imageView.setImageResource(fillImage(currentGame));

		//difficulty level
		TextView textDifficulty = findViewById(R.id.tv_achievement_difficulty);
		setDifficultyLevelText(textDifficulty, currentGame);

		//achievement level
		TextView textLevel = findViewById(R.id.tv_achievement_name);
		setAchievementLevelText(textLevel, currentGame);
	}

	//get id for the image matching with the achievement
	// themes[0] = animals, themes[1] = resources/minerals, themes[2] = weapons
	private int fillImage(Game game) {
		int theme = manager.getTheme();
		int achievement = game.getAchievementLevel();

		String drawableName = "theme_" + theme + "_level_" + achievement;
		try {
			return getResources().getIdentifier(drawableName, "drawable", getPackageName());
		} catch (Exception e) {
			return R.drawable.fireworks;
		}
	}

	//set text of achievement level
	private void setAchievementLevelText(TextView textLevel, Game currentGame) {
		String[][] themes = new String[][]{
				getResources().getStringArray(R.array.achievement_theme_animals),
				getResources().getStringArray(R.array.achievement_theme_resources),
				getResources().getStringArray(R.array.achievement_theme_weapons)
		};
		int theme = manager.getTheme();

		int level = currentGame.getAchievementLevel();
		textLevel.setText(themes[theme][level]);
	}

	//set text of difficulty level
	private void setDifficultyLevelText(TextView textLevel, Game currentGame) {
		double difficulty = currentGame.getDifficultyModifier();
		if (difficulty == 0.75) {
			textLevel.setText(getString(R.string.difficulty_easy));
		} else if (difficulty == 1) {
			textLevel.setText(getString(R.string.difficulty_medium));
		} else if (difficulty == 1.25) {
			textLevel.setText(getString(R.string.difficulty_hard));
		}
	}
}