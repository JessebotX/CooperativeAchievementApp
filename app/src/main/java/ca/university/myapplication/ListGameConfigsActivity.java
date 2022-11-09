package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class ListGameConfigsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_game_configs);

        setUpAddNewGameConfigButton();
        displayListOfConfigsUsingListView();
        clickConfigToLaunchGameConfigInfo();
        longClickConfigToOpenAnEditor();
    }

    @Override
    public void onResume() {
        super.onResume();
        displayListOfConfigsUsingListView();
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
                String name = config.getName();
                int poorScore = config.getExpectedPoorScore();
                int greatScore = config.getExpectedGreatScore();

                Intent intent = AddGameConfigActivity.makeIntent(ListGameConfigsActivity.this, i);
                startActivity(intent);
                return false;
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
        if(size == 0){
            Toast.makeText(this,"no configurations have been added yet",Toast.LENGTH_LONG);
            return;
        }

        String[] listOfConfigs = new String[size];
        for (int i = 0; i < size; i++) {
            GameConfig config = GameConfigManager.getInstance().getConfig(i);
            //listOfConfigs contains the line we print
            listOfConfigs[i] = config.getName() + " || " + config.getExpectedPoorScore()
                    + " || " + config.getExpectedGreatScore();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, R.layout.activity_list_game_configs, R.id.listOfConfigsListView, listOfConfigs);
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
}