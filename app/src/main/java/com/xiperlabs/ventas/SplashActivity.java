package com.xiperlabs.ventas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        bindLogo();
        searchData();
    }

    private void searchData() {
        /*executors.diskIO().execute(() -> db.runInTransaction(() -> {
            User user = db.userDao().getUser();
            if(user == null) {
                openPage = new Intent(Splash.this, LoginActivity.class);

            } else {
                ((OperadoresApp) getApplication()).getToken();
                openPage = new Intent(Splash.this, HomeActivity.class);
            }
        }));
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            executors = null;
            db = null;
            openPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(openPage);
            finish();
        }, 3000);*/
    }

    private void bindLogo() {
        final ImageView splash = findViewById(R.id.splash);
        final AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(700);
        final AlphaAnimation animation2 = new AlphaAnimation(1.0f, 0.2f);
        animation2.setDuration(700);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                splash.startAnimation(animation2);
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {}
            @Override
            public void onAnimationStart(Animation arg0) {}
        });
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                splash.startAnimation(animation1);
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {}
            @Override
            public void onAnimationStart(Animation arg0) {}
        });

        splash.startAnimation(animation1);
    }

}
