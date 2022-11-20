package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

/**
 * This Add Game Activity class helps the user add a new game to a game info.
 */
public class AddGameActivity extends AppCompatActivity {
	private static final int DEFAULT = -1;
	private static final String EXTRA_GAME_INDEX = "EXTRA_GAME_INDEX";
	private static final String EXTRA_CONFIG_INDEX = "EXTRA_CONFIG_INDEX";
	private static final int MIN_PLAYERS = 1;

	private GameConfig gameConfig;
	private GameConfigManager gameConfigManager;
	private Game newGame;
	private Game currentGame;

	private int configIndex;
	private int gameIndex;

	private List<EditText> newInputPlayerScores = new ArrayList<>();
	private EditText inputNumPlayers;
	private TextView tvAchievement;
	private Button saveButton;

	private Boolean editActivity = false;
	private int numPlayers;

	private String[][] achievementNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		extractDataFromIntent();
		Toast.makeText(this, "configIndex: " + configIndex+ "gameIndex: " + gameIndex + " in editgame: " + editActivity, Toast.LENGTH_SHORT).show();

		initializeFields();
		setupPlayerInputs();
		setUpSaveButton();

		if (editActivity) {
			setUpForEditActivity();
		} else{
			TextView tv = findViewById(R.id.AddGameTextView);
			tv.setText("Add New Game");
		}
	}

	private void setUpForEditActivity() {
		inputNumPlayers.setText(Integer.toString(currentGame.getPlayers()));
		setUpIndividualPlayerInputs();
	}

	public static Intent makeIntent(Context context, int configIndex) {
		Intent intent = new Intent(context, AddGameActivity.class);
		intent.putExtra(EXTRA_CONFIG_INDEX, configIndex);
		return intent;
	}

	public static Intent makeIntent(Context context,int configIndex, int gameIndex) {
		Intent intent = new Intent(context, AddGameActivity.class);
		intent.putExtra(EXTRA_CONFIG_INDEX,configIndex);
		intent.putExtra(EXTRA_GAME_INDEX, gameIndex);
		return intent;
	}

	private void initializeFields() {
		gameConfigManager = GameConfigManager.getInstance();
		gameConfig = gameConfigManager.getConfig(configIndex);
		if (editActivity) {
			currentGame = gameConfig.getGame(gameIndex);
		}

//
		achievementNames = new String[][] {
				getResources().getStringArray(R.array.achievement_theme_animals),
				getResources().getStringArray(R.array.achievement_theme_resources),
				getResources().getStringArray(R.array.achievement_theme_weapons)
		};

		inputNumPlayers = findViewById(R.id.inputNumPlayers);
		tvAchievement = findViewById(R.id.tvAchievement);
		saveButton = findViewById(R.id.btnSave);
	}

	private void setupPlayerInputs() {
		inputNumPlayers.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void afterTextChanged(Editable editable) {
				generateIndividualPlayerInputs();
			}
		});
	}

	/**
	 * Update UI dynamically when number of players or combined score is changed
	 */
	private void setUpSaveButton() {
		saveButton.setOnClickListener(view -> {
			String numPlayersText = inputNumPlayers.getText().toString();

			if (!isValidInput()) {
				Toast.makeText(this, getString(R.string.saved_game_err_toast), Toast.LENGTH_SHORT).show();
				return;
			}

			numPlayers = Integer.parseInt(numPlayersText);
			ArrayList<Integer> playerScores = getPlayerScoresFromInputs();

			if (editActivity) {
				gameConfig.editGame(gameIndex, playerScores);
			} else {
				gameConfig.addGame(numPlayers, playerScores);
			}

			Toast.makeText(this, getString(R.string.saved_game_toast), Toast.LENGTH_SHORT).show();
			saveToSharedPreferences();
			finish();
		});
	}

	private void generateIndividualPlayerInputs() {
		clearAchievementText();

		TableLayout table = findViewById(R.id.tablePlayerInputs);
		table.removeAllViews();

		if (!isValidNumPlayers()) {
			return;
		}

		newInputPlayerScores = new ArrayList<>();
		int numPlayers = Integer.parseInt(inputNumPlayers.getText().toString());

		for (int i = 0; i < numPlayers; i++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT
			));
			table.addView(tableRow);

			EditText playerInput = new EditText(this);
			playerInput.setInputType(InputType.TYPE_CLASS_NUMBER);
			playerInput.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.MATCH_PARENT
			));
			playerInput.setHint(getString(R.string.player_input_hint, i + MIN_PLAYERS));
			playerInput.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}

				@Override
				public void afterTextChanged(Editable editable) {
					refreshAchievementText();
				}
			});
			tableRow.addView(playerInput);

			newInputPlayerScores.add(playerInput);
		}
	}
	private void setUpIndividualPlayerInputs() {
		clearAchievementText();

		TableLayout table = findViewById(R.id.tablePlayerInputs);
		table.removeAllViews();

		newInputPlayerScores = new ArrayList<>();
		ArrayList<Integer> currentInputPlayerScores = currentGame.getPlayerScores();
		int numPlayers = currentGame.getPlayers();

		for (int i = 0; i < numPlayers; i++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT
			));
			table.addView(tableRow);

			EditText playerInput = new EditText(this);
			playerInput.setInputType(InputType.TYPE_CLASS_NUMBER);
			playerInput.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.MATCH_PARENT
			));
			playerInput.setHint(getString(R.string.player_input_hint, i + MIN_PLAYERS));
			playerInput.setText(Integer.toString(currentInputPlayerScores.get(i)));
			playerInput.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}

				@Override
				public void afterTextChanged(Editable editable) {
					refreshAchievementText();
				}
			});
			tableRow.addView(playerInput);

			newInputPlayerScores.add(playerInput);
		}
	}
	/**
	 * Refresh the current achievement level that the users reached
	 */
	private void refreshAchievementText() {
		String numPlayersText = inputNumPlayers.getText().toString();

		if (!isValidInput()) {
			clearAchievementText();
			return;
		}

		ArrayList<Integer> playerScores = getPlayerScoresFromInputs();

		int numPlayers = Integer.parseInt(numPlayersText);
		int achievementLevel = calculateAchievementLevel(numPlayers, playerScores);
		int theme = gameConfigManager.getTheme();

		tvAchievement.setText(achievementNames[theme][achievementLevel]);
	}

	private boolean isValidNumPlayers() {
		String inputNumPlayersText = inputNumPlayers.getText().toString();
		if (!isInt(inputNumPlayersText) || Integer.parseInt(inputNumPlayersText) < MIN_PLAYERS) {
			return false;
		}

		return true;
	}

	private boolean isValidInput() {
		if (!isValidNumPlayers()) {
			return false;
		}

		for (TextView playerInput : newInputPlayerScores) {
			if (!isInt(playerInput.getText().toString())) {
				return false;
			}
		}

		return true;
	}

	private ArrayList<Integer> getPlayerScoresFromInputs() {
		if (!isValidInput()) {
			return null;
		}

		ArrayList<Integer> playerScores = new ArrayList<>();
		for (TextView playerInput : newInputPlayerScores) {
			int score = Integer.parseInt(playerInput.getText().toString());
			playerScores.add(score);
		}

		return playerScores;
	}

	/**
	 * uses the Game model to return a achivement level
	 * @return
	 */
	private int calculateAchievementLevel(int numPlayers, ArrayList<Integer> playerScores) {
		newGame = new Game(numPlayers, playerScores, gameConfig.getExpectedPoorScore(), gameConfig.getExpectedGreatScore());
		return newGame.getAchievementLevel();
	}

	private static boolean isInt(String str) {
		try {
			@SuppressWarnings("unused")
			int x = Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void clearAchievementText() {
		tvAchievement.setText(R.string.dash);
	}

	private void extractDataFromIntent() {
		Intent intent = getIntent();
		configIndex = intent.getIntExtra(EXTRA_CONFIG_INDEX, DEFAULT);
		gameIndex = intent.getIntExtra(EXTRA_GAME_INDEX, DEFAULT);

		if (gameIndex != -1) {
			editActivity = true;
		}
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