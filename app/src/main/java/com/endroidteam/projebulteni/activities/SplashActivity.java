package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.endroidteam.projebulteni.MainActivity;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.FontCache;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.onesignal.OneSignal;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    private Context mContext = this;

    private  String[] cities = new String[]{"https://upload.wikimedia.org/wikipedia/commons/d/dc/Berliner_Dom_vor_Sonnenuntergang.jpg",
            "https://upload.wikimedia.org/wikipedia/en/4/49/Trevi_Fountain_Rome_(capital_edit).jpg",
            "https://upload.wikimedia.org/wikipedia/commons/b/b8/Istanbul_(8274724020).jpg",
            "https://upload.wikimedia.org/wikipedia/commons/a/a6/Sunset_over_the_base_of_the_Eiffel,_Paris_2007.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/e/e1/Palau_Nacional,_Barcelona.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/8/8f/Nationaltheatret_Oslo_Front_at_Night.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/0/03/House_of_Blackheads_at_Dusk_3,_Riga,_Latvia_-_Diliff.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/8/84/Beautiful_spring_sunset_in_Vilnius_Lithuania.jpg",
            "https://static.pexels.com/photos/7041/pexels-photo.jpeg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CheckforPush();

        TextView tvSplash = (TextView) findViewById(R.id.tvSplash);
        assert tvSplash != null;
        tvSplash.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));

        KenBurnsView kenBurnsView = (KenBurnsView) findViewById(R.id.kbvImage);

        Random random = new Random();
        int getRandom = random.nextInt((cities.length));

        assert kenBurnsView != null;
        Glide.with(this).load(cities[getRandom]).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().centerCrop().into(kenBurnsView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 7000);
    }

    private void CheckforPush(){
        SharedPreferences preferences = getSharedPreferences("com.endroidteam.projebulteni", MODE_PRIVATE);
        boolean switchState = preferences.getBoolean("switch_state", true);

        if (switchState){
            OneSignal.startInit(this).init();
            OneSignal.setSubscription(true);
        }else {
            OneSignal.startInit(this).init();
            OneSignal.setSubscription(false);
        }
    }
}
