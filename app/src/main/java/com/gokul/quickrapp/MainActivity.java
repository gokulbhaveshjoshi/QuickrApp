package com.gokul.quickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashScreen();
    }

    private void splashScreen() {
        try{
            //                /* Create an Intent that will start the Menu-Activity. */
            //                Intent mainIntent = new Intent(Splash.this,Menu.class);
            //                Splash.this.startActivity(mainIntent);
            //                Splash.this.finish();
            new Handler(Looper.getMainLooper()).postDelayed(this::goToNext, 3000);
        }

        catch(Exception e){
            Log.e("Splash Screen", ""+e);
        }
    }

    private void goToNext() {
       startActivity(new Intent(this, HomeActivity.class));
       finish();
    }
}