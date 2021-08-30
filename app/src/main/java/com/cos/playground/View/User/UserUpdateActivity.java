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

            // 패스워드의 유효성 검사
            if (password.isEmpty()) {
                tfUPassword.setError("비밀번호는 필수입력 사항입니다.");
                focusView = tfUPassword;
                cancel = true;
            } else if (!isPasswordValid(password)) {
                tfUPassword.setError("6자 이상의 비밀번호를 입력하세요.");
                focusView = tfUPassword;
                cancel = true;
            }

            // 이메일의 유효성 검사
            if (email.isEmpty()) {
                tfUEmail.setError("이메일은 필수입력 사항입니다.");
                focusView = tfUEmail;
                cancel = true;
            } else if (!isEmailValid(email)) {
                tfUEmail.setError("이메일은 @가 포함되어야 합니다.");
                focusView = tfUEmail;
                cancel = true;
            }

            // 이름의 유효성 검사
            if (name.isEmpty()) {
                tfUName.setError("이름은 필수입력 사항입니다.");
                focusView = tfUName;
                cancel = true;
            }

            // 닉네임의 유효성 검사
            if (username.isEmpty()) {
                tfUUsername.setError("닉네임은 필수입력 사항입니다.");
                focusView = tfUUsername;
                cancel = true;
            }

            // 연락처의 유효성 검사
            if (phone.isEmpty()) {
                tfUPhone.setError("연락처는 필수입력 사항입니다.");
                focusView = tfUPhone;
                cancel = true;
            }

            // 경력의 유효성 검사
            if (career.isEmpty()) {
                tfUCareer.setError("경력은 필수입력 사항입니다.");
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
                        Toast.makeText(getApplicationContext(), "회원정보가 성공적으로 수정되었습니다.",
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
        // 세션데이터를 받아오므로 getintent 안씀
        tfUUsername.setText(SessionUser.user.getUsername());
        tfUPassword.setText("");
        tfUPhone.setText(SessionUser.user.getPhone());
        tfUName.setText(SessionUser.user.getName());
        tfUEmail.setText(SessionUser.user.getEmail());
        tfUCareer.setText(SessionUser.user.getCareer());
    }
}