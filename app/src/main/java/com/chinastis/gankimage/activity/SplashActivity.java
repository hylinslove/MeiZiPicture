package com.chinastis.gankimage.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SpinnerAdapter;

import com.chinastis.gankimage.MainActivity;
import com.chinastis.gankimage.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        countTime();

    }

    private void countTime() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SplashActivity.this.startActivity
                        (new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        },2000);

    }
}
