package com.cos.playground.View.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.airbnb.lottie.L;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.UserController;
import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.cos.playground.View.BottomNavbar;
import com.cos.playground.View.Community.adapter.MyPostAdapter;
import com.cos.playground.config.SessionUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostActivity extends AppCompatActivity {

    private static final String TAG = "MyPost";
    private static final int ACTIVITY_NUM =1;
    private MyPostActivity mContext = MyPostActivity.this;

    private RecyclerView rvMyPost;
    private RecyclerView.LayoutManager rvLayoutManager;

    private MyPostAdapter myPostAdapter;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        init();
        initAdapter();
        initData();
        initSetting();
    }

    public void init(){
        rvMyPost = findViewById(R.id.rvMyPostList);
        userController = new UserController();
    }

    public void initAdapter(){
        rvLayoutManager = new LinearLayoutManager(
                mContext, RecyclerView.VERTICAL, false);
        rvMyPost.setLayoutManager(rvLayoutManager);

        myPostAdapter = new MyPostAdapter(mContext);
        rvMyPost.setAdapter(myPostAdapter);
    }

    public void initData(){
        userController.myPostById(SessionUser.user.getId()).enqueue(new Callback<CMRespDto<List<CBoard>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<CBoard>>> call, Response<CMRespDto<List<CBoard>>> response) {
                CMRespDto<List<CBoard>> cm = response.body();
                myPostAdapter.addItems(cm.getData());
            }

            @Override
            public void onFailure(Call<CMRespDto<List<CBoard>>> call, Throwable t) {
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