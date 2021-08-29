package com.cos.playground.View.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        btnUUser.setOnClickListener(v->{
            JoinDto joinDto = new JoinDto(
                    tfUUsername.getText().toString(),
                    tfUPassword.getText().toString(),
                    tfUName.getText().toString(),
                    tfUPhone.getText().toString(),
                    tfUCareer.getText().toString(),
                    tfUEmail.getText().toString()
            );
        userController.update(joinDto.getUsername(), joinDto).enqueue(new Callback<CMRespDto<User>>() {
            @Override
            public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                CMRespDto<User> cm = response.body();
                if(cm.getCode()==1){
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