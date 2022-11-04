package ca.university.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddGameConfigActivity extends AppCompatActivity {
    private GameConfigManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_config);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        manager = GameConfigManager.getInstance();

        findViewById(R.id.btn_save_game_config).setOnClickListener(v -> saveGameConfig());

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
        manager.addConfig(name,poorScore,greatScore);
    }

}