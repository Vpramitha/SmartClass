package com.firstapp.myapplicationtest3;

import static androidx.core.os.HandlerCompat.postDelayed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                Intent intent1;
                intent1 = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        }, 3000);

    }


}