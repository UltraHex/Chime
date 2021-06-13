package net.ultrahex.wear.chime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public final class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();

		Intent chimeIntent = new Intent(this, ChimeService.class);
		chimeIntent.setAction("init");
		startForegroundService(chimeIntent);
	}

}