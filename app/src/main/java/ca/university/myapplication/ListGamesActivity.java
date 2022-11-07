package ca.university.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class ListGamesActivity extends AppCompatActivity {
    private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
    private GameConfigManager manager;
    private GameConfig gameConfig;
    private List<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.list_games_toolbar);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        manager = GameConfigManager.getInstance();
//        extractGameConfigExtra();
//        displayInfo();
        gameConfig = new GameConfig("Hot Pot",1,30);
        gameConfig.addGame(2,10);
        gameConfig.addGame(3,30);
        gameConfig.addGame(2,1);
        gameConfig.addGame(5,0);
        gameConfig.addGame(4,40);
        gameConfig.addGame(4,70);
        gameList = gameConfig.getGames();
        populateListView();
    }

//    private void extractGameConfigExtra() {
//        Intent intent = getIntent();
//        int gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX,0);
//        gameConfig = manager.getConfig(gameConfigIndex);
//        gameList = gameConfig.getGames();
//    }

//    public static Intent makeIntent(Context context,int gameConfigIndex) {
//        Intent intent = new Intent(context, ListGamesActivity.class);
//        intent.putExtra(EXTRA_GAME_CONFIG_INDEX,gameConfigIndex);
//        return intent;
//    }

//    private void displayInfo() {
//        if (gameConfig.totalGames() == 0) {
//            TextView tv_msg = findViewById(R.id.tv_no_games_msg);
//            tv_msg.setText(getString(R.string.no_games_msg));
//        }
//    }

    private void populateListView() {
        ArrayAdapter<Game> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.list_games);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Game> {
        public MyListAdapter() {
            super(ListGamesActivity.this, R.layout.item_view, gameList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //get view to work with in case it's given null
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //find the game
            Game currentGame = gameList.get(position);

            //fill the view
            //image
            if (fillImage(currentGame) != -1) {
                ImageView imageView = itemView.findViewById(R.id.item_image);
                imageView.setImageResource(fillImage(currentGame));
            }

            //date
            TextView textDate = itemView.findViewById(R.id.item_date);
            LocalDateTime date = currentGame.getTimeOfCreation();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
            String formattedDate = date.format(dateFormat);
            textDate.setText(formattedDate);

            //number of players
            TextView textNumPlayers = itemView.findViewById(R.id.item_num_players);
            textNumPlayers.setText("" + currentGame.getPlayers());

            //combined score
            TextView textScore = itemView.findViewById(R.id.item_score);
            textScore.setText("" + currentGame.getTotalScore());

            //achievement level
            TextView textLevel = itemView.findViewById(R.id.item_achievement);
            setAchievementLevelText(textLevel, currentGame);

            return itemView;
        }


        //get id for the image matching with the achievement
        private int fillImage(Game game) {
            int achievement = game.getAchievementLevel();
            int imageID = R.drawable.butterfly;
//            switch (achievement) {
//                case 0:
//
//            }
            return imageID;
        }

        //set text of achievement level
        private void setAchievementLevelText(TextView textLevel, Game currentGame) {
            int level = currentGame.getAchievementLevel();
            switch (level) {
                case 0:
                    textLevel.setText(R.string.achievement_level_zero);
                    break;
                case 1:
                    textLevel.setText(R.string.achievement_level_one);
                    break;
                    case 2:
                    textLevel.setText(R.string.achievement_level_two);
                    break;
                    case 3:
                    textLevel.setText(R.string.achievement_level_three);
                    break;
                    case 4:
                    textLevel.setText(R.string.achievement_level_four);
                    break;
                    case 5:
                    textLevel.setText(R.string.achievement_level_five);
                    break;
                    case 6:
                    textLevel.setText(R.string.achievement_level_six);
                    break;
                    case 7:
                    textLevel.setText(R.string.achievement_level_seven);
                    break;
                    case 8:
                    textLevel.setText(R.string.achievement_level_eight);
                    break;
            }
        }

    }

}