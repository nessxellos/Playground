package com.cos.playground.View.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.auth.MainActivity;
import com.cos.playground.config.SessionUser;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfo";
    private UserInfoActivity mContext = UserInfoActivity.this;

    private Button btnUpdateInfo, btnLogout;

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
    }

    public void intiLr(){
        btnLogout.setOnClickListener(v->{
            new AlertDialog.Builder(mContext)
                    .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SessionUser.sessionId=0;
                            SessionUser.user=null;
                            Intent intent = new Intent(
                                    mContext,
                                    MainActivity.class
                            );
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    })
                    .show();
        });
    }
}