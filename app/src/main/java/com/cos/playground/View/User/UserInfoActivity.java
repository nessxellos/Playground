package com.cos.playground.View.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.RemoveDto;
import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.cos.playground.View.auth.LoginActivity;
import com.cos.playground.View.auth.MainActivity;
import com.cos.playground.config.SessionUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;
    private static final String TAG = "UserInfo";
    private UserInfoActivity mContext = UserInfoActivity.this;

    private Button btnUpdateInfo, btnLogout, btnRemove, btnLikeboard,
                    btnMyboard, btnMyreply;
    private ImageView ivUserProfile;
    private TextView tvUsername, tvPhone, tvEmail, tvCareer;

    private UserController userController;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();
        intiLr();
        initSetting();
        if (SessionUser.sessionId!=null){
            initData();
        } else {
            new AlertDialog.Builder(mContext)
                    .setTitle("로그인 후 이용가능합니다.").setMessage("")
                    .setNeutralButton("확인",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(
                            mContext,
                            LoginActivity.class
                    );
                    startActivity(intent);
                }
            }).create().show();
        }
    }

    public void init(){
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);
        btnRemove = findViewById(R.id.btnRemove);
        btnLikeboard = findViewById(R.id.btnLikeboard);
        btnMyboard = findViewById(R.id.btnMyboard);
        btnMyreply = findViewById(R.id.btnMyreply);

        tvCareer = findViewById(R.id.tvCareer);
        tvUsername = findViewById(R.id.tvUsername);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        userController = new UserController();
        ivUserProfile = findViewById(R.id.ivUserProfile);
    }

    public void intiLr(){
        // 정보수정버튼
        btnUpdateInfo.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    UserUpdateActivity.class
            );
            // 세션차원에서 유저데이터 관리
//            intent.putExtra("user", user);
            mContext.startActivity(intent);
        });
        // 로그아웃버튼
        btnLogout.setOnClickListener(v-> {
            if (SessionUser.sessionId.equals("user=authorized")) {
            new AlertDialog.Builder(mContext)
                    .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                           new AlertDialog.Builder(mContext)
                            .setTitle("로그아웃 완료").setMessage("").setNeutralButton("확인",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                SessionUser.sessionId = null;
                                SessionUser.user = null;
                                Intent intent = new Intent(
                                        mContext,
                                        MainActivity.class
                                );
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                                }
                           }).create().show();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    })
                    .show();
            } else{
                Toast.makeText(getApplicationContext(), "작성자 정보와 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        // 회원탈퇴 버튼
        btnRemove.setOnClickListener(v->{
            userController.deleteByUsername(new RemoveDto(SessionUser.user.getUsername())).enqueue(new Callback<CMRespDto<User>>() {
                @Override
                public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                    CMRespDto<User> cm = response.body();
                    if (cm.getCode()==1) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("정말 탈퇴하시겠습니까?").setMessage("Playground를 탈퇴해도, 작성한 글과 댓글은 자동으로 삭제되지 않습니다.")
                                .setPositiveButton("탈퇴하기", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        new AlertDialog.Builder(mContext)
                                                .setTitle("회원탈퇴 완료").setMessage("").setNeutralButton("확인",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                SessionUser.sessionId = null;
                                                SessionUser.user = null;
                                                Intent intent = new Intent(
                                                        mContext,
                                                        MainActivity.class
                                                );
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                startActivity(intent);
                                            }
                                        }).create().show();
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<CMRespDto<User>> call, Throwable t) {

                }
            });
        });
        btnMyreply.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    MyCommentActivity.class
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    public void initData(){
        tvUsername.setText(SessionUser.user.getUsername());
        tvCareer.setText(SessionUser.user.getCareer());
        tvEmail.setText(SessionUser.user.getEmail());
        tvPhone.setText(SessionUser.user.getPhone());
    }

    public void initSetting(){
        BottomNavigationView bn = findViewById(R.id.bottomNavigation);
        BottomNavbar.enableBottomNav(mContext, bn);
        Menu menu = bn.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}