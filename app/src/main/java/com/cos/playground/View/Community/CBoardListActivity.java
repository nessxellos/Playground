package com.cos.playground.View.Community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.cos.playground.View.Community.adapter.CBoardListAdapter;
import com.cos.playground.View.auth.LoginActivity;
import com.cos.playground.config.SessionUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CBoardListActivity extends AppCompatActivity {

    private static final String TAG = "CBoardList";
    private CBoardListActivity mContext = CBoardListActivity.this;

    private RecyclerView rvCBoardList;
    private RecyclerView.LayoutManager rvLayoutManager;

    private Button btnLogin, btnWrite;

    private CBoardListAdapter cBoardListAdapter;
    private BoardController boardController;

    @Override
    protected void onResume() {
        super.onResume();
        if (SessionUser.sessionId == null) {
            btnWrite.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.GONE);
            btnWrite.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cboard_list);

        init();
        initLr();
        initAdapter();
        initData();
    }

    public void init() {
        boardController = new BoardController();
        rvCBoardList = findViewById(R.id.rvCBoardList);
        btnLogin = findViewById(R.id.btnLogin);
        btnWrite = findViewById(R.id.btnWrite);
    }

    public void initLr() {
        btnWrite.setOnClickListener(v-> {
                    Intent intent = new Intent(
                            mContext,
                            CBoardWriteActivity.class
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
    }

    public void initAdapter(){
        rvLayoutManager = new LinearLayoutManager(
                mContext, RecyclerView.VERTICAL, false);
        rvCBoardList.setLayoutManager(rvLayoutManager);

        cBoardListAdapter = new CBoardListAdapter(mContext);
        rvCBoardList.setAdapter(cBoardListAdapter);
    }

    public void initData() {
        boardController.findAll().enqueue(new Callback<CMRespDto<List<CBoard>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<CBoard>>> call, Response<CMRespDto<List<CBoard>>> response) {
                CMRespDto<List<CBoard>> cm = response.body();
                cBoardListAdapter.addItems(cm.getData());
//                if(cm.getCode() == 1){
//                    cBoardListAdapter.addItems(cm.getData());
//                }
            }

            @Override
            public void onFailure(Call<CMRespDto<List<CBoard>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}