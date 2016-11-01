package com.example.kasparsfisers.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText logUser, logPass;
    Button login, register;
    ImageView imgLoading;
    SharedPreferencesUtils preferences;

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = new SharedPreferencesUtils(this);
        logUser = (EditText) findViewById(R.id.loginUser);
        logPass = (EditText) findViewById(R.id.loginPass);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnLoginReg);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        imgLoading = (ImageView) findViewById(R.id.imageRotate);

        // Check if user is logged in
        if(preferences.sessionLoggedIn()){
            startActivity(new Intent(LoginActivity.this, MainScreen.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            imgLoading.startAnimation(animation);

            hideViews();

            Handler handler = new Handler(getMainLooper());
            handler.postDelayed(new Runnable() {
                public void run() {

                    String user = logUser.getText().toString();
                    String pass = logPass.getText().toString();

                    String userDetails = preferences.login(user,pass);
                    if (userDetails.equals("")) {
                        Toast.makeText(LoginActivity.this, R.string.denied, Toast.LENGTH_SHORT).show();
                        showViews();

                    } else {
                        preferences.sessionSetLoggedIn(true);
                        startActivity(new Intent(LoginActivity.this, MainScreen.class));
                        finish();
                        showViews();
                    }
                }
            }, 3000);

        }

        if (v.getId() == R.id.btnLoginReg) {
            RegisterFragment alertdFragment = new RegisterFragment();
            // Show Alert DialogFragment
            alertdFragment.show(fm, "");
        }
    }
    private void hideViews(){

        login.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        logUser.setVisibility(View.GONE);
        logPass.setVisibility(View.GONE);
    }

    private void showViews(){

        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
        logUser.setVisibility(View.VISIBLE);
        logPass.setVisibility(View.VISIBLE);
    }

}
