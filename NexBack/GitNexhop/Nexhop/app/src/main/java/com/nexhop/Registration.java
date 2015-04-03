package com.nexhop;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nexhop.asynctasks.RegisterUser;
import com.nexhop.common.Constants;
import com.nexhop.common.Utilities;
import com.nexhop.custom.CustomActivity;
import com.nexhop.model.RegisterBean;
import com.nexhop.utils.CustomTextWatcher;

public class Registration extends CustomActivity {
   private EditText edt_Name;
//   private EditText edt_lName;
   private EditText edt_phoneNo;
   private EditText edt_uName;
   private EditText edt_password;
//   private EditText edt_confirmPwd;

   private Context context = null;
    private String name=null;
//   private String firstName=null;
//   private String lastName=null;
   private String emailId=null;
   private String uPassword=null;
//   private String cnfPassword=null;
   private String phoneNo=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        context = Registration.this;
        setupView();
    }

    private void setupView() {
        setTouchNClick(R.id.btnReg);
        edt_Name = (EditText) findViewById(R.id.edt_fname);
//        edt_lName = (EditText) findViewById(R.id.edt_lname);
        edt_phoneNo= (EditText) findViewById(R.id.edt_phoneNo);
        edt_uName = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
//        edt_confirmPwd = (EditText) findViewById(R.id.edt_confirm_password);
        edt_Name.addTextChangedListener(new CustomTextWatcher(edt_Name));
//        edt_lName.addTextChangedListener(new CustomTextWatcher(edt_lName));
        edt_phoneNo.addTextChangedListener(new CustomTextWatcher(edt_phoneNo));
        edt_uName.addTextChangedListener(new CustomTextWatcher(edt_uName));
        edt_password.addTextChangedListener(new CustomTextWatcher(edt_password));
//        edt_confirmPwd.addTextChangedListener(new CustomTextWatcher(edt_confirmPwd));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnReg:
                name=edt_Name.getText().toString().trim();
//                lastName=edt_lName.getText().toString().trim();
                phoneNo=edt_phoneNo.getText().toString().trim();
                emailId=edt_uName.getText().toString().trim();
                uPassword=edt_password.getText().toString().trim();
//                cnfPassword=edt_confirmPwd.getText().toString().trim();

                if (Utilities.ValidateEmptyData(edt_Name, Constants.FNAME_EMPTY))
                    return;
                if (!Utilities.isVaildName(name)) {
                    Utilities.ValidateData(edt_Name, Constants.VAILD_FNAME);
                    return;
                }

                /*if (Utilities.ValidateEmptyData(edt_lName, Constants.LNAME_EMPTY))
                    return;
                if (!Utilities.isVaildName(lastName)) {
                    Utilities.ValidateData(edt_lName, Constants.VALID_LNAME);
                    return;
                }*/

                if (Utilities.ValidateEmptyData(edt_phoneNo, Constants.PHONENO_EMPTY))
                    return;
                if (!Utilities.isValidPhoneNumber(phoneNo)) {
                    Utilities.ValidateData(edt_phoneNo, Constants.VALID_PHONENO);
                    return;
                }

                if (Utilities.ValidateEmptyData(edt_uName, Constants.EMAIL_EPMTY))
                    return;
                if (!Utilities.isValidEmailAddress(emailId)) {
                    Utilities.ValidateData(edt_uName, Constants.VALID_EMAILID);
                    return;
                }

                if (Utilities.ValidateEmptyData(edt_password, Constants.PWD_EMPTY))
                    return;
                if (uPassword.length() < 6) {
                    Utilities.ValidateData(edt_password, Constants.VALID_PWD);
                    return;
                }

                /*if (Utilities.ValidateEmptyData(edt_confirmPwd, Constants.CNFPWD_EPMTY))
                    return;
                if (!uPassword.equals(cnfPassword)) {
                    Utilities.ValidateData(edt_password, "");
                    Utilities.ValidateData(edt_confirmPwd, Constants.CNF_PWD);
                    return;
                }*/

                RegisterBean regBean=new RegisterBean();
                regBean.setName(name);
//                regBean.setlName(lastName);
                regBean.setEmailId(emailId);
                regBean.setPassword(uPassword);
                regBean.setPhoneNo(phoneNo);
                new RegisterUser(context,regBean).execute();
                break;
        }
    }
}

