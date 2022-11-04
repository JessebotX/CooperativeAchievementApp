package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListGameConfigs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_game_configs);

        setUpAddNewGameConfigButton();
    }

    private void setUpAddNewGameConfigButton() {
        FloatingActionButton addGame = findViewById(R.id.addGameConfig);

        addGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListGameConfigs.this, AddGameConfigActivity.class);
            }
        });
    }
}