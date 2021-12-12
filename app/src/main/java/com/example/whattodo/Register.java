package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configureAmplify();


        Button signUp = findViewById(R.id.btnReg);
        EditText username = findViewById(R.id.RegUserName);
        EditText email = findViewById(R.id.RegGmail);
        EditText password = findViewById(R.id.RegPassword);
        TextView signIn = findViewById(R.id.btnGotoLogin);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(
                        username.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString()
                );
//                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(Register.this, Login.class);
                startActivity(signIn);
            }
        });
    }
    void signUp(String username, String email, String password) {
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();

        Amplify.Auth.signUp(
                username,
                password,
                options,
                success -> {
                    Log.i(TAG, "signUp successful: " + success.toString());
                    Intent goToVerification = new Intent(Register.this, ConfirmActivity.class);
                    goToVerification.putExtra("username",  username);
//                    goToVerification.putExtra("password", password);
                    startActivity(goToVerification);
                },
                error -> {
                    Log.e(TAG, "signUp failed: " + error.toString());
                });
    }

    private void configureAmplify() {
        // configure Amplify plugins
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "Successfully initialized Amplify plugins");
        } catch (AmplifyException exception) {
            Log.e(TAG, "Failed to initialize Amplify plugins => " + exception.toString());
        }

    }
}