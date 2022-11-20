package ca.university.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

/**
 * Activity that lists the existing games in a specific game configuration.
 */
public class ListGamesActivity extends AppCompatActivity {
	private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
	private GameConfigManager manager;
	private GameConfig gameConfig;
	private List<Game> gameList;
	private int gameConfigIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_games);

		getSupportActionBar().setTitle("List Games Activity");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		manager = GameConfigManager.getInstance();
		extractGameConfigExtra();
		displayInfo();

		populateListView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateListView();
	}

	private void extractGameConfigExtra() {
		Intent intent = getIntent();
		gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX, 0);
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
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = EditGameActivity.makeIntent(ListGamesActivity.this, gameConfigIndex, position);
				startActivity(intent);
			}
		});
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
		// TODO (for a new issue) refactor this to support different themes
		// themes[0] = animals, themes[1] = resources/minerals, themes[2] = weapons
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
			String[][] themes = new String[][] {
					getResources().getStringArray(R.array.achievement_theme_animals),
					getResources().getStringArray(R.array.achievement_theme_resources),
					getResources().getStringArray(R.array.achievement_theme_weapons)
			};
			int theme = manager.getTheme();

			int level = currentGame.getAchievementLevel();
			textLevel.setText(themes[theme][level]);
		}
	}
}
