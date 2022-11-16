package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class ListGameConfigsActivity extends AppCompatActivity {
	public static final String APP_PREFERENCES = "ca.university.myapplication appPrefs";
	public static final String SAVED_GAME_CONFIGS_KEY = "ca.university.myapplication savedList";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_game_configs);

		getFromSharedPreferences();
		setUpAddNewGameConfigButton();
		displayListOfConfigsUsingListView();
		clickConfigToLaunchGameConfigInfo();
		longClickConfigToOpenAnEditor();
		setUpSingleClick();
		setupOptionsButton();
	}

	@Override
	public void onResume() {
		super.onResume();
		displayListOfConfigsUsingListView();
	}

	private void getFromSharedPreferences() {
		Gson gson = new Gson();
		SharedPreferences prefs = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
		GameConfigManager manager = GameConfigManager.getInstance();
		String json = prefs.getString(SAVED_GAME_CONFIGS_KEY, null);
		Type type = new TypeToken<ArrayList<GameConfig>>() {}.getType();

        List<GameConfig> gameConfigs = gson.fromJson(json, type);

        if (gameConfigs != null) {
            manager.setGameConfigs(gameConfigs);
        }
	}

	// [CHECK] Displays info if there are no game configs added
	// [CHECK] Lists game configs.
	// [CHECK] Has a button to add a game config (launches the AddGameConfigActivity).
	// [CHECK] On the click of a game config it launches the GameConfigInfoActivity
	// [CHECK] On the long click of a game config it launches the AddGameConfigActivity with putting extras of the game config info to give the possibility of editing the game config.
	// [HALT] Has a delete button next to every game config that deletes the game config and updates the list view

	private void longClickConfigToOpenAnEditor() {

		ListView listView = findViewById(R.id.listOfConfigsListView);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				GameConfig config = GameConfigManager.getInstance().getConfig(i);

				Intent intent = AddGameConfigActivity.makeIntent(ListGameConfigsActivity.this, i);
				startActivity(intent);
				return false;
			}
		});
	}

	/**
	 * Open Add Game Config Activity
	 */
	private void setUpSingleClick() {

		ListView listView = findViewById(R.id.listOfConfigsListView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				GameConfig config = GameConfigManager.getInstance().getConfig(i);

				Intent intent = GameConfigInfoActivity.makeIntent(ListGameConfigsActivity.this, i);
				startActivity(intent);
			}
		});
	}

	private void displayListOfConfigsUsingListView() {
		/**
		 * if no configs we need to display a message
		 * Name (String)
		 * expected poor score (int)
		 * expected great score (int)
		 */
		ListView listView = findViewById(R.id.listOfConfigsListView);
		int size = GameConfigManager.getInstance().totalConfigs();

		//display message if no configs are added
		TextView tvEmpty = findViewById(R.id.emptyStateString);
		if(size == 0){
			tvEmpty.setText("Add a Game Config by Clicking Add button.");
		} else {
			tvEmpty.setText("");
		}

		String[] listOfConfigs = new String[size];
		for (int i = 0; i < size; i++) {
			GameConfig config = GameConfigManager.getInstance().getConfig(i);
			//listOfConfigs contains the line we print
			listOfConfigs[i] = "Game Name: " + config.getName();
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this,
				R.layout.game_config_item,
				listOfConfigs
		);
		listView.setAdapter(arrayAdapter);
	}

	private void clickConfigToLaunchGameConfigInfo() {
		ListView listView = findViewById(R.id.listOfConfigsListView);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

				Intent intent = GameConfigInfoActivity.makeIntent(ListGameConfigsActivity.this, i);
				startActivity(intent);
				return false;
			}
		});
	}

	private void setUpAddNewGameConfigButton() {
		FloatingActionButton addGame = findViewById(R.id.addGameConfig);
		addGame.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				Intent intent = AddGameConfigActivity.makeIntent(ListGameConfigsActivity.this,-1);
				startActivity(intent);

			}
		});
	}

	private void setupOptionsButton() {
		Button btn = findViewById(R.id.button_options);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = OptionsActivity.makeIntent(ListGameConfigsActivity.this);
				startActivity(intent);
			}
		});
	}
}