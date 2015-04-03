package com.nexhop.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.nexhop.MainActivity;
import com.nexhop.R;

public class GcmMessageHandler extends IntentService
{
	String mes;
	public static final int NOTIFICATION_ID = 1;
	NotificationCompat.Builder builder;
    private NotificationManager mNotificationManager;
	public GcmMessageHandler()
	{
		super("GcmMessageHandler");
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
	}
	@Override
	protected void onHandleIntent(Intent intent)
	{
		if(intent!=null && intent.getExtras()!=null)
		{
			Bundle extras = intent.getExtras();
			GoogleCloudMessaging.getInstance(this);
			Log.i("GCM", "Received url:"+extras.getString("title"));
			Log.i("GCM", "Received message:"+extras.getString("message"));
			Log.i("GCM", "Received :"+extras.toString());
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("GCM Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

