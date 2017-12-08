package com.endroidteam.projebulteni;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.endroidteam.projebulteni.activities.AboutActivity;
import com.endroidteam.projebulteni.activities.ContactActivity;
import com.endroidteam.projebulteni.activities.SettingsActivity;
import com.endroidteam.projebulteni.fragments.ListProjectFragment;
import com.endroidteam.projebulteni.fragments.NoConnectionFragment;
import com.endroidteam.projebulteni.fragments.RootFragment;
import com.endroidteam.projebulteni.others.CheckNetworkConnection;
import com.endroidteam.projebulteni.others.FontCache;

public class MainActivity extends AppCompatActivity {

    private Context mContext = this;
    private TabLayout tabLayout;
    private ImageView header;
    private CoordinatorLayout clMain;
    private boolean doubleBackToExitPressedOnce = false;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout.LayoutParams params;

    private SharedPreferences sharedPreferencesProject;

    private String[] bgs = new String[]{"https://snap-photos.s3.amazonaws.com/img-thumbs/960w/5D32YBOFBF.jpg",
            "https://pixabay.com/static/uploads/photo/2015/12/12/15/24/amsterdam-1089646_960_720.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/b/bb/Plaza_Mayor_de_Madrid_02.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/5/53/Colosseum_in_Rome,_Italy_-_April_2007.jpg",
            "https://i.ytimg.com/vi/8cP0qR5-t4M/maxresdefault.jpg",
            "https://pixabay.com/static/uploads/photo/2015/12/09/09/47/istanbul-1084458_960_720.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Vienna_Skyline.jpg/1280px-Vienna_Skyline.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/f/f9/Prague_sunrise_(8099151633).jpg",
            "https://static.pexels.com/photos/14621/Warsaw-at-night-free-license-CC0.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/7/74/Berlin_Brandenburger_Tor_Nacht.jpg"};

    private boolean fragState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferencesProject = getSharedPreferences("com.endroidteam.projebulteni", MODE_PRIVATE);
        fragState = sharedPreferencesProject.getBoolean("frag_state", true);


        TextView tvHeader = (TextView) findViewById(R.id.tvHeader);
        assert tvHeader != null;
        tvHeader.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        clMain = (CoordinatorLayout) findViewById(R.id.clMain);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        if (collapsingToolbarLayout != null)
            params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        header = (ImageView) findViewById(R.id.header);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SetupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null)
            tabLayout.setupWithViewPager(viewPager);
        SetupTabTexts();

        LoadHeaderImage();

        if (!CheckNetworkConnection.CheckConn(mContext, clMain)) {
            CheckNetworkConnection.CheckConn(mContext, clMain);

            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
            collapsingToolbarLayout.setLayoutParams(params);
        } else {
            if (fragState){
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                collapsingToolbarLayout.setLayoutParams(params);
           }else{
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                collapsingToolbarLayout.setLayoutParams(params);
            }
        }
    }

    private void SetupTabTexts() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getString(R.string.uptodate_projects));
        tabOne.setTypeface(FontCache.get(FontCache.ROBOTO_BOLD, mContext));
        //noinspection ConstantConditions
        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    private void SetupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RootFragment(), getString(R.string.projects));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void LoadHeaderImage() {
        Random random = new Random();
        int getRandom = random.nextInt(bgs.length);

        Glide.with(this).load(bgs[getRandom]).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().centerCrop().into(new GlideDrawableImageViewTarget(header) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.refresh_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
        }
        if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(R.string.exit_title);
            builder.setMessage(R.string.exit_body);
            builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton(R.string.no_button, null);
            builder.show();
        }
        if (id == R.id.refresh) {
            if (!CheckNetworkConnection.CheckConn(mContext, clMain)) {
                CheckNetworkConnection.CheckConn(mContext, clMain);

                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                collapsingToolbarLayout.setLayoutParams(params);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();

                fragmentTransaction.replace(R.id.root_frame, new NoConnectionFragment())
                        .commit();
            } else {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();

                fragmentTransaction.replace(R.id.root_frame, new ListProjectFragment())
                        .commit();

                sharedPreferencesProject = getSharedPreferences("com.endroidteam.projebulteni", MODE_PRIVATE);
                fragState = sharedPreferencesProject.getBoolean("frag_state", true);

                if (fragState) {
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                    collapsingToolbarLayout.setLayoutParams(params);
                } else {
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                    collapsingToolbarLayout.setLayoutParams(params);
                }

            }
        }

        if (id == R.id.feedback) {
            Intent feedbackIntent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(feedbackIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
        }
        if (id == R.id.exit) {
            exitDialog();
        }
        if (id == R.id.about) {
            Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(aboutIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
        }

        return super.onOptionsItemSelected(item);
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.exit_title);
        builder.setMessage(R.string.exit_body);
        builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton(R.string.no_button, null);
        builder.show();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.back_pressed_message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}