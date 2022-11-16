package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

	}
}