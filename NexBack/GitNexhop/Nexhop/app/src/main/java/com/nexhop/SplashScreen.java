package com.nexhop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.nexhop.common.Constants;
import com.nexhop.common.Utilities;
import com.nexhop.gcm.ErrorDialogFragment;
import com.nexhop.gcm.GcmConstants;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class SplashScreen will launched at the start of the application. It will
 * be displayed for 3 seconds and than finished automatically and it will also
 * start the next activity of app.
 */
public class SplashScreen extends FragmentActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener
{
	/** Check if the app is running. */
	private boolean isRunning;

    /*Tag used for debuging*/
    String TAG=SplashScreen.class.getSimpleName();

    /* Activity Context*/
    Context context;
    GoogleCloudMessaging gcm=null;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
        context=SplashScreen.this;

        if (checkPlayServices()) {
            setUpGcm();
        }

		isRunning = true;
		startSplash();
	}

	/**
	 * Starts the count down timer for 3-seconds. It simply sleeps the thread
	 * for 3-seconds.
	 */
	private void startSplash()
	{
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(3000);
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					runOnUiThread(new Runnable() {
						@Override
						public void run()
						{
							doFinish();
						}
					});
				}
			}
		}).start();
	}

	/**
	 * If the app is still running than this method will start the Login
	 * activity and finish the Splash.
	 */
	private synchronized void doFinish()
	{

		if (isRunning)
		{
            SharedPreferences appPref = Utilities.getPrivatePreference(context);
            isRunning = false;
            if(appPref.getBoolean(Constants.USER_AUTHITICATED,false))
            {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
            else{
			    Intent i = new Intent(SplashScreen.this, Home.class);
			    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    startActivity(i);
			    finish();
            }
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			isRunning = false;
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }


    private boolean checkPlayServices() {
        // Check that Google Play services is available
        int resultCode =GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(this.getSupportFragmentManager(), "Menu");
            }
            return false;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
/*
		 * Google Play services can resolve some errors it detects.
		 * If the error has a resolution, try sending an Intent to
		 * start a Google Play services activity that can resolve
		 * error.
		 */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        GcmConstants.PLAY_SERVICES_RESOLUTION_REQUEST);

				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {

            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private void showErrorDialog(int errorCode) {
        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                this,
                GcmConstants.PLAY_SERVICES_RESOLUTION_REQUEST);
        // If Google Play services can provide an error dialog
        if (errorDialog != null) {
            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();
            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);
            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), "SplashScreen");
        }
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = Utilities.getGCMPreferences(context);
        String registrationId = prefs.getString(GcmConstants.PROPERTY_REG_ID, "");
        if (registrationId.equalsIgnoreCase("")) {
            Utilities.Information(TAG, "Registration not found.");
            prefs.edit().putBoolean(GcmConstants.GCM_SYNC, false).commit();
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(GcmConstants.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Utilities.Information(TAG, "App version changed.");
            prefs.edit().putBoolean(GcmConstants.GCM_SYNC, false).commit();
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void setUpGcm()
    {
        gcm = GoogleCloudMessaging.getInstance(this);
        final SharedPreferences prefs = Utilities.getGCMPreferences(context);
        prefs.getString(GcmConstants.PROPERTY_REG_ID, "");
        String regid = getRegistrationId(context);
        Utilities.Debug(TAG, regid);
        if (regid.equalsIgnoreCase("")) {
            registerInBackground();
        }else {
            if(!prefs.getBoolean(GcmConstants.GCM_SYNC, false)) //to void multiple request to server
            {
            }
        }
    }



    public void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                final SharedPreferences prefs = Utilities.getGCMPreferences(context);
                      SharedPreferences appprefs= Utilities.getPrivatePreference(context);
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    String regid = gcm.register(GcmConstants.SENDER_ID);
                    storeRegistrationId(context, regid);

                    Utilities.Information("GCM", " Device registered, registration ID=" + regid);
                    if(!prefs.getBoolean(GcmConstants.GCM_SYNC, false))
                    {
                        List<NameValuePair> gcmlist=new ArrayList<NameValuePair>();
                        gcmlist.add(new BasicNameValuePair("key1",appprefs.getString(Constants.USERID,"")));
                        gcmlist.add(new BasicNameValuePair("key2",prefs.getString(GcmConstants.PROPERTY_REG_ID,"")));

                    }
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
            @Override
            protected void onPostExecute(String msg) {

            }
        }.execute(null, null, null);
    };

private void storeRegistrationId(Context context, String regId) {
final SharedPreferences prefs = Utilities.getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GcmConstants.PROPERTY_REG_ID, regId);
        editor.putInt(GcmConstants.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
        }
}