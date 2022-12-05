package ca.university.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
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
	private GameConfigManager manager;
	private Game savedGame;
	private int[] requiredScores;
	private String[][] achievementNames;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		manager = GameConfigManager.getInstance();

		//create the view to show
		View v = LayoutInflater.from(getActivity())
				.inflate(R.layout.achievement_celebration,null);

		//setup close button
		ImageView close_button = v.findViewById(R.id.btn_close);
		close_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().finish();
			}
		});

		//setup replay button
		ImageView replay_button = v.findViewById(R.id.btn_replay);
		replay_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getDialog().cancel();
				FragmentManager manager = getActivity().getSupportFragmentManager();
				MessageFragment dialog = new MessageFragment();
				dialog.show(manager,"Celebration!");
			}
		});

		//initialize achievement names
		achievementNames = new String[][] {
				getResources().getStringArray(R.array.achievement_theme_animals),
				getResources().getStringArray(R.array.achievement_theme_resources),
				getResources().getStringArray(R.array.achievement_theme_weapons)
		};

		//fill text view to show next level points
		savedGame = AddGameActivity.getNewGame();
		requiredScores = savedGame.getAchievementLevelRequiredScores();
		int nextLevelRequiredScore = requiredScores[savedGame.getAchievementLevel()];
		int neededPoints = nextLevelRequiredScore - savedGame.getTotalScore();

		String nextLevelName = achievementNames[manager.getTheme()][savedGame.getAchievementLevel()+1];
		TextView tv_next_level = v.findViewById(R.id.tv_next_level_score);
		tv_next_level.setText(getString(R.string.points_for_next_achievement,
				neededPoints,nextLevelName));

		//theme spinner
		Spinner themeSpinner = v.findViewById(R.id.spinner_theme);
		ArrayAdapter themeAdapter = ArrayAdapter.createFromResource
				(getActivity(), R.array.achievement_themes, R.layout.theme_spinner_text);
		themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		themeSpinner.setAdapter(themeAdapter);
//		themeSpinner.setSelection(lastSizeClick);
//		themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//				editor.putInt("lastSizeClick",position).commit();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> adapterView) {
//
//			}
//		});

		//play sound
		final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),R.raw.celebration);
		mediaPlayer.start();

		//build the alert dialog
		return new AlertDialog.Builder(getActivity(),R.style.dialog)
				.setView(v)
				.create();
	}
}
