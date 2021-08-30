package com.cos.playground.View.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.DetailDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.cos.playground.config.SessionUser;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CBoardDetailActivity extends AppCompatActivity {

    private static final String TAG = "cBoard";
    private CBoardDetailActivity mContext = CBoardDetailActivity.this;

    private MaterialButton btnDelete, btnUpdateForm;
    private TextView tvBox;

    private BoardController boardController;
    private CBoard cBoard;
    private int cBoardId;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cboard_detail);

        init();
        initLr();
        initSetting();
    }
    public void init(){
        boardController = new BoardController();
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdateForm = findViewById(R.id.btnUpdateForm);
        tvBox = findViewById(R.id.tvBox);
    }

    public void initLr(){
        btnUpdateForm.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    CBoardUpdateActivity.class
            );
            intent.putExtra("cBoard", cBoard);
            mContext.startActivity(intent);
        });

        btnDelete.setOnClickListener(v->{
            boardController.deleteById(cBoard.getId()).enqueue(new Callback<CMRespDto<CBoard>>() {
                @Override
                public void onResponse(Call<CMRespDto<CBoard>> call, Response<CMRespDto<CBoard>> response) {
                    CMRespDto<CBoard> cm = response.body();
                    if(cm.getCode()!=1){
                        btnDelete.setVisibility(View.GONE);
                        btnUpdateForm.setVisibility(View.GONE);
                    }
                    Intent intent = new Intent(
                            mContext,
                            CBoardDetailActivity.class
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<CMRespDto<CBoard>> call, Throwable t) {

                }
            });
        });
    }

    public void initSetting(){
        Intent getIntent = getIntent();
        cBoardId = getIntent.getIntExtra("cBoardId", 0);
        if(cBoardId == 0) finish();
    }

    public void initData(){
        DetailDto detailDto = new DetailDto();
        if(SessionUser.user!=null){
        detailDto.setId(SessionUser.user.getId());
        } else { detailDto.setId(0);}
        boardController.findById(cBoardId, detailDto).enqueue(new Callback<CMRespDto<CBoard>>() {
            @Override
            public void onResponse(Call<CMRespDto<CBoard>> call, Response<CMRespDto<CBoard>> response) {
                CMRespDto<CBoard> cm = response.body();
                if(cm.getCode()!=1){
                    btnDelete.setVisibility(View.GONE);
                    btnUpdateForm.setVisibility(View.GONE);
                }
                cBoard = cm.getData();
                tvBox.setText("");
                tvBox.append("id: "+cm.getData().getId()+"\n");
                tvBox.append("title: "+cm.getData().getTitle()+"\n");
                tvBox.append("content: "+cm.getData().getContent()+"\n");
                tvBox.append("regdate: "+cm.getData().getRegdate()+"\n");
                tvBox.append("file: "+cm.getData().getFile()+"\n");
                tvBox.append("favCount: "+cm.getData().getFavCount()+"\n");
                tvBox.append("viewCount: "+cm.getData().getViewCount()+"\n");
                tvBox.append("userId: "+cm.getData().getUserId()+"\n");
                tvBox.append("writer: "+cm.getData().getWriter()+"\n");
            }

            @Override
            public void onFailure(Call<CMRespDto<CBoard>> call, Throwable t) {

            }
        });
    }
}