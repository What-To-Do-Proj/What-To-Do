package com.example.whattodo;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            Amplify.addPlugin( new AWSApiPlugin() );
//            Amplify.addPlugin( new AWSDataStorePlugin() );
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.addPlugin( new AWSS3StoragePlugin() );
//            Amplify.configure( getApplicationContext() );
//            Log.i( "Tutorial", "Initialized Amplify" );
//        } catch (AmplifyException failure) {
//            Log.e( "Tutorial", "Could not initialize Amplify", failure );
//        }

        setContentView(R.layout.activity_login);
        Button LoginIn = findViewById(R.id.buttonLogin);
        EditText username = findViewById(R.id.LogUserName);
        EditText password = findViewById(R.id.LoginPassword);
        Button createNewAccount = findViewById(R.id.newAccount);
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled(true);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = preferences.edit();


        LoginIn.setOnClickListener(view -> {
             LoginIn(username.getText().toString(),
             password.getText().toString());
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