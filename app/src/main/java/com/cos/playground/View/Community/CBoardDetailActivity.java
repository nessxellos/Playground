package com.cos.playground.View.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.DetailDto;
import com.cos.playground.Controller.DTO.Fav;
import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.CBoard;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.cos.playground.config.SessionUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CBoardDetailActivity extends AppCompatActivity {

    private static final String TAG = "cBoard";
    private CBoardDetailActivity mContext = CBoardDetailActivity.this;

    private static final int ACTIVITY_NUM =1;
    private Button btnDelete, btnUpdateForm;
    private TextView tvDetailUsername, tvDetailEmail, tvDetailCategory, tvDetailRegdate, tvLike,
                    tvDetailTitle, tvDetailContent, tvViewCount, tvLikeCount, ic_Like, ic_Reply;
    private ImageView ivUserProfile, ivFile;

    private BoardController boardController;
    private UserController userController;
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
        initbtbar();
    }
    public void init(){
        userController = new UserController();
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
        tvLike = findViewById(R.id.tvLike);

        ic_Like = findViewById(R.id.ic_Like);
        ic_Reply = findViewById(R.id.ic_Reply);
    }

    public void initLr(){
        if(SessionUser.sessionId==null){
            ic_Like.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
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

        ic_Like.setOnClickListener(v->{
            Fav fav = new Fav(SessionUser.user.getId(),cBoard.getId());
            Log.d(TAG, "initLr: "+ fav);
            userController.likeById(fav).enqueue(new Callback<CMRespDto<Fav>>() {
                @Override
                public void onResponse(Call<CMRespDto<Fav>> call, Response<CMRespDto<Fav>> response) {
                    CMRespDto<Fav> cm = response.body();
                    if(cm.getCode()==1){
                        Toast.makeText(getApplicationContext(), "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                        ic_Like.setSelected(true);
                        tvLike.setText("좋아요 취소");
                        onResume();
                    } else if (cm.getCode()==-1){
                        Toast.makeText(getApplicationContext(), "좋아요를 취소했습니다.", Toast.LENGTH_SHORT).show();
                        ic_Like.setSelected(false);
                        tvLike.setText("좋아요");
                        onResume();
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 정보가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<CMRespDto<Fav>> call, Throwable t) {

                }
            });
        });
    }

    public void initbtbar(){
        BottomNavigationView bn = findViewById(R.id.bottomNavigation);
        BottomNavbar.enableBottomNav(mContext, bn);
        Menu menu = bn.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
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