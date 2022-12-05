package ca.university.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

public class MessageFragment extends AppCompatDialogFragment {
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
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

		//text view to show next level points
		TextView tv_next_level = v.findViewById(R.id.tv_next_level_score);
		tv_next_level.setText(getString(R.string.points_for_next_achievement,7,"hi"));

		//play sound
		final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),R.raw.celebration);
		mediaPlayer.start();

		//build the alert dialog
		return new AlertDialog.Builder(getActivity(),R.style.dialog)
				.setView(v)
				.create();
	}
}
