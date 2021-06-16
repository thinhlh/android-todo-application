package com.example.todoapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInIntentLauncher;
    private CallbackManager facebookCallBackManager;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hide Action Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Setup Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setUpGoogleSignInLauncher();


        SignInButton googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_WIDE);
        googleSignInButton.setOnClickListener(this);

        //Setup Facebook Sign In
        facebookCallBackManager = CallbackManager.Factory.create();
        LoginButton facebookLoginButton = findViewById(R.id.facebook_login_button);
        facebookLoginButton.setPadding(30, 30, 30, 30);

        facebookLoginButton.registerCallback(facebookCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                UserRepo.uid = loginResult.getAccessToken().getUserId();
                Log.d("", "Your User ID: " + loginResult.getAccessToken().getUserId());
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        facebookCallBackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            UserRepo.uid = account.getId();
        }
    }

    private void setUpGoogleSignInLauncher() {
        googleSignInIntentLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    handleSignInResult(task);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) throws ApiException {
        GoogleSignInAccount account = completedTask.getResult(ApiException.class);
        UserRepo.uid = account.getId();
        startActivity(new Intent(this, HomeActivity.class));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.google_sign_in_button) {
            googleSignIn();
        }

    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInIntentLauncher.launch(signInIntent);
    }
}
