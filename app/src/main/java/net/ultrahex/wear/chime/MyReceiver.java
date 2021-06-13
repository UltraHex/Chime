package net.ultrahex.wear.chime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent chimeIntent = new Intent(context, ChimeService.class);
			chimeIntent.setAction("init");
			context.startForegroundService(chimeIntent);
		}
	}
}