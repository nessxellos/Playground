package com.cos.playground.View.Community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.cos.playground.View.Community.adapter.CBoardListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CBoardListActivity extends AppCompatActivity {

    private static final String TAG = "CBoardList";
    private CBoardListActivity mContext = CBoardListActivity.this;

    private RecyclerView rvCBoardList;
    private RecyclerView.LayoutManager rvLayoutManager;

    private CBoardListAdapter cBoardListAdapter;
    private BoardController boardController;

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
    }

    public void initLr() {

    }

    public void initAdapter(){
        rvLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);

    }

    public void initData() {
        boardController.findAll().enqueue(new Callback<CMRespDto<List<CBoard>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<CBoard>>> call, Response<CMRespDto<List<CBoard>>> response) {
                CMRespDto<List<CBoard>> cm = response.body();
                if(cm.getCode() == 1){
                    cBoardListAdapter.addItems(cm.getData());
                }
            }

            @Override
            public void onFailure(Call<CMRespDto<List<CBoard>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}