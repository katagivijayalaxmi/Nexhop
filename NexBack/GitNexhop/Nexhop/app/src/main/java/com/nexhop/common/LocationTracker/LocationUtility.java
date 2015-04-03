package com.nexhop.common.LocationTracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.EditText;

import com.nexhop.model.LocationBean;

/**
 * Created by vijayalaxmi on 16/3/15.
 */
public class LocationUtility {
    public static LocationBean GetLocCoordinates(Context ctx)
    {
        CurrentLocationTracker tracker=new CurrentLocationTracker(ctx);
        return tracker.locBean;
    }

    public static class CaptureLocationAddress extends AsyncTask<Context, Integer, String>
    {
        double latitude;
        double longitude;
        Context ctx;
        EditText tv;
        SharedPreferences preferences;

        public CaptureLocationAddress(double latitude, double longitude,
                                      Context context, EditText textview) {
            this.latitude = latitude;
            this.longitude = longitude;
            ctx = context;
            preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            tv = textview;
        }

        @Override
        protected String doInBackground(Context... params)
        {
            String location = getLocationDetails(latitude, longitude);
            return location;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            if(result==null){
                result = "";
            }
            tv.setText(result);
        }
    }


    public static String getLocationDetails(double lat,double lang)
    {
        StringBuilder str = new StringBuilder();
        try
        {
            GetReverseGeoCoding geoCode = new GetReverseGeoCoding();
            geoCode.getAddress(lat, lang);
            if(!geoCode.getAddress1().equalsIgnoreCase(""))
            {
                str.append(geoCode.getAddress1());
            }
            if(!geoCode.getAddress2().equalsIgnoreCase(""))
            {
                str.append(", ").append(geoCode.getAddress2());
            }
            if(!geoCode.getCity().equalsIgnoreCase(""))
            {
                str.append(", ").append(geoCode.getCity());

            }
            if(!geoCode.getPIN().equalsIgnoreCase(""))
            {
                str.append(", ").append(geoCode.getPIN());

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return str.toString();
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

}
