package com.nexhop.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexhop.MainActivity;
import com.nexhop.R;
import com.nexhop.custom.CustomFragment;
import com.nexhop.model.Data;

/**
 * The Class Settings is the fragment that shows various settings options.
 */
public class Settings extends CustomFragment
{

	/** The category list. */
	private ArrayList<Data> iList;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.settings, null);

		((MainActivity) getActivity()).toolbar.setTitle("Settings");
		((MainActivity) getActivity()).toolbar.findViewById(
				R.id.spinner_toolbar).setVisibility(View.GONE);
		return v;
	}

	/* (non-Javadoc)
	 * @see com.whatshere.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
	}

}
