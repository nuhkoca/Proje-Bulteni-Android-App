package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.FontCache;
import com.onesignal.OneSignal;

public class NotificationActivity extends AppCompatActivity {

    private TextView tvHeaderNotif, tvNotifText, tvNotifExp;
    private Toolbar toolbarNotif;
    private Context mContext = this;
    private SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbarNotif = (Toolbar) findViewById(R.id.toolbarNotif);
        setSupportActionBar(toolbarNotif);

        switchCompat = (SwitchCompat) findViewById(R.id.switch_compat);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvHeaderNotif = (TextView) findViewById(R.id.tvHeaderNotif);
        tvHeaderNotif.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        tvNotifText = (TextView) findViewById(R.id.tvNotifText);

        SharedPreferences preferences = getSharedPreferences("com.endroidteam.projebulteni", MODE_PRIVATE);
        boolean switchState = preferences.getBoolean("switch_state", true);

        if (switchState) {
            switchCompat.setChecked(true);
            tvNotifText.setText(getString(R.string.not_open));
            OneSignal.startInit(mContext).init();
            OneSignal.setSubscription(true);
        } else {
            switchCompat.setChecked(false);
            tvNotifText.setText(getString(R.string.not_close));
            OneSignal.startInit(mContext).init();
            OneSignal.setSubscription(false);
        }


        tvNotifText.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));

        tvNotifExp = (TextView) findViewById(R.id.tvNotifExp);
        tvNotifText.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("com.endroidteam.projebulteni", MODE_PRIVATE).edit();
                editor.putBoolean("switch_state", switchCompat.isChecked());
                editor.apply();

                if (switchCompat.isChecked()) {
                    tvNotifText.setText(getString(R.string.not_open));
                    OneSignal.startInit(mContext).init();
                    OneSignal.setSubscription(true);
                } else {
                    tvNotifText.setText(getString(R.string.not_close));
                    OneSignal.startInit(mContext).init();
                    OneSignal.setSubscription(false);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.pull_in_left,R.anim.pull_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left,R.anim.pull_out_right);
    }
}
