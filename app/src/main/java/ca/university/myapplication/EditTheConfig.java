package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.university.myapplication.model.GameConfigManager;

public class EditTheConfig extends AppCompatActivity {

    EditText editName = findViewById(R.id.editGameName);
    EditText editPoorScore = findViewById(R.id.editPoorScore);
    EditText editGreatScore = findViewById(R.id.editGreatScore);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_the_config);

        editAttributes();
    }

    private void editAttributes() {
        Button save = findViewById(R.id.saveBtn);

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String newName = editName.getText().toString();
                String newPoorScore = editPoorScore.getText().toString();
                String newGreatScore = editGreatScore.getText().toString();
                //create new config and replace selected one with new one
                Intent intend = getIntent();
                int i = getIntent().getIntExtra("configID", 0);
                GameConfigManager.getInstance().setConfig(i, newName, newPoorScore, newGreatScore);
                finish();
            }
        });
    }
}