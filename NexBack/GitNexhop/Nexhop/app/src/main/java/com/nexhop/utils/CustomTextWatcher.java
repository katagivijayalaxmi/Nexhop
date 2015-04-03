package com.nexhop.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by vijayalaxmi on 18/3/15.
 */
public class CustomTextWatcher implements TextWatcher {
    EditText edtTxt;

    public CustomTextWatcher(EditText edtText) {
        this.edtTxt =edtText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        edtTxt.setError(null);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
