package com.nexhop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.nexhop.common.Constants;
import com.nexhop.common.Utilities;
import com.nexhop.custom.CustomActivity;

/**
 * The Activity Home is launched after the Splash screen. It simply show two
 * options for Login and Signup.
 */
public class Home extends CustomActivity
{

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		setupView();
	}

	/**
	 * Setup the click & other events listeners for the view components of this
	 * screen. You can add your logic for Binding the data to TextViews and
	 * other views as per your need.
	 */
	private void setupView()
	{
		setTouchNClick(R.id.btnReg);
		setTouchNClick(R.id.btnLogin);
	}

	/* (non-Javadoc)
	 * @see com.taxi.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
        switch (v.getId())
        {
            case R.id.btnReg:
                Intent intReg = new Intent(this, Registration.class);
                intReg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intReg);
//                finish();
                break;
            case R.id.btnLogin:
                Intent intLogin = new Intent(this, Login.class);
                intLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intLogin);
//                finish();
                break;
        }
		/*if (v.getId() == R.id.btnLogin)
		{

		}*/
	}

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences appPref= Utilities.getPrivatePreference(Home.this);
        if(appPref.getBoolean(Constants.USER_AUTHITICATED,false))
        {
            finish();
            return;
        }
    }
}
