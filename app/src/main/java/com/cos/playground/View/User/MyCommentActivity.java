package com.cos.playground.View.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cos.playground.Controller.CommentController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.Comment;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.cos.playground.View.Community.adapter.CommentListAdapter;
import com.cos.playground.View.Community.adapter.MyCommentAdapter;
import com.cos.playground.config.SessionUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCommentActivity extends AppCompatActivity {
    
    private MyCommentActivity mContext = MyCommentActivity.this;
    private static final String TAG = "MyComment";

    private static final int ACTIVITY_NUM = 1;

    private MyCommentAdapter myCommentAdapter;
    private RecyclerView rvMycommentList;
    private RecyclerView.LayoutManager rvLayoutManager;

    private CommentController commentController;
    private UserController userController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);


        init();
        initLr();
        initAdapter();
        initSetting();
        initData();

    }

    public void init(){
        rvMycommentList = findViewById(R.id.rvMyCommentList);
        commentController = new CommentController();
        userController = new UserController();
    }

    public void initLr(){}

    public void initAdapter(){
        rvLayoutManager = new LinearLayoutManager(
                mContext, RecyclerView.VERTICAL,false);
        rvMycommentList.setLayoutManager(rvLayoutManager);

        myCommentAdapter = new MyCommentAdapter(mContext);
        rvMycommentList.setAdapter(myCommentAdapter);
    }

    public void initData(){
        userController.getMyComments(SessionUser.user.getId()).enqueue(new Callback<CMRespDto<List<Comment>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Comment>>> call, Response<CMRespDto<List<Comment>>> response) {
                CMRespDto<List<Comment>> cm = response.body();
                myCommentAdapter.addItems(cm.getData());
            }

            @Override
            public void onFailure(Call<CMRespDto<List<Comment>>> call, Throwable t) {
                t.printStackTrace();
            }
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