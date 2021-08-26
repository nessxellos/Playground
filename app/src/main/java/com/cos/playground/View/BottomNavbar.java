package com.cos.playground.View;

import android.content.Context;
import android.content.Intent;

import com.cos.playground.R;
import com.cos.playground.View.Community.CBoardDetailActivity;
import com.cos.playground.View.Community.RBoardDetailActivity;
import com.cos.playground.View.User.UserInfoActivity;
import com.cos.playground.View.auth.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavbar {

    public void enableBottomNav(Context context, BottomNavigationView BNav) {


        BNav.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.bottom_home) {
                    Intent intent = new Intent(
                            context,
                            MainActivity.class
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                } else if (item.getItemId() == R.id.bottom_commu) {
                    Intent intent = new Intent(
                            context,
                            CBoardDetailActivity.class
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                } else if (item.getItemId() == R.id.bottom_hire) {
                    Intent intent = new Intent(
                            context,
                            RBoardDetailActivity.class
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                } else if (item.getItemId() == R.id.bottom_mypage) {
                    Intent intent = new Intent(
                            context,
                            UserInfoActivity.class
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                }

                return true;
        });
    }



}
