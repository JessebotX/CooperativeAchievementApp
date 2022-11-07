package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class AddGameActivity extends AppCompatActivity {

    private static final String EXTRA_GAME_INDEX = "EXTRA_GAME_INDEX";
    private static final String TAG = "ADD_GAME_ACTIVITY";
    GameConfig gameConfig;

    GameConfigManager gameConfigManager;

    Game newGame;

    EditText inputNumPlayers;
    EditText inputCombinedScore;

    TextView tvAchievement;

    Button saveButton;


    int numPlayers;
    int combinedScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        inputNumPlayers = findViewById(R.id.inputNumPlayers);
        inputCombinedScore = findViewById(R.id.inputCombinedScore);
        tvAchievement = findViewById(R.id.tvAchievement);
        saveButton = findViewById(R.id.btnSave);

        gameConfigManager = GameConfigManager.getInstance();
        // add fake data
        gameConfigManager.addConfig("Poker", 10, 100);

        // get Game index from caller
        int gameIndex = extractDataFromIntent();
        gameConfig = gameConfigManager.getConfig(gameIndex);


        setUpSaveButton();
        setUpInputListeners();
    }

    /**
     * For each input box, we want to add a listener to update and recalculate the achievement level
     * everytime the user enters something.
     */
    private void setUpInputListeners() {

        EditText[] userInputs = {inputNumPlayers, inputCombinedScore};

        for (EditText userInput : userInputs) {
            userInput.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Toast.makeText(AddGameActivity.this, "Detected num players", Toast.LENGTH_SHORT).show();
                    refreshAchievementText();
                }
            });
        }
    }

    private void setUpSaveButton() {
        saveButton.setOnClickListener( view -> {

            String numPlayersText = inputNumPlayers.getText().toString();
            String combinedScoreText = inputNumPlayers.getText().toString();

            // exit early if input is not entered
            if ( !isInt(numPlayersText) || !isInt(combinedScoreText) || Integer.parseInt(numPlayersText) < 1) {
                Toast.makeText(this, "Please Finish Entering Inputs.", Toast.LENGTH_SHORT).show();
                return;
            }

            numPlayers = Integer.parseInt(numPlayersText);
            combinedScore = Integer.parseInt(combinedScoreText);

            // add to config

            gameConfig.addGame(numPlayers, combinedScore);

            Toast.makeText(this, "New Game Saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

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
        tvAchievement.setText(Integer.toString(achievementLevel));
    }

    private int calculateAchievementLevel(int numPlayers, int combinedScore) {
        newGame = new Game(numPlayers, combinedScore, gameConfig.getExpectedPoorScore(), gameConfig.getExpectedGreatScore());
        return newGame.getAchievementLevel();
    }

    public static boolean isInt(String str) {
        try {
            @SuppressWarnings("unused")
            int x = Integer.parseInt(str);
            return true; //String is an Integer
        } catch (NumberFormatException e) {
            return false; //String is not an Integer
        }

    }

    public static Intent makeIntent(Context context, int gameIndex){
        Intent intent = new Intent(context, AddGameActivity.class);
        intent.putExtra(EXTRA_GAME_INDEX, gameIndex);
        return intent;
    }

    private int extractDataFromIntent() {
        Intent intent = getIntent();
        return intent.getIntExtra(EXTRA_GAME_INDEX, -1);
    }
}