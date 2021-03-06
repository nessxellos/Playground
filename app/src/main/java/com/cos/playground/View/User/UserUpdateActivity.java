package com.cos.playground.View.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.JoinDto;
import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.auth.MainActivity;
import com.cos.playground.config.SessionUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateActivity extends AppCompatActivity {

    private static final String TAG = "UserUpdate";
    private UserUpdateActivity mContext = UserUpdateActivity.this;

    private Button btnUUser;
    private TextInputEditText tfUCareer, tfUEmail, tfUPassword, tfUName, tfUUsername, tfUPhone;

    private UserController userController;
    private User user;

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        init();
        initLr();
        initData();
    }

    public void init(){
        userController = new UserController();
        btnUUser = findViewById(R.id.btnUUser);

        tfUCareer = findViewById(R.id.tfUCareer);
        tfUEmail = findViewById(R.id.tfUEmail);
        tfUName = findViewById(R.id.tfUName);
        tfUPassword = findViewById(R.id.tfUPassword);
        tfUPhone = findViewById(R.id.tfUPhone);
        tfUUsername = findViewById(R.id.tfUUsername);
    }

    public void initLr(){
        btnUUser.setOnClickListener(v-> {
            String username = tfUUsername.getText().toString();
            String password = tfUPassword.getText().toString();
            String name = tfUName.getText().toString();
            String phone = tfUPhone.getText().toString();
            String career = tfUCareer.getText().toString();
            String email = tfUEmail.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // ??????????????? ????????? ??????
            if (password.isEmpty()) {
                tfUPassword.setError("??????????????? ???????????? ???????????????.");
                focusView = tfUPassword;
                cancel = true;
            } else if (!isPasswordValid(password)) {
                tfUPassword.setError("6??? ????????? ??????????????? ???????????????.");
                focusView = tfUPassword;
                cancel = true;
            }

            // ???????????? ????????? ??????
            if (email.isEmpty()) {
                tfUEmail.setError("???????????? ???????????? ???????????????.");
                focusView = tfUEmail;
                cancel = true;
            } else if (!isEmailValid(email)) {
                tfUEmail.setError("???????????? @??? ??????????????? ?????????.");
                focusView = tfUEmail;
                cancel = true;
            }

            // ????????? ????????? ??????
            if (name.isEmpty()) {
                tfUName.setError("????????? ???????????? ???????????????.");
                focusView = tfUName;
                cancel = true;
            }

            // ???????????? ????????? ??????
            if (username.isEmpty()) {
                tfUUsername.setError("???????????? ???????????? ???????????????.");
                focusView = tfUUsername;
                cancel = true;
            }

            // ???????????? ????????? ??????
            if (phone.isEmpty()) {
                tfUPhone.setError("???????????? ???????????? ???????????????.");
                focusView = tfUPhone;
                cancel = true;
            }

            // ????????? ????????? ??????
            if (career.isEmpty()) {
                tfUCareer.setError("????????? ???????????? ???????????????.");
                focusView = tfUCareer;
                cancel = true;
            }
            JoinDto joinDto = new JoinDto(username, password, name, phone, career, email);
            if (cancel) {
                focusView.requestFocus();
            } else {
                userController.update(joinDto).enqueue(new Callback<CMRespDto<User>>() {
                @Override
                public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                    CMRespDto<User> cm = response.body();
                    if (cm.getCode() == 1) {
                        Toast.makeText(getApplicationContext(), "??????????????? ??????????????? ?????????????????????.",
                                Toast.LENGTH_SHORT).show();
                        SessionUser.user = cm.getData();
                        Intent intent = new Intent(
                                mContext,
                                UserInfoActivity.class
                        );
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("id", user.getId());
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<CMRespDto<User>> call, Throwable t) {

                }
            });
        }
        });
    }

    public void initData() {
        // ?????????????????? ??????????????? getintent ??????
        tfUUsername.setText(SessionUser.user.getUsername());
        tfUPassword.setText("");
        tfUPhone.setText(SessionUser.user.getPhone());
        tfUName.setText(SessionUser.user.getName());
        tfUEmail.setText(SessionUser.user.getEmail());
        tfUCareer.setText(SessionUser.user.getCareer());
    }
}