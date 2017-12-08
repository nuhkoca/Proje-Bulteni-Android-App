package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.endroidteam.projebulteni.MainActivity;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.adapters.DividerItemDecoration;
import com.endroidteam.projebulteni.adapters.SettingsAdapter;
import com.endroidteam.projebulteni.adapters.SimpleSectionedRecyclerViewAdapter;
import com.endroidteam.projebulteni.models.SettingsModel;
import com.endroidteam.projebulteni.others.FontCache;
import com.endroidteam.projebulteni.others.GotoURLs;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private List<SettingsModel> settingsModels;
    private RecyclerView rvSettings;
    private RecyclerView.Adapter mAdapter;
    private Toolbar toolbarSettings;
    private LinearLayoutManager linearLayoutManager;
    private TextView tvHeaderSettings;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbarSettings = (Toolbar) findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbarSettings);

        tvHeaderSettings = (TextView) findViewById(R.id.tvHeaderSettings);
        tvHeaderSettings.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvSettings = (RecyclerView) findViewById(R.id.rvSettings);
        if (rvSettings != null)
            rvSettings.setHasFixedSize(true);
        rvSettings.setItemAnimator(new DefaultItemAnimator());

        linearLayoutManager = new LinearLayoutManager(mContext);
        rvSettings.setLayoutManager(linearLayoutManager);
        rvSettings.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        settingsModels = new ArrayList<>();
        mAdapter = new SettingsAdapter(settingsModels);

        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, getString(R.string.licence)));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(1, getString(R.string.team_title)));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(3, getString(R.string.social_media_title)));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(6, getString(R.string.instant_notif_title)));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(7, getString(R.string.cache_title)));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(8, getString(R.string.update_title)));

        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));


        rvSettings.setAdapter(mSectionedAdapter);

        rvSettings.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvSettings, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                switch (position) {
                    case 1:
                        Intent licenseIntent = new Intent(SettingsActivity.this, LicenseActivity.class);
                        startActivity(licenseIntent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        break;
                    case 3:
                        Intent developerIntent = new Intent(SettingsActivity.this, DeveloperActivity.class);
                        startActivity(developerIntent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        break;
                    case 4:
                        Intent crewIntent = new Intent(SettingsActivity.this, CrewActivity.class);
                        startActivity(crewIntent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        break;
                    case 6:
                        GotoURLs.getFacebookPageURL(mContext);
                        break;

                    case 7:
                        GotoURLs.getInstagramPageURL(mContext);
                        break;
                    case 8:
                        GotoURLs.getTwitterPageURL(mContext);
                        break;
                    case 10:
                        Intent notIntent = new Intent(SettingsActivity.this, NotificationActivity.class);
                        startActivity(notIntent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        break;
                    case 12:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(R.string.warning);
                        builder.setMessage(R.string.cache_message);
                        builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Glide.get(mContext).clearDiskCache();

                                Toast.makeText(mContext, getString(R.string.cleaned_cache), Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton(R.string.no_button, null);
                        builder.show();
                        break;
                    case 14:
                        Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.update_link)));
                        startActivity(updateIntent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareSettingsData();

    }

    private void prepareSettingsData() {
        SettingsModel settingsModel = new SettingsModel(getString(R.string.licence));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.developer));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.crew));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.findusonfacebook));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.findusoninstagram));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.findusontwitter));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.notify_sett));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.clear_cache));
        settingsModels.add(settingsModel);

        settingsModel = new SettingsModel(getString(R.string.check_for_update));
        settingsModels.add(settingsModel);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        super.onBackPressed();
    }
}
