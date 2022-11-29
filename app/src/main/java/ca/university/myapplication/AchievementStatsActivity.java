package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AchievementStatsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_histogram);
	}

	public static Intent makeIntent(Context context) {
		Intent intent = new Intent(context, AchievementStatsActivity.class);
		return intent;
	}
}