package com.cos.playground.View.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.playground.Controller.BoardController;
import com.cos.playground.Controller.DTO.BoardWriteDto;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.cos.playground.View.auth.MainActivity;
import com.cos.playground.config.SessionUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CBoardWriteActivity extends AppCompatActivity {

    private static final String TAG = "CBoardWrite";
    private CBoardWriteActivity mContext = CBoardWriteActivity.this;

    private MaterialButton btnWrite;
    private Spinner spinner;
    private TextInputEditText tfTitle, tfContent;
    private TextView tvSelectCategory;

    private BoardController boardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cboard_write);

        init();
        initLr();
    }

    public void init(){
        boardController = new BoardController();
        btnWrite = findViewById(R.id.btnWrite);
        tfContent = findViewById(R.id.tfContent);
        tfTitle = findViewById(R.id.tfTitle);
        spinner = findViewById(R.id.spinner);
        tvSelectCategory = findViewById(R.id.tvSelectCategory);
    }

    public void initLr(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvSelectCategory.setText((String)spinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnWrite.setOnClickListener(v->{
            String title = tfTitle.getText().toString().trim();
            String content = tfContent.getText().toString().trim();
            String category = tvSelectCategory.getText().toString().trim();
            Log.d(TAG, "initLr: "+category);
            Log.d(TAG, "initLr: "+SessionUser.user.getId());

            boolean cancel = false;
            View focusView = null;

            if (content.isEmpty()) {
                tfContent.setError("내용을 입력해주세요.");
                focusView = tfContent;
                cancel = true;
            }
            if (title.isEmpty()) {
                tfTitle.setError("제목을 입력해주세요.");
                focusView = tfTitle;
                cancel = true;
            }
//            Log.d(TAG, "initLr: "+content+title+category);
            if (cancel) {
                focusView.requestFocus();
            } else {
                boardController.write(new BoardWriteDto(title, content, category, SessionUser.user.getId())).enqueue(new Callback<CMRespDto<CBoard>>() {
                    @Override
                    public void onResponse(Call<CMRespDto<CBoard>> call, Response<CMRespDto<CBoard>> response) {
                        CMRespDto<CBoard> cm = response.body();
                        if(cm.getCode()==1){
                            Toast.makeText(getApplicationContext(), "게시글 작성 완료", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(
                                    mContext,
                                    CBoardListActivity.class
                            );
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CMRespDto<CBoard>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });




    }
}