package com.cos.playground.View.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.JoinDto;
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

    private TextView tfEmail, tfPassword, tfName, tfUsername, tfPhone, tfCareer;
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
        tfEmail = findViewById(R.id.tfEmail);
        tfName = findViewById(R.id.tfName);
        tfPassword = findViewById(R.id.tfPassword);
        tfCareer = findViewById(R.id.tfCareer);
        tfUsername = findViewById(R.id.tfUsername);
        tfPhone = findViewById(R.id.tfPhone);

        BNav = findViewById(R.id.bottomNavigation);
    }

    public void initLr(){
        join_button.setOnClickListener(v->{
            String username = tfUsername.getText().toString().trim();
            String password = tfPassword.getText().toString().trim();
            String name = tfName.getText().toString().trim();
            String phone = tfPhone.getText().toString().trim();
            String career = tfCareer.getText().toString().trim();
            String email = tfEmail.getText().toString().trim();

                    boolean cancel = false;
                    View focusView = null;

                    // ??????????????? ????????? ??????
                    if (password.isEmpty()) {
                        tfPassword.setError("??????????????? ???????????? ???????????????.");
                        focusView = tfPassword;
                        cancel = true;
                    } else if (!isPasswordValid(password)) {
                        tfPassword.setError("6??? ????????? ??????????????? ???????????????.");
                        focusView = tfPassword;
                        cancel = true;
                    }

                    // ???????????? ????????? ??????
                    if (email.isEmpty()) {
                        tfEmail.setError("???????????? ???????????? ???????????????.");
                        focusView = tfEmail;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        tfEmail.setError("???????????? @??? ??????????????? ?????????.");
                        focusView = tfEmail;
                        cancel = true;
                    }

                    // ????????? ????????? ??????
                    if (name.isEmpty()) {
                        tfName.setError("????????? ???????????? ???????????????.");
                        focusView = tfName;
                        cancel = true;
                    }

                    // ???????????? ????????? ??????
                    if (username.isEmpty()) {
                        tfUsername.setError("???????????? ???????????? ???????????????.");
                        focusView = tfUsername;
                        cancel = true;
                    }

                    // ???????????? ????????? ??????
                    if (phone.isEmpty()) {
                        tfPhone.setError("???????????? ???????????? ???????????????.");
                        focusView = tfPhone;
                        cancel = true;
                    }

                    // ????????? ????????? ??????
                    if (career.isEmpty()) {
                        tfCareer.setError("????????? ???????????? ???????????????.");
                        focusView = tfCareer;
                        cancel = true;
                    }

                    if (cancel) {
                        focusView.requestFocus();
                    } else {
                        userController.join(new JoinDto(username, password, name, phone, career, email)).enqueue(new Callback<CMRespDto<User>>() {
                            @Override
                            public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                                CMRespDto<User> cm = response.body();
                                if(cm.getCode()==1){
                                    Toast.makeText(getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
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