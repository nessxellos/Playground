package com.cos.playground.View.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.auth.MainActivity;
import com.cos.playground.config.SessionUser;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfo";
    private UserInfoActivity mContext = UserInfoActivity.this;

    private Button btnUpdateInfo, btnLogout;
    private ImageView ivUserProfile;

    private UserController userController;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();
        intiLr();
    }

    public void init(){
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);
        userController = new UserController();
        ivUserProfile = findViewById(R.id.ivUserProfile);

    }

    public void intiLr(){
        btnUpdateInfo.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    UserUpdateActivity.class
            );
            // 세션차원에서 유저데이터 관리
//            intent.putExtra("user", user);
            mContext.startActivity(intent);
        });
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

                            //시간지연 후 이동
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent = new Intent(
//                                            mContext,
//                                            MainActivity.class
//                                    );
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                    startActivity(intent);
//                                }
//                            }, 2000);

                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    })
                    .show();
            } else{
                Toast.makeText(getApplicationContext(), "로그인 정보가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}