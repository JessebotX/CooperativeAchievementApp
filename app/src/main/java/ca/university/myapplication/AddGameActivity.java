package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGameActivity extends AppCompatActivity {

    EditText inputNumPlayers;
    EditText inputCombinedScore;
    Button saveButton;

    int numPlayers;
    int combinedScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        inputNumPlayers = findViewById(R.id.inputNumPlayers);
        inputCombinedScore = findViewById(R.id.inputCombinedScore);
        saveButton = findViewById(R.id.btnSave);

        setUpSaveButton();
    }

    private void setUpSaveButton() {
        saveButton.setOnClickListener( view -> {
            String numPlayersText = inputNumPlayers.getText().toString();
            String combinedScoreText = inputNumPlayers.getText().toString();

            // exit early if input is not entered
            if (!isInt(numPlayersText) || !isInt(combinedScoreText)) {
                Toast.makeText(this, "Please Finish Entering Inputs.", Toast.LENGTH_SHORT).show();
                return;
            }

            numPlayers = Integer.parseInt(numPlayersText);
            combinedScore = Integer.parseInt(combinedScoreText);

            // save to game config


            Toast.makeText(this, "New Game Saved!", Toast.LENGTH_SHORT).show();

        });
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

    public static Intent make_intent(Context context){
        return new Intent(context, AddGameActivity.class);
    }
}