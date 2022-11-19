package ca.university.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

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

		//create a button listener
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		};

		//build the alert dialog
		return new AlertDialog.Builder(getActivity())
				.setTitle("Congratulations!")
				.setView(v)
				.setPositiveButton(android.R.string.ok,listener)
				.create();

	}
}
