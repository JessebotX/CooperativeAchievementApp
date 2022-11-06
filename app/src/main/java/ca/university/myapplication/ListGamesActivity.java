package ca.university.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class ListGamesActivity extends AppCompatActivity {
    private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
    private GameConfigManager manager;
    private GameConfig gameConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.list_games_toolbar);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        manager = GameConfigManager.getInstance();
        extractGameConfigExtra();
        displayInfo();
        populateListView();
//        findViewById(R.id.btn_save_game_config).setOnClickListener(v -> saveGameConfig());


    }

    private void extractGameConfigExtra() {
        Intent intent = getIntent();
        int gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX,0);
        gameConfig = manager.getConfig(gameConfigIndex);
    }

//    public static Intent makeIntent(Context context) {
//        Intent intent = new Intent(context, ListGamesActivity.class);
//        return intent;
//    }

    public static Intent makeIntent(Context context,int gameConfigIndex) {
        Intent intent = new Intent(context, ListGamesActivity.class);
        intent.putExtra(EXTRA_GAME_CONFIG_INDEX,gameConfigIndex);
        return intent;
    }

    private void displayInfo() {
        if (gameConfig.totalGames() == 0) {
            TextView tv_msg = findViewById(R.id.tv_no_games_msg);
            tv_msg.setText(getString(R.string.no_games_msg));
        }
    }

    private void populateListView() {

    }

}