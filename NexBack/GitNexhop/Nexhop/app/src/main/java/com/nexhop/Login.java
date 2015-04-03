package com.nexhop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.nexhop.asynctasks.RegisterUser;
import com.nexhop.common.Connectivity;
import com.nexhop.common.Constants;
import com.nexhop.common.LocationTracker.CurrentLocationTracker;
import com.nexhop.common.MyProgress;
import com.nexhop.common.RestUrl;
import com.nexhop.common.Utilities;
import com.nexhop.custom.CustomActivity;
import com.nexhop.gcm.GcmConstants;
import com.nexhop.model.LocationBean;
import com.nexhop.model.RegisterBean;
import com.nexhop.utils.CustomTextWatcher;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Activity Login is launched after the Home screen. You need to write your
 * logic for actual Login. You also need to implement Facebook Login if
 * required.
 */
public class Login extends CustomActivity
{
    private UiLifecycleHelper uiHelper;
    /** The pager. */
	private ViewPager pager;
	/** The view that hold dots. */
	private LinearLayout vDots;
    private EditText edt_uName;
    private EditText edt_uPassword;


    private String userName=null;
    private String uPassword=null;

    private Context context;
    SharedPreferences appPref=null;
    private static String TAG= Login.class.getSimpleName();

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
        setupView();
        setupData();

        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);

	}
    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }



    private void setupData()
    {
        context=Login.this;
        appPref = Utilities.getPrivatePreference(context);

        if(getIntent()!=null && getIntent().getStringExtra(Constants.USERNAME)!=null)
        {
            edt_uName.setText(getIntent().getStringExtra(Constants.USERNAME));
        }
    }

    /**
	 * Setup the click & other events listeners for the view components of this
	 * screen. You can add your logic for Binding the data to TextViews and
	 * other views as per your need.
	 */
	private void setupView()
	{
        edt_uName=(EditText)findViewById(R.id.edt_email);
        edt_uPassword=(EditText)findViewById(R.id.edt_password);
        edt_uName.addTextChangedListener(new CustomTextWatcher(edt_uName));
        edt_uPassword.addTextChangedListener(new CustomTextWatcher(edt_uPassword));
		Button b = (Button) setTouchNClick(R.id.btnReg);
		b.setText(Html.fromHtml(getString(R.string.sign_up)));
        LoginButton fbBtn = (LoginButton) findViewById(R.id.fb_login_button);
        fbBtn.setReadPermissions(Arrays.asList("email","public_profile"));
        fbBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    Utilities.Debug(TAG,user.toString());
                    RegisterBean regBean=new RegisterBean();
                    regBean.setName(user.getFirstName());
                    regBean.setlName(user.getLastName());
                    regBean.setEmailId(String.valueOf(user.getProperty("email")));
                    regBean.setPassword(Constants.EMPTY_STRING);
                    new RegisterUser(context,regBean).execute();
                    finish();
                    return;

                }
            }
        });
		setTouchNClick(R.id.btnLogin);
		setTouchNClick(R.id.btnForget);
        setTouchNClick(R.id.btnReg);

		initPager();
	}

	/**
	 * Inits the pager view.
	 */
	private void initPager()
	{
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setPageMargin(10);

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos)
			{
				if (vDots == null || vDots.getTag() == null)
					return;
				((ImageView) vDots.getTag())
						.setImageResource(R.drawable.dot_gray);
				((ImageView) vDots.getChildAt(pos))
						.setImageResource(R.drawable.dot_blue);
				vDots.setTag(vDots.getChildAt(pos));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});
		vDots = (LinearLayout) findViewById(R.id.vDots);

		pager.setAdapter(new PageAdapter());
		setupDotbar();
	}

	/**
	 * Setup the dotbar to show dots for pages of view pager with one dot as
	 * selected to represent current page position.
	 */
	private void setupDotbar()
	{
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		param.setMargins(10, 0, 0, 0);
		vDots.removeAllViews();
		for (int i = 0; i < 5; i++)
		{
			ImageView img = new ImageView(this);
			img.setImageResource(i == 0 ? R.drawable.dot_blue
					: R.drawable.dot_gray);
			vDots.addView(img, param);
			if (i == 0)
			{
				vDots.setTag(img);
			}

		}
	}

	/**
	 * The Class PageAdapter is adapter class for ViewPager and it simply holds
	 * a Single image view with dummy images. You need to write logic for
	 * loading actual images.
	 */
	private class PageAdapter extends PagerAdapter
	{

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return 5;
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup, int)
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int arg0)
		{
			final ImageView img = (ImageView) getLayoutInflater().inflate(
					R.layout.img, null);

			img.setImageResource(R.drawable.img_signin);

			container.addView(img,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT);
			return img;
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup, int, java.lang.Object)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			try
			{
				// super.destroyItem(container, position, object);
				// if(container.getChildAt(position)!=null)
				// container.removeViewAt(position);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

	}

	/* (non-Javadoc)
	 * @see com.taxi.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
        switch(v.getId())
        {
            case R.id.btnLogin:
                appPref.edit().putBoolean(Constants.USER_AUTHITICATED,false).commit();
                userName=edt_uName.getText().toString().trim();
                uPassword=edt_uPassword.getText().toString().trim();
                if (Utilities.ValidateEmptyData(edt_uName, Constants.EMAIL_EPMTY))
                    return;
                if (!Utilities.isValidEmailAddress(userName)) {
                    Utilities.ValidateData(edt_uName, Constants.VALID_EMAILID);
                    return;
                }

                if (Utilities.ValidateEmptyData(edt_uPassword, Constants.PWD_EMPTY))
                    return;
                if (uPassword.length() < 6) {
                    Utilities.ValidateData(edt_uPassword, Constants.VALID_PWD);
                    return;
                }

              new loginUser().execute();
              break;

            case R.id.btnReg:
                Intent intReg = new Intent(this, Registration.class);
                intReg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intReg);
                finish();
                break;

        }


	}


    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                if(appPref.getBoolean(Constants.USER_REGISTERED,false))
                {
                    session.closeAndClearTokenInformation();
                }
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
                if (!Connectivity.checkNetwork(context))
                    Utilities.displayToast(Constants.POORCONNECTIVITY,Login.this,Constants.LONG_TOAST);
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }


    private class loginUser extends AsyncTask<Context, Integer, String> {
        SharedPreferences gcmPref = Utilities.getGCMPreferences(context);
        List<NameValuePair> loginList = null;
        RestUrl restUrl = new RestUrl();
        @Override
        protected String doInBackground(Context... params) {
            CurrentLocationTracker tracker = new CurrentLocationTracker(context);
            LocationBean lbean = tracker.getLatLong();
            loginList=new ArrayList<NameValuePair>();
            loginList.add(new BasicNameValuePair("userId",userName));
            loginList.add(new BasicNameValuePair("password",uPassword));
            loginList.add(new BasicNameValuePair("lat",String.valueOf(lbean.getLatitude())));
            loginList.add(new BasicNameValuePair("long",String.valueOf(lbean.getLongitude())));
            loginList.add(new BasicNameValuePair("deviceToken",gcmPref.getString(GcmConstants.PROPERTY_REG_ID, "")));
            loginList.add(new BasicNameValuePair("deviceType",Constants.DEVICE_TYPE));
            loginList.add(new BasicNameValuePair("osVersion", Utilities.osVersion()));

            String loginResult = null;
            if (Connectivity.checkNetwork(context)) {

                loginResult="{\"status\":1,\"msg\":\"Logged in successfully\",\"uid\":1,\"categories\":[{\"id\":1,\"name\":\"Electronices\"}]}";
//http://www.nexhop.com/feeds/registration
// loginResult = restUrl.queryRestUrl(context, "login", Constants.POST, loginList);

            } else {
                loginResult = Constants.NETWORK_NOTFOUND;
            }
            return loginResult;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyProgress.show(context, "", "");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                MyProgress.CancelDialog();

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
                        appPref.edit().putBoolean(Constants.USER_AUTHITICATED,true).commit();
                        Intent i = new Intent(Login.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        return;
                    }


                } else {
                    MyProgress.CancelDialog();
                    Utilities.displayToast(res[1], context, Constants.LONG_TOAST);
                    return;
                }
            }catch (JSONException je)
            {
                je.printStackTrace();
                MyProgress.CancelDialog();
                Utilities.displayToast(Constants.TECHNICALERROR,context,Constants.LONG_TOAST);
            }
            catch (Exception e) {
                e.printStackTrace();
                MyProgress.CancelDialog();
                Utilities.displayToast(Constants.TECHNICALERROR,context,Constants.LONG_TOAST);
            }
        }
    }


}
