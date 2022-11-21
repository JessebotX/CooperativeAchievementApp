package ca.university.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

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

		//play sound
		final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),R.raw.celebration);
		mediaPlayer.start();

		//build the alert dialog
		return new AlertDialog.Builder(getActivity(),R.style.dialog)
				.setView(v)
				.create();
	}
}
