package ca.university.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MessageFragment extends AppCompatDialogFragment {
//	Animation tv_animation;
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		//create the view to show
		View v = LayoutInflater.from(getActivity())
				.inflate(R.layout.achievement_celebration,null);

		//play sound
		final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),R.raw.celebration);
		mediaPlayer.start();

		//animation
//		tv_animation = AnimationUtils.loadAnimation(getActivity(),R.anim.celebration_tv_animation);
//		TextView tv_celebration = getActivity().findViewById(R.id.tv_celebration);
//		tv_celebration.setAnimation(tv_animation);

		//create a button listener
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				getActivity().finish();
			}
		};

		//build the alert dialog
		return new AlertDialog.Builder(getActivity())
//				.setTitle("Congratulations!")
				.setView(v)
				.setPositiveButton(android.R.string.ok,listener)
				.create();

	}
}
