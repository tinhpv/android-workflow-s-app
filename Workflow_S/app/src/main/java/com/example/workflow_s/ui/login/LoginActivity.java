package com.example.workflow_s.ui.login;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-06
 * Copyright © 2019 TinhPV. All rights reserved
 **/

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.workflow_s.R;
import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;
import com.example.workflow_s.ui.authentication.AuthenticationActivity;
import com.example.workflow_s.ui.main.MainActivity;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    private static final String TAG = "LOGIN_ACTIVITY";
    final private int REQUEST_CODE_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleAccount;
    private User currentUser = null;

    private LoginContract.LoginPresenter mLoginPresenter;

    LottieAnimationView mAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupLoadingAnimation();
        initializeGoogleSignIn();
        initData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void setupLoadingAnimation() {
        mAnimationView = findViewById(R.id.animation_view);
        mAnimationView.setVisibility(View.INVISIBLE);
    }

    private void switchOnLoading() {
        mAnimationView.setVisibility(View.VISIBLE);
        if (!mAnimationView.isAnimating()) {
            mAnimationView.playAnimation();
        }
    }

    private void switchOffLoading() {
        if (mAnimationView.isAnimating()) {
            mAnimationView.pauseAnimation();
            mAnimationView.cancelAnimation();
            mAnimationView.setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {
        mLoginPresenter = new LoginPresenterImpl(this, new LoginInteractor());
    }

    private void initializeCurrentUser(GoogleSignInAccount account) {
        String id = account.getId();
        String name = account.getDisplayName();
        String email = account.getEmail();
        String type = getResources().getString(R.string.google);
        String role = "";
        String token = "";
        String avatar = "";
        if (null != account.getPhotoUrl()) {
            avatar = account.getPhotoUrl().toString();
        }

        this.currentUser = new User(id, name, email, type, role, avatar, token);
    }


    private void initializeGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    // TODO -  HANDLE CASE GOOGLE ACCOUNT
    private void updateUI(GoogleSignInAccount account) {
        if (null != account) {
            Log.d(TAG, "Sign-In successfully!");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "Sign-In fail!");
        } // end if
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            mGoogleAccount = completedTask.getResult(ApiException.class);
            if (null != mGoogleAccount) {
                switchOnLoading();
                initializeCurrentUser(mGoogleAccount);
                mLoginPresenter.addUserToDB(currentUser);
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code = " + e.getStatusCode());
        } // end try
    }


    public void handleGoogleSignInTapped(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void saveCurrentUserToPreference(User user) {
        SharedPreferenceUtils.saveCurrentUserData(this, user, null);
    }

    public void saveCurrentOrganizationToPreference(Organization organization) {
        SharedPreferenceUtils.saveCurrentUserData(this, null, organization);
    }

    @Override
    public void navigateToCodeVerifyActivity() {
        switchOffLoading();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToMainActivity() {
        switchOffLoading();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFinishedAddUser(User user) {
        currentUser.setRole(user.getRole());
        currentUser.setToken(user.getToken());
        saveCurrentUserToPreference(currentUser);
        mLoginPresenter.getCurrentOrganization(currentUser.getId());
    }

    @Override
    public void onFinishedGetOrg(UserOrganization org) {
        Organization tmpOrg = new Organization(org.getOrganizationId(), org.getOrganizationName());
        saveCurrentOrganizationToPreference(tmpOrg);
        mLoginPresenter.checkRoleUser(currentUser.getRole());
    }

}
