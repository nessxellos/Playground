package com.cos.playground.View.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.cos.playground.View.Community.CBoardListActivity;
import com.cos.playground.View.Community.CBoardWriteActivity;
import com.cos.playground.View.User.UserInfoActivity;
import com.cos.playground.config.SessionUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;
    private Button btnLogin, btnInfo, btnBoardlist;
    private static final String TAG = "MainActivity";
    private MainActivity mContext = MainActivity.this;
    private TextView btnJoin;

    @Override
    protected void onResume() {
        super.onResume();
        if(SessionUser.sessionId==null){
            btnInfo.setVisibility(View.GONE);
        } else {
            btnInfo.setVisibility(View.VISIBLE);
        }
        if(SessionUser.sessionId!=null){
            btnJoin.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
        } else {
            btnJoin.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initLr();
        initSetting();
    }

    private void init(){
        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);
        btnInfo = findViewById(R.id.btnInfo);
        btnBoardlist = findViewById(R.id.btnBoardlist);
        // 로그인 세션 없을시 버튼 비활성화

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

        btnInfo.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    UserInfoActivity.class
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        btnBoardlist.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    CBoardListActivity.class
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    public void initSetting(){
        BottomNavigationView bn = findViewById(R.id.bottomNavigation);
        BottomNavbar.enableBottomNav(mContext, bn);
        Menu menu = bn.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
