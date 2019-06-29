package com.example.workflow_s.ui.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
<<<<<<< HEAD

import com.example.workflow_s.R;
import com.example.workflow_s.ui.main.MainActivity;
=======
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.home.HomeActivity;
>>>>>>> 828f753ecd74dc6c7d5c2ded254288314fa046e8
import com.example.workflow_s.utils.Constant;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.goodiebag.pinview.Pinview;

<<<<<<< HEAD
/**
 * Workflow_S
 * Created by TinhPV on 2019-06-24
 * Copyright © 2019 TinhPV. All rights reserved
 **/

=======
>>>>>>> 828f753ecd74dc6c7d5c2ded254288314fa046e8
public class AuthenticationActivity extends AppCompatActivity implements AuthenticationContract.AuthenticationView {

    private EditText mPhoneNumberEdt;
    private Button mCodeButton, mPhoneButton;
    private LinearLayout mPhoneLayout, mCodeLayout;
    private Pinview mPinview;
    private AuthenticationContract.AuthenticationPresenter mPresenter;
    private Boolean onChangeButtonStatus = false;
    private String userId, phoneNum, verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        init();
    }

    private void init() {
        userId = SharedPreferenceUtils.retrieveData(this, getString(R.string.pref_userId));
        verifyCode = "";

        mPhoneNumberEdt = findViewById(R.id.edt_phone_number);
        mCodeButton = findViewById(R.id.bt_code_continue);
        mPhoneButton = findViewById(R.id.bt_phone_continue);
        mPinview = findViewById(R.id.pinView);
        mPhoneLayout = findViewById(R.id.phone_submit_layout);
        mCodeLayout = findViewById(R.id.code_submit_layout);

        mPhoneLayout.setVisibility(View.VISIBLE);
        mCodeLayout.setVisibility(View.INVISIBLE);
//        mPhoneButton.setEnabled(false);
        mCodeButton.setEnabled(false);

        mPresenter = new AuthenticationPresenterImpl(this, new AuthenticationInteractor());
        mPinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                verifyCode += pinview.getValue();
                if (verifyCode.length() == Constant.CODE_LENGTH) {
                    mCodeButton.setEnabled(true);
                }
            }
        });
    }


    public void handleContinueButtonTapped(View view) {
        if (!onChangeButtonStatus) {
            phoneNum = mPhoneNumberEdt.getText().toString();
            onChangeButtonStatus = true;
            mPresenter.updatePhone(userId, phoneNum);
        } else {
            mPresenter.submitVerifyCode(userId, verifyCode);
        }
    }

    @Override
    public void onFinishedUpdatePhone() {
        mPresenter.getVerifyCode(userId);
    }

    @Override
    public void onFinishedGetVerifyCode() {
        mPhoneLayout.setVisibility(View.GONE);
        mCodeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishedSubmitVerifyCode() {
<<<<<<< HEAD
        Intent intent = new Intent(this, MainActivity.class);
=======
        Intent intent = new Intent(this, HomeActivity.class);
>>>>>>> 828f753ecd74dc6c7d5c2ded254288314fa046e8
        startActivity(intent);
    }
}
