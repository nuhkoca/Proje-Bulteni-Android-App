package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.endroidteam.projebulteni.MainActivity;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.FontCache;
import com.endroidteam.projebulteni.others.ReadFileFromAsset;

public class AboutActivity extends AppCompatActivity {

    private TextView tvAboutContent, tvHeaderAbout;
    private Toolbar toolbarAbout;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvHeaderAbout = (TextView) findViewById(R.id.tvHeaderAbout);
        tvHeaderAbout.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        toolbarAbout = (Toolbar) findViewById(R.id.toolbarAbout);
        setSupportActionBar(toolbarAbout);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvAboutContent = (TextView) findViewById(R.id.tvAboutContent);
        tvAboutContent.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));

        tvAboutContent.setText(ReadFileFromAsset.getFile(mContext, "texts/aboutpb.txt"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent mainIntent = new Intent(AboutActivity.this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(AboutActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        super.onBackPressed();
    }
}
