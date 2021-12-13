package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.io.File;

public class TaskDetailActivity extends AppCompatActivity {
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
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
        handler = new Handler( Looper.getMainLooper(),
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        return false;
                    }
                });
        // create intent to get passing extra from main activity
        Intent detailIntent=getIntent();
        String taskName  = detailIntent.getStringExtra("taskName");
        String body  = detailIntent.getStringExtra("body");
        String state  = detailIntent.getStringExtra("state");
        String key =detailIntent.getStringExtra("imgKey");


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TaskDetailActivity.this);
        String locationData = sharedPreferences.getString(key,"there is no location");


// set the name of the task in the  TextView
        TextView taskNameView = findViewById(R.id.task_details_title_taskName_view);
        taskNameView.setText(taskName);


        TextView taskBodyView = findViewById(R.id.body);
        taskBodyView.setText(body);

        TextView taskStateView = findViewById(R.id.state);
        taskStateView.setText(state);

// location
        TextView taskLocation = findViewById(R.id.location);
        taskLocation.setText(locationData);

        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir() + "/download.txt"),
                result ->{ Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getAbsolutePath());
                    File imgFile = new  File(result.getFile().getAbsolutePath());
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ImageView taskimage=findViewById(R.id.taskimage);
                        taskimage.setImageBitmap(myBitmap);
                    }

                },
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );

    }
    public void back( View view){
        this.finish();
    }


}