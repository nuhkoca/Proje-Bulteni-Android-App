package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.FontCache;
import com.endroidteam.projebulteni.others.ReadFileFromAsset;

public class LicenseActivity extends AppCompatActivity {

    private TextView tvLicenseContent, tvHeaderLicense;
    private Toolbar toolbarLicense;
    private Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        tvHeaderLicense= (TextView) findViewById(R.id.tvHeaderLicense);
        tvHeaderLicense.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM,mContext));

        toolbarLicense= (Toolbar) findViewById(R.id.toolbarLicense);
        setSupportActionBar(toolbarLicense);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvLicenseContent= (TextView) findViewById(R.id.tvLicenseContent);
        tvLicenseContent.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR,mContext));

        tvLicenseContent.setText(ReadFileFromAsset.getFile(mContext,"texts/license.txt"));
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
