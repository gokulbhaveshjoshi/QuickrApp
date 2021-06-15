package com.gokul.quickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        splashScreen();
    }

    private void splashScreen() {
        try{
            new Handler(Looper.getMainLooper()).postDelayed(this::goToNext, 3000);
        }

        catch(Exception e){
            Log.e("Splash Screen", ""+e);
        }
    }

    private void goToNext() {
        if(auth.getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
        }
        else{
            startActivity(new Intent(this, BuyActivity.class));
        }
        finish();

    }
}