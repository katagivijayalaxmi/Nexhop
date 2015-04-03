package com.nexhop.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.nexhop.Login;
import com.nexhop.MainActivity;
import com.nexhop.common.Connectivity;
import com.nexhop.common.Constants;
import com.nexhop.common.LocationTracker.CurrentLocationTracker;
import com.nexhop.common.LocationTracker.LocationUtility;
import com.nexhop.common.MyProgress;
import com.nexhop.common.RestUrl;
import com.nexhop.common.Utilities;
import com.nexhop.gcm.GcmConstants;
import com.nexhop.model.LocationBean;
import com.nexhop.model.RegisterBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vijayalaxmi on 18/3/15.
 */
public class RegisterUser extends AsyncTask <Context, Integer, String> {
    SharedPreferences appPref=null;
    SharedPreferences gcmPref=null;
    List<NameValuePair> registrationList = null;
    RestUrl restUrl = new RestUrl();
    RegisterBean regBean=null;
    Context context=null;
    String TAG =RegisterUser.class.getSimpleName();

    public RegisterUser(Context context,RegisterBean regBean)
    {
      appPref = Utilities.getPrivatePreference(context);
      gcmPref = Utilities.getGCMPreferences(context);
      this.regBean=regBean;
      this.context=context;
    }

    @Override
    protected String doInBackground(Context... params) {
        CurrentLocationTracker tracker = new CurrentLocationTracker(context);
        LocationBean lbean = tracker.getLatLong();
        registrationList = new ArrayList<NameValuePair>();
        registrationList.add(new BasicNameValuePair("fName", regBean.getName()));
//        registrationList.add(new BasicNameValuePair("lName", regBean.getlName()));
        registrationList.add(new BasicNameValuePair("email", regBean.getEmailId()));
        registrationList.add(new BasicNameValuePair("password", regBean.getPassword()));
        registrationList.add(new BasicNameValuePair("phoneNo", regBean.getPhoneNo()));
        registrationList.add(new BasicNameValuePair("lat", String.valueOf(lbean.getLatitude())));
        registrationList.add(new BasicNameValuePair("long", String.valueOf(lbean.getLongitude())));
        registrationList.add(new BasicNameValuePair("area", LocationUtility.getLocationDetails(lbean.getLatitude(),lbean.getLongitude())));
        registrationList.add(new BasicNameValuePair("deviceToken", gcmPref.getString(GcmConstants.PROPERTY_REG_ID, "")));
        registrationList.add(new BasicNameValuePair("osVersion", Utilities.osVersion()));
        registrationList.add(new BasicNameValuePair("deviceType", Constants.DEVICE_TYPE));
        String regResult = null;
        if (Connectivity.checkNetwork(context)) {

            regResult="{\"status\":1,\"msg\":\"Registration Successful\"}";
//            regResult="{\"status\":0,\"msg\":\"User already Registeredl\"}";
            //http://www.nexhop.com/feeds/registration
//          regResult = restUrl.queryRestUrl(context,"registration", Constants.POST, registrationList);

        } else {
            regResult = Constants.NETWORK_NOTFOUND;
        }
        return regResult;
    }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyProgress.show(context, "", "");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            appPref.edit().putBoolean(Constants.USER_REGISTERED,false).commit();
            MyProgress.CancelDialog();
            try {
                if (result == null || Constants.EMPTY_STRING.equals(result)) {
                    Utilities.displayToast(Constants.SERVERERROR, context, Constants.LONG_TOAST);
                    return;
                }
                if (result.equalsIgnoreCase(Constants.NETWORK_NOTFOUND)) {
                    Utilities.displayToast(Constants.POORCONNECTIVITY,context,Constants.LONG_TOAST);
                    return;
                }
                String[] res = result.split("delimiter_");
                if (!res[0].equals("500")) {
                    JSONObject regResult = new JSONObject(result);
                    Utilities.displayToast(regResult.getString("msg"),context,Constants.LONG_TOAST);
                    if(regResult.getInt("status")==1)
                    {
                        if(((Activity)context).getClass().getSimpleName().equalsIgnoreCase("login")){
                            appPref.edit()
                                    .putBoolean(Constants.USER_REGISTERED,true)
                                    .putBoolean(Constants.USER_AUTHITICATED,true).commit();
                            Intent i = new Intent(context, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context. startActivity(i);
                            ((Activity)context).finish();
                            return;
                        }else {
                            appPref.edit().putBoolean(Constants.USER_REGISTERED,true).commit();
                            Intent i = new Intent(context, Login.class);
                            i.putExtra(Constants.USERNAME, regBean.getEmailId());
                            context.startActivity(i);
                            ((Activity) context).finish();
                            return;
                        }

                    }

                } else {
                    Utilities.displayToast(res[1], context, Constants.LONG_TOAST);
                    return;
                }
            }catch (JSONException je)
            {
                je.printStackTrace();
                Utilities.displayToast(Constants.TECHNICALERROR,context,Constants.LONG_TOAST);
            }
            catch (Exception e) {
                e.printStackTrace();
                Utilities.displayToast(Constants.TECHNICALERROR,context,Constants.LONG_TOAST);
            }
        }
    }


