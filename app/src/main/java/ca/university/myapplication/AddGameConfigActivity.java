package ca.university.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

/**
 * Activity that allows user to add or edit or delete a game configuration.
 */
public class AddGameConfigActivity extends AppCompatActivity {
	private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
	private static final int CAMERA_REQUEST_CODE = 2;
	private static final int COMPRESSION_QUALITY = 100;
	private static final String EMPTY_STRING = "";

	private GameConfigManager manager;
	private GameConfig gameConfig;
	private boolean isInAddMode;
	private int gameConfigIndex;
	private boolean hasDeletedGame = false;
	private String photoBase64 = "";

	private Button uploadBtn;
	private ImageView configImage;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game_config);
		manager = GameConfigManager.getInstance();
		configImage = findViewById(R.id.gameConfigImage);
		uploadBtn = findViewById(R.id.uploadImageBtn);
		androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

		extractGameConfigExtra();
		setSupportActionBar(toolbar);
		setupInitialImageView();
		setupCamera();

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		fillInfoForEdit();
		if (isInAddMode) {
			findViewById(R.id.btn_delete_config).setVisibility(View.GONE);
		} else {
			findViewById(R.id.btn_delete_config).setOnClickListener(v -> deleteGameConfig());
		}
		findViewById(R.id.btn_save_game_config).setOnClickListener(v -> saveGameConfig());
	}

	private void setupInitialImageView() {
		if (!isInAddMode) {
			photoBase64 = gameConfig.getPhotoAsBase64();
		}

		if (photoBase64 == null || photoBase64.trim().isEmpty()) {
			Drawable defaultPhoto = getResources().getDrawable(R.drawable.ic_launcher_foreground);
			configImage.setImageDrawable(defaultPhoto);
		} else {
			Bitmap bitmap = base64ToBitmap(photoBase64);
			configImage.setImageBitmap(bitmap);
		}
	}

	public static Intent makeIntent(Context context,int gameConfigIndex) {
		Intent intent = new Intent(context, AddGameConfigActivity.class);
		intent.putExtra(EXTRA_GAME_CONFIG_INDEX,gameConfigIndex);
		return intent;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMERA_REQUEST_CODE);
			} else {
				Toast.makeText(this, "Must allow camera access to take photo", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == CAMERA_REQUEST_CODE) {
				if (data == null || data.getExtras() == null) {
					Toast.makeText(this, "Error has occured with getting image from camera", Toast.LENGTH_SHORT).show();
				}
				Bitmap photo = (Bitmap)data.getExtras().get("data");
				configImage = findViewById(R.id.gameConfigImage);

				photoBase64 = bitmapToBase64(photo);
				Bitmap photoBitmap = base64ToBitmap(photoBase64);

				configImage.setImageBitmap(photoBitmap);
			}
		}
	}

	private String bitmapToBase64(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY, outputStream);
		return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
	}

	private Bitmap base64ToBitmap(String base64) {
		byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	}

	private void setupCamera() {
		//Button buttonCamera = findViewById(R.id.buttonCamera);
		uploadBtn = findViewById(R.id.uploadImageBtn);
		String[] requestedPermissions = new String[] { Manifest.permission.CAMERA };

		uploadBtn.setOnClickListener(e -> {
			if (ContextCompat.checkSelfPermission(AddGameConfigActivity.this, Manifest.permission.CAMERA)
					== PackageManager.PERMISSION_GRANTED)
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMERA_REQUEST_CODE);
			} else {
				ActivityCompat.requestPermissions(AddGameConfigActivity.this, requestedPermissions, CAMERA_PERMISSION_REQUEST_CODE);
			}
		});
	}
	//4-----------------------------------------------------------
	private void extractGameConfigExtra() {
		Intent intent = getIntent();
		gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX,0);
		if (gameConfigIndex == -1) {
			isInAddMode = true;
			return;
		}
		gameConfig = manager.getConfig(gameConfigIndex);
	}

	private void fillInfoForEdit() {
		if(!isInAddMode) {
			EditText etName = findViewById(R.id.et_name_game_config);
			etName.setText(gameConfig.getName());

			EditText etPoorScore = findViewById(R.id.et_poor_score_game_config);
			etPoorScore.setText(EMPTY_STRING + gameConfig.getExpectedPoorScore());

			EditText etGreatScore = findViewById(R.id.et_great_score_game_config);
			etGreatScore.setText(EMPTY_STRING + gameConfig.getExpectedGreatScore());
		}
	}

	private void saveGameConfig() {
		if (hasDeletedGame) {
			return;
		}

		//get inputs
		EditText etName = findViewById(R.id.et_name_game_config);
		String name = etName.getText().toString();

		EditText etPoorScore = findViewById(R.id.et_poor_score_game_config);
		String poorScoreStr = etPoorScore.getText().toString();
		int poorScore;
		try {
			poorScore = Integer.parseInt(poorScoreStr);
		} catch (NumberFormatException ex) {
			Toast.makeText(this, R.string.invalid_score_toast_msg,Toast.LENGTH_SHORT).show();
			return;
		}

		EditText etGreatScore = findViewById(R.id.et_great_score_game_config);
		String greatScoreStr = etGreatScore.getText().toString();
		int greatScore;
		try {
			greatScore = Integer.parseInt(greatScoreStr);
		} catch (NumberFormatException ex) {
			Toast.makeText(this, R.string.invalid_score_toast_msg,Toast.LENGTH_SHORT).show();
			return;
		}

		//validate
		if (name.length() == 0) {
			Toast.makeText(this, R.string.invalid_name_toast_msg,Toast.LENGTH_SHORT).show();
			return;
		}

		//save
		if (isInAddMode) {
			manager.addConfig(name, poorScore, greatScore, photoBase64);
		} else if (!isInAddMode) {
			gameConfig.setName(name);
			gameConfig.setExpectedPoorScore(poorScore);
			gameConfig.setExpectedGreatScore(greatScore);
			gameConfig.setPhotoAsBase64(photoBase64);
		}

		saveToSharedPreferences();
		finish();
	}

	private void deleteGameConfig() {
		if (hasDeletedGame) {
			return;
		}
		manager.removeConfig(gameConfigIndex);
		hasDeletedGame = true;

		saveToSharedPreferences();
		finish();
	}

	private void saveToSharedPreferences() {
		SharedPreferences prefs = getSharedPreferences(ListGameConfigsActivity.APP_PREFERENCES, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		Gson gson = new Gson();

		String json = gson.toJson(manager.getGameConfigs());
		editor.putString(ListGameConfigsActivity.SAVED_GAME_CONFIGS_KEY, json);
		editor.apply();
	}
}