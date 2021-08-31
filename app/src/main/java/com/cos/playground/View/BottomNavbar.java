package com.cos.playground.View;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cos.playground.R;
import com.cos.playground.View.Community.CBoardListActivity;
import com.cos.playground.View.User.UserInfoActivity;
import com.cos.playground.View.auth.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavbar {

    public static void enableBottomNav(Context context, BottomNavigationView BNav) {
        BNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.bottom_home) {
                    Intent intent = new Intent(
                            context,
                            MainActivity.class
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else if (item.getItemId() == R.id.bottom_commu) {
                    Intent intent2 = new Intent(
                            context,
                            CBoardListActivity.class
                    );
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent2);
                } else if (item.getItemId() == R.id.bottom_hire) {
                    Intent intent3 = new Intent(
                            context,
                            MainActivity.class
                    );
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent3);
                } else if (item.getItemId() == R.id.bottom_mypage) {
                    Intent intent4 = new Intent(
                            context,
                            UserInfoActivity.class
                    );
                    intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent4);
                }

                return true;
            }
        });

    }


}
