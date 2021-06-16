package com.example.todoapplication;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        FirebaseApp.initializeApp(this);

        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        AccessToken facebookAccessToken = AccessToken.getCurrentAccessToken();
        boolean isFacebookLoggedIn = facebookAccessToken != null && facebookAccessToken.getUserId() != null;
        if (account == null && !isFacebookLoggedIn) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {

            if (isFacebookLoggedIn) {
                UserRepo.uid = facebookAccessToken.getUserId();
            } else {
                UserRepo.uid = account.getId();
            }
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}