package ca.university.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
	public static final int REQUEST_CODE = 2;
	public static final int PERMISSION_REQUEST_CODE = 1;

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
	private double difficultyModifier = Game.NORMAL_DIFFICULTY;

	private String[][] achievementNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		extractDataFromIntent();
		initializeFields();
		setupCamera();
		setupPlayerInputs();
		setUpSaveButton();

		if (editActivity) {
			setUpForEditActivity();
		} else{
			setupDifficultySelect();
		}
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

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, REQUEST_CODE);
			} else {
				Toast.makeText(this, "Must allow camera access to take photo", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CODE) {
				if (data == null || data.getExtras() == null) {
					Toast.makeText(this, "Error has occured with getting image from camera", Toast.LENGTH_SHORT).show();
				}
				Bitmap photo = (Bitmap)data.getExtras().get("data");
				ImageView photoImageView = findViewById(R.id.photoImageView);
				photoImageView.setImageBitmap(photo);
			}
		}
	}

	private void setupCamera() {
		Button buttonCamera = findViewById(R.id.buttonCamera);
		String[] requestedPermissions = new String[] { Manifest.permission.CAMERA };

		buttonCamera.setOnClickListener(e -> {
			if (ContextCompat.checkSelfPermission(AddGameActivity.this, Manifest.permission.CAMERA)
					== PackageManager.PERMISSION_GRANTED)
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, REQUEST_CODE);
			} else {
				ActivityCompat.requestPermissions(AddGameActivity.this, requestedPermissions, PERMISSION_REQUEST_CODE);
			}
		});
	}

	private void setUpForEditActivity() {
		inputNumPlayers.setText(Integer.toString(currentGame.getPlayers()));
		setUpIndividualPlayerInputs();
		setUpRadioDifficultyForEdit();
		displayAchievementOnEditActivity();
	}

	private void displayAchievementOnEditActivity() {

		int achievementLevel = currentGame.getAchievementLevel();
		int theme = gameConfigManager.getTheme();

		tvAchievement.setText(achievementNames[theme][achievementLevel]);
	}

	private void setUpRadioDifficultyForEdit() {
		RadioGroup group = findViewById(R.id.radioGroupDifficulty);
		String[] difficulties = getResources().getStringArray(R.array.difficulties);
		double[] difficultyModifiers = new double[] {
				Game.EASY_DIFFICULTY, Game.NORMAL_DIFFICULTY, Game.HARD_DIFFICULTY
		};
		double defaultDifficulty = Game.NORMAL_DIFFICULTY;
		double gameDifficulty = currentGame.getDifficultyModifier();

		if (gameDifficulty == Game.EASY_DIFFICULTY) {
			defaultDifficulty = Game.EASY_DIFFICULTY;
		}
		if (gameDifficulty == Game.HARD_DIFFICULTY) {
			defaultDifficulty = Game.HARD_DIFFICULTY;
		}

		for (int i = 0; i < difficulties.length; i++) {
			final int INDEX = i;

			RadioButton button = new RadioButton(this);

			button.setText(difficulties[i]);
			button.setOnClickListener(v -> {
				difficultyModifier = difficultyModifiers[INDEX];
				refreshAchievementText();
			});

			group.addView(button);

			if (difficultyModifiers[i] == defaultDifficulty) {
				button.setChecked(true);
			}
		}

	}

	private void initializeFields() {
		gameConfigManager = GameConfigManager.getInstance();
		gameConfig = gameConfigManager.getConfig(configIndex);
		if (editActivity) {
			currentGame = gameConfig.getGame(gameIndex);
		}


		achievementNames = new String[][] {
				getResources().getStringArray(R.array.achievement_theme_animals),
				getResources().getStringArray(R.array.achievement_theme_resources),
				getResources().getStringArray(R.array.achievement_theme_weapons)
		};

		inputNumPlayers = findViewById(R.id.inputNumPlayers);
		tvAchievement = findViewById(R.id.tvAchievement);
		saveButton = findViewById(R.id.btnSave);
	}

	private void setupDifficultySelect() {
		RadioGroup group = findViewById(R.id.radioGroupDifficulty);
		String[] difficulties = getResources().getStringArray(R.array.difficulties);
		double[] difficultyModifiers = new double[] {
				Game.EASY_DIFFICULTY, Game.NORMAL_DIFFICULTY, Game.HARD_DIFFICULTY
		};
		double defaultDifficulty = Game.NORMAL_DIFFICULTY;

		for (int i = 0; i < difficulties.length; i++) {
			final int INDEX = i;

			RadioButton button = new RadioButton(this);

			button.setText(difficulties[i]);
			button.setOnClickListener(v -> {
				difficultyModifier = difficultyModifiers[INDEX];
				refreshAchievementText();
			});

			group.addView(button);

			if (difficultyModifiers[i] == defaultDifficulty) {
				button.setChecked(true);
			}
		}
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
				gameConfig.editGame(gameIndex, playerScores, difficultyModifier);
			} else {
				assert playerScores != null;
				gameConfig.addGame(numPlayers, playerScores, difficultyModifier);
			}

			Toast.makeText(this, getString(R.string.saved_game_toast), Toast.LENGTH_SHORT).show();
			saveToSharedPreferences();
			showAchievementCelebration();
		});
	}

	/**
	 * Display the dialog for the achievement celebration
	 */
	private void showAchievementCelebration() {
		FragmentManager manager = getSupportFragmentManager();
		MessageFragment dialog = new MessageFragment();
		dialog.show(manager,"Celebration!");
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
		newGame = new Game(numPlayers, playerScores, gameConfig.getExpectedPoorScore(), gameConfig.getExpectedGreatScore(), difficultyModifier);
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