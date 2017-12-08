package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.FontCache;
import com.endroidteam.projebulteni.others.GotoURLs;

public class DeveloperActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvHeaderDeveloper, tvName, tvProfession, tvLinkedin, tvCity, tvVersion;
    private Toolbar toolbarDeveloper;
    private Context mContext = this;
    private PackageInfo mPackageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        toolbarDeveloper = (Toolbar) findViewById(R.id.toolbarDeveloper);
        setSupportActionBar(toolbarDeveloper);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvHeaderDeveloper = (TextView) findViewById(R.id.tvHeaderDeveloper);
        tvName = (TextView) findViewById(R.id.tvName);
        tvProfession = (TextView) findViewById(R.id.tvProfession);
        tvLinkedin = (TextView) findViewById(R.id.tvLinkedin);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvVersion = (TextView) findViewById(R.id.tvVersion);

        tvHeaderDeveloper.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));
        tvName.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));
        tvProfession.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));
        tvLinkedin.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));
        tvCity.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));
        tvVersion.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));

        try {
            mPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tvVersion.setText(String.format("v %s", mPackageInfo.versionName));

        tvLinkedin.setOnClickListener(this);
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
    public void onClick(View v) {
        GotoURLs.getLinkedinPageURL(mContext);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left,R.anim.pull_out_right);
    }
}
