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

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.cos.playground.View.Community.CBoardDetailActivity;
import com.cos.playground.View.Community.CBoardListActivity;
import com.cos.playground.View.Community.CBoardWriteActivity;
import com.cos.playground.View.User.UserInfoActivity;
import com.cos.playground.config.SessionUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;
    private Button btnLogin, btnInfo, btnBoardlist;
    private static final String TAG = "MainActivity";
    private MainActivity mContext = MainActivity.this;
    private TextView btnJoin;
    private BoardController boardController;
    private TextView tvBest1, tvBest2;
    private CBoard cBoard;
    private int best1;
    private int best2;

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
        initData();
        initLr();
        initSetting();
    }

    private void init(){
        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);
        btnInfo = findViewById(R.id.btnInfo);
        btnBoardlist = findViewById(R.id.btnBoardlist);
        boardController = new BoardController();
        tvBest1 = findViewById(R.id.tvBest1);
        tvBest2 = findViewById(R.id.tvBest2);
        // 로그인 세션 없을시 버튼 비활성화

    }

    private void initData(){
        boardController.getTopPost().enqueue(new Callback<CMRespDto<List<CBoard>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<CBoard>>> call, Response<CMRespDto<List<CBoard>>> response) {
                CMRespDto<List<CBoard>> cm = response.body();
                if(cm.getCode()==1){
                    tvBest1.setText("");
                    tvBest1.append("제목 : "+cm.getData().get(0).getTitle()+"\n");
                    tvBest1.append("조회수 : "+cm.getData().get(0).getViewCount());
                    best1 = cm.getData().get(0).getId();
                    tvBest2.setText("");
                    tvBest2.append("제목 : "+cm.getData().get(1).getTitle()+"\n");
                    tvBest2.append("조회수 : "+cm.getData().get(1).getViewCount());
                    best2 = cm.getData().get(1).getId();
                }
            }

            @Override
            public void onFailure(Call<CMRespDto<List<CBoard>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
        tvBest1.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    CBoardDetailActivity.class
            );
            intent.putExtra("cBoardId", best1);
            mContext.startActivity(intent);
        });
        tvBest2.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    CBoardDetailActivity.class
            );
            intent.putExtra("cBoardId", best2);
            mContext.startActivity(intent);
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
