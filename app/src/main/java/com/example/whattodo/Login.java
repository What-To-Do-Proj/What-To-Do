package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import com.amplifyframework.core.Amplify;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button LoginIn = findViewById(R.id.buttonLogin);
        EditText username = findViewById(R.id.LogUserName);
        EditText password = findViewById(R.id.LoginPassword);
        Button createNewAccount = findViewById(R.id.newAccount);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = preferences.edit();

        LoginIn.setOnClickListener(view -> {
            LoginIn(username.getText().toString(), password.getText().toString());
            preferenceEditor.putString("username",username.getText().toString());
            preferenceEditor.apply();
        });

        createNewAccount.setOnClickListener(view -> {
            Intent goToSignUp = new Intent(Login.this, Register.class);
            startActivity(goToSignUp);
        });



    }

    void LoginIn(String username, String password) {
        Amplify.Auth.signIn(
                username,
                password,
                success -> {
                    Log.i(TAG, "signIn: worked " + success.toString());
                    Intent goToMain = new Intent(Login.this, MainActivity.class);
                    startActivity(goToMain);
                },

                error -> Log.e(TAG, "signIn: failed" + error.toString()));
    }
}