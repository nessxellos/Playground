package com.cos.playground.View.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.BoardUpdateDto;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CBoardUpdateActivity extends AppCompatActivity {

    private static final String TAG = "cBoardUp";
    private CBoardUpdateActivity mContext = CBoardUpdateActivity.this;

    private MaterialButton btnUCBoard;
    private TextInputEditText tfTitle, tfContent;

    private BoardController boardController;
    private CBoard cBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cboard_update);

        init();
        initLr();
//        initSetting();
        initData();
    }

    public void init(){
        boardController = new BoardController();
        btnUCBoard = findViewById(R.id.btnUCBoard);
        tfTitle = findViewById(R.id.tfTitle);
        tfContent = findViewById(R.id.tfContent);
    }

    public void initLr(){
        btnUCBoard.setOnClickListener(v->{
            BoardUpdateDto boardUpdateDto = new BoardUpdateDto(
                    tfTitle.getText().toString(),
                    tfContent.getText().toString()
                    );
            boardController.update(cBoard.getId(), boardUpdateDto).enqueue(new Callback<CMRespDto<CBoard>>() {
                @Override
                public void onResponse(Call<CMRespDto<CBoard>> call, Response<CMRespDto<CBoard>> response) {
                    CMRespDto<CBoard> cm = response.body();
                    if(cm.getCode()==1){
                        Intent intent = new Intent(
                                mContext,
                                CBoardDetailActivity.class
                        );
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("cBoardId", cBoard.getId());
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<CMRespDto<CBoard>> call, Throwable t) {

                }
            });
        });
    }
    public void initData() {
        Intent getIntent = getIntent();
        cBoard = (CBoard) getIntent.getSerializableExtra("cBoard");

        tfTitle.setText(cBoard.getTitle());
        tfContent.setText(cBoard.getContent());
    }
}