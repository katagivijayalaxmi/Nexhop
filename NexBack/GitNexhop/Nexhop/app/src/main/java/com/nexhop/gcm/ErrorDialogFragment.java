package com.nexhop.gcm;

import android.app.Dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
/**
 * Created by mahiti on 17/3/15.
 */
public class ErrorDialogFragment extends DialogFragment {
    private Dialog mDialog;
    /**
     * Default constructor. Sets the dialog field to null
     */
    public ErrorDialogFragment() {
        super();
        mDialog = null;
    }
    /**
     * Set the dialog to display
     *
     * @param dialog An error dialog
     */
    public void setDialog(Dialog dialog) {
        mDialog = dialog;
    }

    /*
     * This method must return a Dialog to the DialogFragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//			return mDialog;

        if (mDialog == null)
            super.setShowsDialog (false);

        return mDialog;
    }

}
