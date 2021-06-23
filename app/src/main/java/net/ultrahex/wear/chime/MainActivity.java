package net.ultrahex.wear.chime;

import android.content.*;
import android.os.*;

import androidx.appcompat.app.*;

public final class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.settingsFragment, new SettingsFragment())
				.commit();
	}

	@Override
	protected void onStart() {
		super.onStart();

		Intent chimeIntent = new Intent(this, ChimeService.class);
		chimeIntent.setAction("init");
		startForegroundService(chimeIntent);
	}

}