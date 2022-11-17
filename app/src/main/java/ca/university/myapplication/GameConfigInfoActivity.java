package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

/**
 * This Game Config Info class helps users view the scores for each achievement given the number of
 * players involved. This activity also helps the user add a new game to each game config.
 */
public class GameConfigInfoActivity extends AppCompatActivity {

	private static final String EXTRA_GAME_CONFIG_INDEX = "EXTRA_GAME_CONFIG_INDEX";

	GameConfigManager gameConfigManager;
	GameConfig gameConfig;
	Game game;

	int gameConfigIndex;

	TextView[] scoreViews;

	EditText userInput;

	FloatingActionButton fab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_config_info);

		getSupportActionBar().setTitle("Game Config Info");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// extract data
		userInput = findViewById(R.id.etNumPlayersGameConfig);

		gameConfigIndex = extractDataFromIntent();

		scoreViews = getAchievementTextViews();
		gameConfigManager = GameConfigManager.getInstance();

		setAchievementLabelTextViews();

		gameConfig = gameConfigManager.getConfig(gameConfigIndex);
		game = new Game(1, 0, gameConfig.getExpectedPoorScore(), gameConfig.getExpectedGreatScore());

		setUpListener();
		setUpFab();
		setUpViewListButton();
	}

	private void setUpViewListButton() {
		Button saveButton = findViewById(R.id.btnGameConfigViewGameList);

		saveButton.setOnClickListener(view -> {
			Intent intent = ListGamesActivity.makeIntent(GameConfigInfoActivity.this, gameConfigIndex);
			startActivity(intent);
		});
	}

	private void setUpFab() {
		fab = findViewById(R.id.GameConfigAddFab);

		fab.setOnClickListener(view -> {
			// start add game activity: uncomment after merge
            Intent addGameIntent = AddGameActivity.makeIntent(GameConfigInfoActivity.this, gameConfigIndex);
            startActivity(addGameIntent);
		});
	}

	/**
	 * Set up listener for the number of players text
	 */
	private void setUpListener() {

		userInput.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				refreshScoreView();
			}
		});
	}

	/**
	 * @return an array of text views for all achievement
	 */
	private TextView[] getAchievementTextViews() {
		return new TextView[]{
				findViewById(R.id.tvAchievement0),
				findViewById(R.id.tvAchievement1),
				findViewById(R.id.tvAchievement2),
				findViewById(R.id.tvAchievement3),
				findViewById(R.id.tvAchievement4),
				findViewById(R.id.tvAchievement5),
				findViewById(R.id.tvAchievement6),
				findViewById(R.id.tvAchievement7),
		};
	}

	/**
	 * @return an array of text views for achievement display names
	 */
	private TextView[] getAchievementLabelsTextViews() {
		return new TextView[] {
				findViewById(R.id.tvAchievementLabel0),
				findViewById(R.id.tvAchievementLabel1),
				findViewById(R.id.tvAchievementLabel2),
				findViewById(R.id.tvAchievementLabel3),
				findViewById(R.id.tvAchievementLabel4),
				findViewById(R.id.tvAchievementLabel5),
				findViewById(R.id.tvAchievementLabel6),
				findViewById(R.id.tvAchievementLabel7),
				findViewById(R.id.tvAchievementLabel8),
		};
	}

	private void setAchievementLabelTextViews() {
		String[][] themes = new String[][] {
			getResources().getStringArray(R.array.achievement_theme_animals),
			getResources().getStringArray(R.array.achievement_theme_resources),
			getResources().getStringArray(R.array.achievement_theme_weapons)
		};

		TextView[] views = getAchievementLabelsTextViews();
		int theme = gameConfigManager.getTheme();

		for (int i = 0; i < views.length; i++) {
			views[i].setText(themes[theme][i]);
		}
	}

	/**
	 * helper function to update the UI for all achievements
	 */
	private void refreshScoreView() {
		String numPlayersText = userInput.getText().toString();

		// exit early if input is not entered
		if (!isInt(numPlayersText) || Integer.parseInt(numPlayersText) < 1) {
			Toast.makeText(this, "Please Finish Entering Inputs.", Toast.LENGTH_SHORT).show();
			convertAllAchievementsToBlank();
			return;
		}

		int numPlayers = Integer.parseInt(numPlayersText);

		setUpScoreViews(numPlayers);
	}

	private void convertAllAchievementsToBlank() {
		for(TextView tv: scoreViews) {
			tv.setText("-");
		}
	}

	// helper function to check if an string is an integer
	public static boolean isInt(String str) {
		try {
			@SuppressWarnings("unused")
			int x = Integer.parseInt(str);
			return true; //String is an Integer
		} catch (NumberFormatException e) {
			return false; //String is not an Integer
		}
	}

	/**
	 * updates all Achievement scores given the number of players
	 * @param numPlayers user input
	 */
	private void setUpScoreViews(int numPlayers) {
		Game tempGame = new Game(numPlayers, game.getTotalScore(), game.getExpectedPoorScore(), game.getExpectedGreatScore());
		int[] achievementLevels = tempGame.getAchievementLevelRequiredScores();
		int i = 0;
		for (TextView scoreView : scoreViews) {
			scoreView.setText(Integer.toString(achievementLevels[i]));
			i++;
		}
	}

	/**
	 * Returns the Intent for game config info activity
	 * @param context
	 * @param gameConfigIndex
	 * @return
	 */
	public static Intent makeIntent(Context context, int gameConfigIndex) {
		Intent intent = new Intent(context, GameConfigInfoActivity.class);
		intent.putExtra(EXTRA_GAME_CONFIG_INDEX, gameConfigIndex);
		return intent;
	}

	/**
	 * helper function to extract data from intent
	 * @return
	 */
	private int extractDataFromIntent() {
		Intent intent = getIntent();
		return intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX, -1);
	}
}