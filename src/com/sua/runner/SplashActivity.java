package com.sua.runner;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_TIME = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
                //startActivity(i);
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }
}
