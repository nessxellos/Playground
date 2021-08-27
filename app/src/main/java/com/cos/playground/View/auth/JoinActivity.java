package com.cos.playground.View.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private JoinActivity mContext = JoinActivity.this;

    private TextView join_email, join_password, join_name;
    private Button join_button;
    private UserController userController;
    private BottomNavigationView BNav;
    private BottomNavbar bottomNavbar = new BottomNavbar();

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();
        initLr();
    }

    public void init(){
        userController = new UserController();
        join_button = findViewById(R.id.join_button);
        join_email = findViewById(R.id.join_email);
        join_name = findViewById(R.id.join_name);
        join_password = findViewById(R.id.join_password);
        BNav = findViewById(R.id.bottomNavigation);
    }

    public void initLr(){
        join_button.setOnClickListener(v->{
            String password = join_password.getText().toString().trim();
            String email = join_email.getText().toString().trim();
            String name = join_name.getText().toString().trim();

                    boolean cancel = false;
                    View focusView = null;

                    // 패스워드의 유효성 검사
                    if (password.isEmpty()) {
                        join_password.setError("비밀번호를 입력해주세요.");
                        focusView = join_password;
                        cancel = true;
                    } else if (!isPasswordValid(password)) {
                        join_password.setError("6자 이상의 비밀번호를 입력하세요.");
                        focusView = join_password;
                        cancel = true;
                    }

                    // 이메일의 유효성 검사
                    if (email.isEmpty()) {
                        join_email.setError("이메일을 입력해주세요.");
                        focusView = join_email;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        join_email.setError("이메일은 @가 포함되어야 합니다.");
                        focusView = join_email;
                        cancel = true;
                    }

                    // 이름의 유효성 검사
                    if (name.isEmpty()) {
                        join_name.setError("이름을 입력해주세요.");
                        focusView = join_name;
                        cancel = true;
                    }

                    if (cancel) {
                        focusView.requestFocus();
                    } else {
                        userController.join(new User(password, name, email)).enqueue(new Callback<CMRespDto<User>>() {
                            @Override
                            public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                                CMRespDto<User> cm = response.body();
                                if(cm.getCode()==1){
                                    Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(
                                            mContext,
                                            MainActivity.class
                                    );
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }

                            }
                            @Override
                            public void onFailure(Call<CMRespDto<User>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

        });

        //bottomNavbar.enableBottomNav(mContext, BNav);

    }
}