package net.ultrahex.wear.chime;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;

import static android.app.AlarmManager.*;
import static android.app.NotificationManager.*;

public final class ChimeService extends Service {

	private static final String TAG = "ChimeService";

	public ChimeService() {}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate() called");

		((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(
				new NotificationChannel(
						"ChimeService",
						"Chime Service",
						NotificationManager.IMPORTANCE_LOW
				)
		);

		startForeground(
				1,
				new Notification.Builder(this, "ChimeService")
						.setLargeIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
						.setSmallIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
						.setContentTitle("Chime Service").
						setContentText("Chime!")
						.build()
		);

		scheduleNextChime();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(
				TAG, "onStartCommand() called with: " +
						"intent = [" + intent + "], " +
						"flags = [" + flags + "], " +
						"startId = [" + startId + "]"
		);

		switch (intent.getAction()) {
			case "chime":
				vibrate();
				// fallthrough
			case "init":
				scheduleNextChime();
				// fallthrough
			default:
				stopSelf();
		}

		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void scheduleNextChime() {
		/*
		 *                  ╱ t + 1 ms \
		 * nextChime = ceil| ---------- | * 15 min
		 *                  \  15 min  /
		 */
		long nextChime = (long) (
				Math.ceil((System.currentTimeMillis() + 1) / (double) INTERVAL_FIFTEEN_MINUTES) *
						INTERVAL_FIFTEEN_MINUTES);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Intent chimeIntent = new Intent(this, ChimeService.class);
		chimeIntent.setAction("chime");
		PendingIntent pendingIntent = PendingIntent.getForegroundService(this, 0, chimeIntent, 0);

		alarmManager.cancel(pendingIntent);
		alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, nextChime, pendingIntent);

		Log.d(TAG, String.format("scheduleNextChime: %1$tY %1$tB %1$te, %1$tI:%1$tM %1$tp", nextChime));
	}

	private void vibrate() {

		NotificationManager notificationManager =
				(NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

		switch (notificationManager.getCurrentInterruptionFilter()) {
			case INTERRUPTION_FILTER_ALL:
				Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
				vibrator.vibrate(VibrationEffect.createWaveform(new long[]{0, 50, 150, 50}, -1));
				// fallthrough
			case INTERRUPTION_FILTER_PRIORITY:
			case INTERRUPTION_FILTER_ALARMS:
			case INTERRUPTION_FILTER_NONE:
			case INTERRUPTION_FILTER_UNKNOWN:
			default:
		}

	}
}