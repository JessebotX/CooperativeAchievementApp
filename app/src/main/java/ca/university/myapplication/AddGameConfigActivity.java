package ca.university.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

/**
 * Activity that allows user to add or edit or delete a game configuration.
 */
public class AddGameConfigActivity extends AppCompatActivity {
	private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
	private GameConfigManager manager;
	private GameConfig gameConfig;
	boolean isInAddMode;
	private int gameConfigIndex;
	boolean hasDeletedGame = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game_config);

		androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		manager = GameConfigManager.getInstance();

		extractGameConfigExtra();
		fillInfoForEdit();
		if (isInAddMode) {
			findViewById(R.id.btn_delete_config).setVisibility(View.GONE);
		} else {
			findViewById(R.id.btn_delete_config).setOnClickListener(v -> deleteGameConfig());
		}
		findViewById(R.id.btn_save_game_config).setOnClickListener(v -> saveGameConfig());
	}

	private void extractGameConfigExtra() {
		Intent intent = getIntent();
		gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX,0);
		if (gameConfigIndex == -1) {
			isInAddMode = true;
			return;
		}
		gameConfig = manager.getConfig(gameConfigIndex);
	}

	public static Intent makeIntent(Context context,int gameConfigIndex) {
		Intent intent = new Intent(context, AddGameConfigActivity.class);
		intent.putExtra(EXTRA_GAME_CONFIG_INDEX,gameConfigIndex);
		return intent;
	}

	private void fillInfoForEdit() {
		if(!isInAddMode) {
			EditText etName = findViewById(R.id.et_name_game_config);
			etName.setText(gameConfig.getName());

			EditText etPoorScore = findViewById(R.id.et_poor_score_game_config);
			etPoorScore.setText("" + gameConfig.getExpectedPoorScore());

			EditText etGreatScore = findViewById(R.id.et_great_score_game_config);
			etGreatScore.setText("" + gameConfig.getExpectedGreatScore());
		}
	}

	private void saveGameConfig() {

		//get inputs
		EditText etName = findViewById(R.id.et_name_game_config);
		String name = etName.getText().toString();

		EditText etPoorScore = findViewById(R.id.et_poor_score_game_config);
		String poorScoreStr = etPoorScore.getText().toString();
		int poorScore;
		try {
			poorScore = Integer.parseInt(poorScoreStr);
		} catch (NumberFormatException ex) {
			Toast.makeText(this, R.string.invalid_score_toast_msg,Toast.LENGTH_SHORT).show();
			return;
		}

		EditText etGreatScore = findViewById(R.id.et_great_score_game_config);
		String greatScoreStr = etGreatScore.getText().toString();
		int greatScore;
		try {
			greatScore = Integer.parseInt(greatScoreStr);
		} catch (NumberFormatException ex) {
			Toast.makeText(this, R.string.invalid_score_toast_msg,Toast.LENGTH_SHORT).show();
			return;
		}

		//validate
		if (name.length() == 0) {
			Toast.makeText(this, R.string.invalid_name_toast_msg,Toast.LENGTH_SHORT).show();
			return;
		}

		//save
		if (isInAddMode) {
			manager.addConfig(name, poorScore, greatScore);
		} else if (!isInAddMode && !hasDeletedGame) {
			gameConfig.setName(name);
			gameConfig.setExpectedPoorScore(poorScore);
			gameConfig.setExpectedGreatScore(greatScore);
		}
	}

	private void deleteGameConfig() {
		if (hasDeletedGame) {
			return;
		}
		manager.removeConfig(gameConfigIndex);
		EditText etName = findViewById(R.id.et_name_game_config);
		etName.setText("");

		EditText etPoorScore = findViewById(R.id.et_poor_score_game_config);
		etPoorScore.setText("");

		EditText etGreatScore = findViewById(R.id.et_great_score_game_config);
		etGreatScore.setText("");
		hasDeletedGame = true;
	}

}
