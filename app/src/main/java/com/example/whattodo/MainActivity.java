package com.example.whattodo;
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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
//import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.ArrayList;
import java.util.List;



    public class MainActivity extends AppCompatActivity {
        List<Task> tasks = new ArrayList<>();
        TaskAdapter adapter = new TaskAdapter( tasks, this );
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );

// Initialized Amplify
            try {
                Amplify.addPlugin( new AWSApiPlugin() );
                Amplify.addPlugin( new AWSDataStorePlugin() );
                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.addPlugin( new AWSS3StoragePlugin() );
                Amplify.configure( getApplicationContext() );
                Log.i( "Tutorial", "Initialized Amplify" );
            } catch (AmplifyException failure) {
                Log.e( "Tutorial", "Could not initialize Amplify", failure );
            }
            Amplify.DataStore.observe( Task.class,
                    started -> Log.i( "Tutorial", "Observation began." ),
                    change -> Log.i( "Tutorial", change.item().toString() ),
                    failure -> Log.e( "Tutorial", "Observation failed.", failure ),
                    () -> Log.i( "Tutorial", "Observation complete." )
            );
            Amplify.DataStore.observe( Team.class,
                    started -> Log.i( "Tutorial", "Observation began." ),
                    change -> Log.i( "Tutorial", change.item().toString() ),
                    failure -> Log.e( "Tutorial", "Observation failed.", failure ),
                    () -> Log.i( "Tutorial", "Observation complete." )
            );


// for auth ...........................
//            Amplify.Auth.fetchAuthSession(
//                    result -> Log.i( "AmplifyQuickstart", result.toString() ),
//                    error -> Log.e( "AmplifyQuickstart", error.toString() )
//            );


// getting data from database .
//
            Amplify.DataStore.query(
                    Task.class,
                    items -> {
                        while (items.hasNext()) {
                            Task item = items.next();
                            tasks.add( item );
                            Log.i( "Amplify", "Id " + item.getId() );
                        }

                    },
                    failure -> Log.e( "Amplify", "Could not query DataStore", failure )
            );

            Button Register = findViewById( R.id.Register );
            Register.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent AddTaskIntent = new Intent( MainActivity.this, Register.class );
                    startActivity( AddTaskIntent );
                }
            } );

            RecyclerView recyclerView = findViewById( R.id.RV_main );
            recyclerView.setAdapter( adapter );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );
            linearLayoutManager.setReverseLayout( true );
            linearLayoutManager.setStackFromEnd( true );
            recyclerView.setLayoutManager( linearLayoutManager );


            Button addTask = MainActivity.this.findViewById( R.id.button_addTask );
            Button allTasks = MainActivity.this.findViewById( R.id.button_allTasks );
            Button settings = MainActivity.this.findViewById( R.id.button_settings );


            TextView userNameView = findViewById( R.id.home_page_userName );


            String userName = sharedPreferences.getString( "userName", "User" );
            userNameView.setText( userName + "' Tasks" );


            addTask.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToAddTaskActivity = new Intent( MainActivity.this, AddTaskActivity.class );
                    startActivity( goToAddTaskActivity );

                }
            } );
            handler = new Handler( Looper.getMainLooper(),
                    new Handler.Callback() {
                        @Override
                        public boolean handleMessage(@NonNull Message message) {
                            return false;
                        }
                    });
            allTasks.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToAllTasksActivity = new Intent( MainActivity.this, AllTasksActivity.class );
                    startActivity( goToAllTasksActivity );
                }
            } );

            settings.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingsIntent = new Intent( MainActivity.this, SettingsActivity.class );
                    startActivity( settingsIntent );
                }
            } );

        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onResume() {
            super.onResume();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( MainActivity.this );
            String team = sharedPreferences.getString( "team", "team" );
            TextView userNameView = findViewById( R.id.home_page_userName );
            String userName = sharedPreferences.getString( "userName", "User" );
            userNameView.setText( userName + "' Tasks" );



            Amplify.DataStore.query(
                    Team.class,
                    items -> {
                        while (items.hasNext()) {
                            Team item = items.next();

                            Amplify.DataStore.query(
                                    Task.class, Task.TEAM_ID.eq( item.getId() ),
                                    itemss -> {
                                        tasks.clear();
                                        while (itemss.hasNext()) {
                                            Task item1 = itemss.next();
                                            tasks.add( item1 );
                                            Log.i( "DUCK", "list " + item1.getTeamId() );

                                        }
                                        handler.post( runnable );
                                    },
                                    failure -> Log.e( "Amplify", "Could not query DataStore", failure )
                            );
                            Log.i( "Amplify", "Id " + item.getId() );
                        }
                        handler.post( runnable );
                    },
                    failure -> Log.e( "Amplify", "Could not query DataStore", failure )
            );
        }
    }