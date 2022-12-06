package ca.university.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfigManager;

public class MessageFragment extends AppCompatDialogFragment {
	private View v;
	private GameConfigManager manager;
	private String[][] achievementNames;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		manager = GameConfigManager.getInstance();

		//create the view to show
		v = LayoutInflater.from(getActivity())
				.inflate(R.layout.achievement_celebration,null);

		setupCloseButton();
		setupReplayButton();
		initializeAchievementNames();
		fillNextLevelPointsTextView();
		populateThemeSpinner();
		playSound();

		//build the alert dialog
		return new AlertDialog.Builder(getActivity(),R.style.dialog)
				.setView(v)
				.create();
	}

	private void saveIntToSharedPrefs(String key, int num) {
		SharedPreferences prefs = getActivity().getSharedPreferences(ListGameConfigsActivity.APP_PREFERENCES, ListGameConfigsActivity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		editor.putInt(key, num);
		editor.apply();
	}

	//setup close button
	private void setupCloseButton() {
		ImageView close_button = v.findViewById(R.id.btn_close);
		close_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().finish();
			}
		});
	}

	//setup replay button
	private void setupReplayButton() {
		ImageView replay_button = v.findViewById(R.id.btn_replay);
		replay_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getDialog().cancel();
				FragmentManager manager = getActivity().getSupportFragmentManager();
				MessageFragment dialog = new MessageFragment();
				dialog.show(manager, "Celebration!");
			}
		});
	}

	//initialize achievement names
	private void initializeAchievementNames() {
		achievementNames = new String[][]{
				getResources().getStringArray(R.array.achievement_theme_animals),
				getResources().getStringArray(R.array.achievement_theme_resources),
				getResources().getStringArray(R.array.achievement_theme_weapons)
		};
	}

	//fill text view to show next level points
	private void fillNextLevelPointsTextView() {
		Game savedGame = AddGameActivity.getNewGame();
		if (savedGame.getAchievementLevel() < 8) {
			int[] requiredScores = savedGame.getAchievementLevelRequiredScores();
			int nextLevelRequiredScore = requiredScores[savedGame.getAchievementLevel()];
			int neededPoints = nextLevelRequiredScore - savedGame.getTotalScore();

			String nextLevelName = achievementNames[manager.getTheme()][savedGame.getAchievementLevel() + 1];
			TextView tv_next_level = v.findViewById(R.id.tv_next_level_score);
			tv_next_level.setText(getString(R.string.points_for_next_achievement,
					neededPoints, nextLevelName));
		}
	}

	//theme spinner
	private void populateThemeSpinner() {
		Spinner themeSpinner = v.findViewById(R.id.spinner_theme);
		ArrayAdapter themeAdapter = ArrayAdapter.createFromResource
				(getActivity(), R.array.achievement_themes, R.layout.theme_spinner_text);
		themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		themeSpinner.setAdapter(themeAdapter);
		themeSpinner.setSelection(manager.getTheme());
		themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
				manager.setTheme(position);
				saveIntToSharedPrefs(ListGameConfigsActivity.SAVED_THEME_ID_KEY, position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
	}

	//play sound
	private void playSound() {
		final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.celebration);
		mediaPlayer.start();
	}
}
