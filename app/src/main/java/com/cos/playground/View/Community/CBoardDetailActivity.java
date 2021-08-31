package com.cos.playground.View.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.DetailDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.cos.playground.config.SessionUser;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CBoardDetailActivity extends AppCompatActivity {

    private static final String TAG = "cBoard";
    private CBoardDetailActivity mContext = CBoardDetailActivity.this;

    private Button btnDelete, btnUpdateForm;
    private TextView tvDetailUsername, tvDetailEmail, tvDetailCategory, tvDetailRegdate,
                    tvDetailTitle, tvDetailContent, tvViewCount, tvLikeCount, ic_Like, ic_Reply;
    private ImageView ivUserProfile, ivFile;

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
        tvDetailUsername = findViewById(R.id.tvDetailUsername);
        tvDetailEmail = findViewById(R.id.tvDetailEmail);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailRegdate = findViewById(R.id.tvDetailRegdate);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailContent = findViewById(R.id.tvDetailContent);
        tvViewCount = findViewById(R.id.tvViewCount);
        tvLikeCount = findViewById(R.id.tvLikeCount);
        ic_Like = findViewById(R.id.ic_Like);
        ic_Reply = findViewById(R.id.ic_Reply);
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
                Date from = cm.getData().getRegdate();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = transFormat.format(from);
                cBoard = cm.getData();
                tvDetailTitle.setText(cm.getData().getTitle());
                tvDetailEmail.setText(cm.getData().getUsermail());
                tvDetailCategory.setText(cm.getData().getCategory());
                tvDetailRegdate.setText(date);
                tvDetailUsername.setText(cm.getData().getWriter());
                tvDetailContent.setText(cm.getData().getContent());
                tvViewCount.setText("조회수 :"+cm.getData().getViewCount());
                tvLikeCount.setText("좋아요 : "+cm.getData().getFavCount());
            }

            @Override
            public void onFailure(Call<CMRespDto<CBoard>> call, Throwable t) {

            }
        });
    }
}