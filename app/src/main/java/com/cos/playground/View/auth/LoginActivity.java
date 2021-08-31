package com.cos.playground.View.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.LoginDto;
import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.cos.playground.config.SessionUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private LoginActivity mContext = LoginActivity.this;

    private TextInputEditText tfUsername, tfPassword;
    private Button btnLogin;
    private TextView tvFindId, tvFindPw, tvJoin;
    private static final int ACTIVITY_NUM = 1;

    private UserController userController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initLr();
        initSetting();

    }

    public void init(){
        userController = new UserController();
        tfUsername = findViewById(R.id.tfUsername);
        tfPassword = findViewById(R.id.tfPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvFindId = findViewById(R.id.tvFindId);
        tvFindPw = findViewById(R.id.tvFindPw);
        tvJoin = findViewById(R.id.tvJoin);
    }

    public void initLr(){
        btnLogin.setOnClickListener(v->{
            String username = tfUsername.getText().toString().trim();
            String password = tfPassword.getText().toString().trim();
            userController.login(new LoginDto(username, password)).enqueue(new Callback<CMRespDto<User>>() {
                @Override
                public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                    CMRespDto<User> cm = response.body();
                    Headers header = response.headers();
                    String cookie = header.get("Set-Cookie");
                    cookie = cookie.substring(0,15);
                    if (cookie.equals("user=authorized")) {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        SessionUser.user = cm.getData();
                        SessionUser.sessionId = cookie;
                        Intent intent = new Intent(
                                mContext,
                                MainActivity.class
                        );
                        startActivity(intent);
                    } else if (cm.getCode()==-1){
                        Toast.makeText(getApplicationContext(), "ID 혹은 password가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CMRespDto<User>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });

        tvJoin.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    JoinActivity.class
            );
            mContext.startActivity(intent);
        });

        tvFindId.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    JoinActivity.class
            );
            mContext.startActivity(intent);
        });

        tvFindPw.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    FindPwActivity.class
            );
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