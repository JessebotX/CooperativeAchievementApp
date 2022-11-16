package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ca.university.myapplication.model.GameConfigManager;

/**
 * Activity that allows user to change achievement themes globally
 */
public class OptionsActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		createRadioButtons();
	}

	public static Intent makeIntent(Context c) {
		Intent i = new Intent(c, OptionsActivity.class);
		return i;
	}

	private void createRadioButtons() {
		GameConfigManager manager = GameConfigManager.getInstance();
		RadioGroup group = findViewById(R.id.radioGroupThemes);
		String[] themes = getResources().getStringArray(R.array.achievement_themes);

		for (int i = 0; i < themes.length; i++) {
			final int INDEX = i;

			RadioButton radioButton = new RadioButton(this);

			radioButton.setText(themes[INDEX]);
			radioButton.setOnClickListener(v -> {
				manager.setTheme(INDEX);
				saveIntToSharedPrefs(ListGameConfigsActivity.SAVED_THEME_ID_KEY, INDEX);
			});

			group.addView(radioButton);

			if (manager.getTheme() == INDEX) {
				radioButton.setChecked(true);
			}
		}
	}

	private void saveIntToSharedPrefs(String key, int num) {
		SharedPreferences prefs = this.getSharedPreferences(ListGameConfigsActivity.APP_PREFERENCES, ListGameConfigsActivity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		editor.putInt(key, num);
		editor.apply();
	}
}