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

		manager = GameConfigManager.getInstance();
		extractGameConfigExtra();
		displayInfo();

		populateListView();
	}

	private void extractGameConfigExtra() {
		Intent intent = getIntent();
		int gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX, 0);
		gameConfig = manager.getConfig(gameConfigIndex);
		gameList = gameConfig.getGames();
	}

	public static Intent makeIntent(Context context, int gameConfigIndex) {
		Intent intent = new Intent(context, ListGamesActivity.class);
		intent.putExtra(EXTRA_GAME_CONFIG_INDEX, gameConfigIndex);
		return intent;
	}

	private void displayInfo() {
		if (gameConfig.totalGames() == 0) {
			TextView tv_msg = findViewById(R.id.tv_no_games_msg);
			tv_msg.setText(getString(R.string.no_games_msg));
		}
	}

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
			ImageView imageView = itemView.findViewById(R.id.item_image);
			imageView.setImageResource(fillImage(currentGame));

			//date
			TextView textDate = itemView.findViewById(R.id.item_date);
			int year = currentGame.getTimeOfCreation().getYear();
			int month = currentGame.getTimeOfCreation().getMonth();
			int day = currentGame.getTimeOfCreation().getDay();
			int hour = currentGame.getTimeOfCreation().getHour();
			int minute = currentGame.getTimeOfCreation().getMinute();
			int second = currentGame.getTimeOfCreation().getSecond();
			textDate.setText(getString(R.string.date, year, month, day, hour, minute, second));

			//number of players
			TextView textNumPlayers = itemView.findViewById(R.id.item_num_players);
			textNumPlayers.setText(getString(R.string.num_players) + currentGame.getPlayers());

			//combined score
			TextView textScore = itemView.findViewById(R.id.item_score);
			textScore.setText(getString(R.string.combined_score_colon) + currentGame.getTotalScore());

			//achievement level
			TextView textLevel = itemView.findViewById(R.id.item_achievement);
			setAchievementLevelText(textLevel, currentGame);

			return itemView;
		}


		//get id for the image matching with the achievement
		private int fillImage(Game game) {
			int achievement = game.getAchievementLevel();
			int imageID;
			switch (achievement) {
				case 0:
					imageID = R.drawable.butterfly;
					break;
				case 1:
					imageID = R.drawable.bee_icon;
					break;
				case 2:
					imageID = R.drawable.chicken_icon;
					break;
				case 3:
					imageID = R.drawable.fox_icon;
					break;
				case 4:
					imageID = R.drawable.tiger_icon;
					break;
				case 5:
					imageID = R.drawable.gorilla_icon;
					break;
				case 6:
					imageID = R.drawable.rhinoceros_icon;
					break;
				case 7:
					imageID = R.drawable.whale_icon;
					break;
				case 8:
					imageID = R.drawable.dragon_icon;
					break;
				default:
					imageID = R.drawable.ic_launcher_foreground;
					break;
			}
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