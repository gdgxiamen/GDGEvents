package com.xmgdg.gdgevents.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.xmgdg.gdgevents.Tools.AppStat;
import com.xmgdg.gdgevents.Tools.Tool;

/**
 * Created by xcold on 15-5-26.
 */
public class GooglePlusLoginUtils implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHOTO = "photo";
    public static final String PROFILE = "profile";
    /* Request code used to invoke sign in user interactions. */
    private static final int PROFILE_PIC_SIZE = 400;
    private String TAG = "GooglePlusLoginUtils";
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;

    private SignInButton btnSignIn;
    private Context ctx;
    private GPlusLoginStatus loginstatus;

    public GooglePlusLoginUtils(Context ctx, int btnRes) {
        Log.i(TAG, "GooglePlusLoginUtils");
        this.ctx = ctx;
        this.btnSignIn = (SignInButton) ((Activity) ctx).findViewById(btnRes);
        btnSignIn.setOnClickListener(this);
        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    public void setLoginStatus(GPlusLoginStatus loginStatus) {
        this.loginstatus = loginStatus;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "onConnectionFailed");
        Log.i(TAG, "Error Code " + result.getErrorCode());
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), (Activity) ctx, 0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    public void setSignInClicked(boolean value) {
        mSignInClicked = value;
    }

    public void setIntentInProgress(boolean value) {
        mIntentInProgress = value;
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void reconnect() {
        if (!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void signInWithGplus() {
        Log.i(TAG, "signInWithGplus");
        if (!mGoogleApiClient.isConnecting()) {
            Log.i(TAG, "mGoogleApiClient is Not Connecting");
            Toast.makeText(ctx, "mGoogleApiClient is Not Connecting", Toast.LENGTH_LONG).show();
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    private void resolveSignInError() {
        Log.i(TAG, "resolveSignInError");
        if (mConnectionResult != null && mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult((Activity) ctx, AppStat.MainActivityIntentCode.GPLUS_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        Log.i(TAG, "onConnected");
        mSignInClicked = false;
        Log.w(TAG, "User is connected!");
        // Get user's information
        if (getProfileInformation()) {
            btnSignIn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Log.i(TAG, "onConnectionSuspended");
        mGoogleApiClient.connect();
    }

    private boolean getProfileInformation() {
        Log.i(TAG, "getProfileInformation");
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;
                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                Bundle profile = new Bundle();
                profile.putString(NAME, personName);
                profile.putString(EMAIL, email);
                profile.putString(PHOTO, personPhotoUrl);
                profile.putString(PROFILE, personGooglePlusProfile);

                loginstatus.OnSuccessGPlusLogin(profile);

                Tool.SignUpInLeanCloud(email, personPhotoUrl, personPhotoUrl, personName, (Activity) ctx);

                //   new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
                return true;
            } else {
                Toast.makeText(ctx,
                        "Person information is null", Toast.LENGTH_LONG).show();
                Log.w(TAG, "Person information is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        signInWithGplus();
    }

    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == AppStat.MainActivityIntentCode.GPLUS_SIGN_IN) {
            if (responseCode != ((Activity) ctx).RESULT_OK) {
                setSignInClicked(false);
            }
            setIntentInProgress(false);
            reconnect();
        }
    }

    public interface GPlusLoginStatus {
        void OnSuccessGPlusLogin(Bundle profile);
    }
}
