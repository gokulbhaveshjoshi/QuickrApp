package com.gokul.quickrapp;

import androidx.appcompat.app.AppCompatActivity;

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
        Toast.makeText(this, "SplashActivity", Toast.LENGTH_LONG).show();
        splashScreen();
    }

    private void splashScreen() {
        try{
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(Splash.this,Menu.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.finish();
                toast();

            }, 3000);
        }

        catch(Exception e){
            Log.e("Splash Screen", ""+e);
        }
    }

    private void toast() {
        Toast.makeText(this, "SplashActivity", Toast.LENGTH_LONG).show();
    }
}