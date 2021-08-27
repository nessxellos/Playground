package com.cos.playground.View.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.Community.CBoardWriteActivity;
import com.cos.playground.View.User.UserInfoActivity;
import com.cos.playground.config.SessionUser;

public class MainActivity extends AppCompatActivity {

    private Button btnJoin, btnLogin, btnWrite, btnInfo;
    private static final String TAG = "MainActivity";
    private MainActivity mContext = MainActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initLr();
    }

    private void init(){
        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);
        btnWrite = findViewById(R.id.btnWrite);
        btnInfo = findViewById(R.id.btnInfo);
    }

    private void initLr(){
        btnJoin.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    JoinActivity.class
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    LoginActivity.class
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        btnWrite.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    CBoardWriteActivity.class
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        btnInfo.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    UserInfoActivity.class
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
