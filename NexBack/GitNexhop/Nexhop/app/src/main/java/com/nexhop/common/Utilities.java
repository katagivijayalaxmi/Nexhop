package com.nexhop.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by vijayalaxmi on 16/3/15.
 */
public class Utilities {
    public static void displayToast(final String string, final Context context,int toastType)
    {
        try
        {
            if( context != null && null != string  && !"".equals(string))
            {
                switch(toastType)
                {
                    case 1:
                        final Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 500);
                    break;
                    case 2:
                        ((Activity) context).runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast t = Toast.makeText(context, string, Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER, 0, 0);
                                t.show();
                            }
                        });
                        break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void Debug(String tag,String string) {

        if(null!=string && null !=tag)
        {
            Log.d("Nexhop",tag+string);
        }
    }

    public static void Verbose(String tag,String string) {

        if(null!=string && null !=tag)
        {
            Log.v("Nexhop",tag+string);
        }
    }

    public static void Error(String tag,String string) {

        if(null!=string && null !=tag)
        {
            Log.e("Nexhop",tag+string);
        }
    }

    public static void Information(String tag,String string) {

        if(null!=string && null !=tag)
        {
            Log.i("Nexhop",tag+string);
        }
    }

    public static SharedPreferences getPrivatePreference(Context ctx) {
         SharedPreferences preferences=ctx.getSharedPreferences(Constants.NEXHOP_PREFERENCE,Context.MODE_PRIVATE);
         return preferences;
    }

    public static SharedPreferences getGCMPreferences(Context ctx) {
        // This sample app persists the registration ID in shared preferences.
        SharedPreferences preferences=ctx.getSharedPreferences(Constants.NEXHOP_GCM_PREFERENCE,Context.MODE_PRIVATE);
        return preferences;
    }


    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }



    /* \\p{L}:  is a Unicode Character Property that matches any kind of letter from any language
    * ex:Patrick O'Brian ,Peter Müller */
    public static boolean isVaildName(String fname)
    {
        String nPattern = "^[\\p{L} .'-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(nPattern);
        java.util.regex.Matcher m = p.matcher(fname);
        return m.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static  boolean ValidateEmptyData(EditText edt,String message)
    {
        if(edt.getText().toString().trim().equalsIgnoreCase(""))
        {
            edt.requestFocus();
            edt.setError(message);
            return true;
        }
        else
        {
            edt.setError(null);
            return false;
        }
    }


    public static void ValidateData(EditText edt,String message)
    {
        edt.requestFocus();
        edt.setError(message);

    }
    public static String osVersion()
    {
        return Build.VERSION.RELEASE;


    }
}
