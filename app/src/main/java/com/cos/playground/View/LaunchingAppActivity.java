package com.cos.playground.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cos.playground.R;
import com.cos.playground.View.auth.MainActivity;

public class LaunchingAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching_app);

        Handler handler = new Handler();
        handler.postDelayed(new LaunchingHandler(), 4000);
    }

    private class LaunchingHandler implements Runnable {

        @Override
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            LaunchingAppActivity.this.finish();
        }
    }
}