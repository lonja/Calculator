package com.lonja.calculator.common.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

class GoogleAuthenticationStrategy extends BaseSocialAuthenticationStrategy {

    private GoogleSignInOptions mGoogleSignInOptions;

    private GoogleApiClient mGoogleApiClient;

    private GoogleCallback<GoogleSignInResult> mSignInResultCallback;

    GoogleAuthenticationStrategy(@NonNull FragmentActivity activity,
                                 @NonNull GoogleCallback<GoogleSignInResult> callback) {
        super(activity);
        mSignInResultCallback = callback;
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(fragmentActivity)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        mSignInResultCallback.onError(new Exception(connectionResult.getErrorMessage()));
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    @Override
    public void executeCallbacks(int requestCode, int responseCode, Intent data) {
        if (responseCode == Activity.RESULT_OK) {
            return;
        }
        mSignInResultCallback.onSuccess(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
    }

    @Override
    public void login() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        fragmentActivity.startActivityForResult(signInIntent, 9001);
    }
}