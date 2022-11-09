package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

/**
 * This Add Game Activity class helps the user add a new game to a game info.
 */
public class AddGameActivity extends AppCompatActivity {

	private static final String EXTRA_GAME_INDEX = "EXTRA_GAME_INDEX";

	private GameConfig gameConfig;
	private GameConfigManager gameConfigManager;
	private Game newGame;

	private EditText inputNumPlayers;
	private EditText inputCombinedScore;
	private TextView tvAchievement;
	private Button saveButton;

	private int numPlayers;
	private int combinedScore;

	private String[] achievementNames = {
			"Shiny Butterflies (lowest)",
			"Busy Bees",
			"Adorable Chickens",
			"Fantastic Foxes",
			"Tactical Tigers",
			"Merciless Gorillas",
			"Rampaging Rhinoceros",
			"Whooping Whales",
			"Firebreathing Dragons"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game);

		getSupportActionBar().setTitle("Add Game Info");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		inputNumPlayers = findViewById(R.id.inputNumPlayers);
		inputCombinedScore = findViewById(R.id.inputCombinedScore);
		tvAchievement = findViewById(R.id.tvAchievement);
		saveButton = findViewById(R.id.btnSave);

		gameConfigManager = GameConfigManager.getInstance();

		// get Game index from caller
		int gameIndex = extractDataFromIntent();
		gameConfig = gameConfigManager.getConfig(gameIndex);

		setUpSaveButton();
		setUpInputListeners();
	}

	public static Intent makeIntent(Context context, int gameIndex) {
		Intent intent = new Intent(context, AddGameActivity.class);
		intent.putExtra(EXTRA_GAME_INDEX, gameIndex);
		return intent;
	}

	/**
	 * For each input box, we want to add a listener to update and recalculate the achievement level
	 * everytime the user enters something.
	 */
	private void setUpInputListeners() {

		EditText[] userInputs = {inputNumPlayers, inputCombinedScore};

		for (EditText userInput : userInputs) {
			userInput.addTextChangedListener(new TextWatcher() {
				public void afterTextChanged(Editable s) {
				}

				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				public void onTextChanged(CharSequence s, int start, int before, int count) {
					refreshAchievementText();
				}
			});
		}
	}

	/**
	 * Update UI dynamically when number of players or combined score is changed
	 */
	private void setUpSaveButton() {
		saveButton.setOnClickListener(view -> {

			String numPlayersText = inputNumPlayers.getText().toString();
			String combinedScoreText = inputCombinedScore.getText().toString();

			// exit early if input is not entered
			if (!isInt(numPlayersText) || !isInt(combinedScoreText) || Integer.parseInt(numPlayersText) < 1) {
				Toast.makeText(this, "Please Finish Entering Inputs.", Toast.LENGTH_SHORT).show();
				return;
			}

			numPlayers = Integer.parseInt(numPlayersText);
			combinedScore = Integer.parseInt(combinedScoreText);

			// add to config

			gameConfig.addGame(numPlayers, combinedScore);

			Toast.makeText(this, "New Game Saved!", Toast.LENGTH_SHORT).show();
			saveToSharedPreferences();
			finish();
		});
	}

	/**
	 * Refresh the current achievement level that the users reached
	 */
	private void refreshAchievementText() {
		String numPlayersText = inputNumPlayers.getText().toString();
		String combinedScoreText = inputCombinedScore.getText().toString();

		if (!isInt(numPlayersText) || !isInt(combinedScoreText) || Integer.parseInt(numPlayersText) < 1) {
			return;
		}

		int numPlayers = Integer.parseInt(numPlayersText);
		int numCombinedScore = Integer.parseInt(combinedScoreText);

		int achievementLevel = calculateAchievementLevel(numPlayers, numCombinedScore);
		// update text
		tvAchievement.setText(achievementNames[achievementLevel]);
	}

	/**
	 * uses the Game model to return a achivement level
	 * @param numPlayers
	 * @param combinedScore
	 * @return
	 */
	private int calculateAchievementLevel(int numPlayers, int combinedScore) {
		newGame = new Game(numPlayers, combinedScore, gameConfig.getExpectedPoorScore(), gameConfig.getExpectedGreatScore());
		return newGame.getAchievementLevel();
	}

	// helper function to check weather or not a string is a number
	public static boolean isInt(String str) {
		try {
			@SuppressWarnings("unused")
			int x = Integer.parseInt(str);
			return true; //String is an Integer
		} catch (NumberFormatException e) {
			return false; //String is not an Integer
		}
	}

	// helper function to extract data from intent
	private int extractDataFromIntent() {
		Intent intent = getIntent();
		return intent.getIntExtra(EXTRA_GAME_INDEX, -1);
	}

	private void saveToSharedPreferences() {
		SharedPreferences prefs = getSharedPreferences(ListGameConfigsActivity.APP_PREFERENCES, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		Gson gson = new Gson();

		String json = gson.toJson(gameConfigManager.getGameConfigs());
		editor.putString(ListGameConfigsActivity.SAVED_GAME_CONFIGS_KEY, json);
		editor.apply();
	}
}