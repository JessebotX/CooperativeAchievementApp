package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ca.university.myapplication.model.GameConfigManager;

public class ListGameConfigs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_game_configs);

        setUpAddNewGameConfigButton();
        displayListOfConfigsUsingListView();
        clickConfigToLaunchGameConfigInfo();
        longClickConfigToOpenAnEditor();
    }



    //Displays info if there are no game configs added
    //Lists game configs.
    //Has a button to add a game config (launches the AddGameConfigActivity).
    //On the click of a game config it launches the GameConfigInfoActivity
    //On the long click of a game config it launches the AddGameConfigActivity with putting extras of the game config info to give the possibility of editing the game config.
    //Has a delete button next to every game config that deletes the game config and updates the list view

    private void longClickConfigToOpenAnEditor() {
        ListView listView = findViewById(R.id.listOfConfigs);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListGameConfigs.this, EditTheConfig.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private void displayListOfConfigsUsingListView() {
        /**
         * if no configs we need to display a message
         */
        ListView listView = findViewById(R.id.listOfConfigs);
        int size = GameConfigManager.getInstance().totalConfigs();

        /**
         * Name (String)
         * expected poor score (int)
         * expected great score (int)
         */
        String[] listOfConfigs = new String[size];
        for (int i = 0; i < size; i++) {
            GameConfigManager.getInstance().getConfig(i);
        }
        /**
         * ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
         *                 this,R.layout.activity_display_history,R.id.TextViewID,arr);
         *         listView.setAdapter(arrayAdapter);
         */
    }

    private void clickConfigToLaunchGameConfigInfo() {
        ListView listView = findViewById(R.id.listOfConfigs);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**
                 * Intent intent = new Intent(ListGameConfigs.this, GameConfigInfoActivity.class);
                 * startActivity(intent);
                 */
                return false;
            }
        });
    }

    private void setUpAddNewGameConfigButton() {
        FloatingActionButton addGame = findViewById(R.id.addGameConfig);
        addGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /**
                 * Intent intent = new Intent(ListGameConfigs.this, AddGameConfigActivity.class);
                 * startActivity(intent);
                 */
            }
        });
    }
}