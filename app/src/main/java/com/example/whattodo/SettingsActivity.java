package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

public class SettingsActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button loginBut = findViewById(R.id.loginBut);// to Login
        Button saveNameOfUser = findViewById(R.id.button_save_name);// to save the name of user in the input Field
        EditText userNameField  = findViewById(R.id.input_user_name);// enter the name of user .

        // save the user name in the SharedPreferences (like local storage in js).
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
//                sharedPreferences.edit().putString("userName",userName).apply();
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();

      // create spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"High", "Medium", "Low"});
       Spinner teamSpinner = findViewById(R.id.chooseTeamSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(dataAdapter);


        saveNameOfUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameField.getText().toString();// target the name of user from the input after clicking the button
                sharedPrefEditor.putString("userName",userName).apply();
//                sharedPrefEditor.apply();
                String team = teamSpinner.getSelectedItem().toString();
                sharedPreferences.edit().putString("team",team).apply();
                Toast.makeText(SettingsActivity.this,"Saved!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        handler = new Handler( Looper.getMainLooper(),
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        return false;
                    }
                });
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

//        ImageView done = findViewById(R.id.done);
        TextView username = findViewById(R.id.login_user_name);
//        done.setVisibility(View.INVISIBLE);
        username.setText("");
//Button login = (Button)  findViewById(R.id.loginBut);
        Amplify.Auth.fetchAuthSession(
                user -> {
                    if (user.isSignedIn()) {
                        String data = Amplify.Auth.getCurrentUser().getUsername();
                        username.setText("loged in user : "+data);
//                        done.setVisibility(View.VISIBLE);
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

//        Amplify.Auth.fetchAuthSession(
//                user -> {
//                    if (user.isSignedIn()) {
//                        login.setText("Log out");
//                    }else {
//                        login.setText("Log in");
//                    }
//                },
//                failure -> Log.e("Amplify", "Could not query DataStore", failure)
//        );
        handler = new Handler( Looper.getMainLooper(),
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        return false;
                    }
                });
    }

    public void back( View view){
        this.finish();
    }



}