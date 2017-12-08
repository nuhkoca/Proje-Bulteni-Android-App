package com.endroidteam.projebulteni.activities;

import android.net.Uri;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.endroidteam.projebulteni.MainActivity;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.json.Config;
import com.endroidteam.projebulteni.json.ProjectAdapter;
import com.endroidteam.projebulteni.json.ProjectModel;
import com.endroidteam.projebulteni.others.CheckNetworkConnection;
import com.endroidteam.projebulteni.others.FontCache;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ProjectDetailActivity extends AppCompatActivity {

    private List<ProjectModel> projectModels;

    private RecyclerView rvProjectDetail;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private JSONObject json;
    private Context mContext = this;
    private Toolbar toolbarProjectDetail;
    private TextView tvHeaderProjectDetail;
    private Intent intent;
    private String getProjectID;
    private String getExtraProjectID;

    private RequestQueue requestQueue;

    private BottomSheetLayout bottomSheetLayout;
    private Intent shareIntent;
    private IntentPickerSheetView intentPickerSheetView;
    private RelativeLayout rlRoot;

    private ImageView ivNoConnProject;
    private TextView tvNoConnProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        rlRoot = (RelativeLayout) findViewById(R.id.rlRoot);
        ivNoConnProject = (ImageView) findViewById(R.id.ivNoConnProject);
        tvNoConnProject = (TextView) findViewById(R.id.tvNoConnProject);

        toolbarProjectDetail = (Toolbar) findViewById(R.id.toolbarprojectDetail);
        setSupportActionBar(toolbarProjectDetail);

        tvHeaderProjectDetail = (TextView) findViewById(R.id.tvHeaderProjectDetail);
        tvHeaderProjectDetail.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvProjectDetail = (RecyclerView) findViewById(R.id.rvProjectDetail);
        if (rvProjectDetail != null)
            rvProjectDetail.setHasFixedSize(true);
        rvProjectDetail.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        mLinearLayoutManager = new LinearLayoutManager(this);
        rvProjectDetail.setLayoutManager(mLinearLayoutManager);

        projectModels = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        mAdapter = new ProjectAdapter(projectModels, this);
        rvProjectDetail.setAdapter(new ScaleInAnimationAdapter(mAdapter));

        intent = getIntent();

        getProjectID = intent.getStringExtra("projectid");

        if (CheckNetworkConnection.CheckConn(mContext, rlRoot)) {
            rvProjectDetail.setVisibility(View.VISIBLE);
            ivNoConnProject.setVisibility(View.GONE);
            tvNoConnProject.setVisibility(View.GONE);

            getData();
        } else {
            rvProjectDetail.setVisibility(View.GONE);
            ivNoConnProject.setVisibility(View.VISIBLE);
            tvNoConnProject.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!CheckNetworkConnection.CheckConn(mContext, rlRoot)) {
            getMenuInflater().inflate(R.menu.detail_menu, menu);
        } else {
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent mainIntent = new Intent(ProjectDetailActivity.this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        }
        if (id == R.id.refresh_detail) {
            if (!CheckNetworkConnection.CheckConn(mContext, rlRoot)) {
                Toast.makeText(mContext, getString(R.string.noproject_openinternet), Toast.LENGTH_LONG).show();
                rvProjectDetail.setVisibility(View.GONE);
                ivNoConnProject.setVisibility(View.VISIBLE);
                tvNoConnProject.setVisibility(View.VISIBLE);

                projectModels.clear();
                mAdapter.notifyDataSetChanged();
            } else {

                rvProjectDetail.setVisibility(View.VISIBLE);
                ivNoConnProject.setVisibility(View.GONE);
                tvNoConnProject.setVisibility(View.GONE);

                projectModels.clear();
                mAdapter.notifyDataSetChanged();

                getData();

                toolbarProjectDetail.getMenu().findItem(R.id.refresh_detail).setVisible(false);
                toolbarProjectDetail.getMenu().findItem(R.id.bug_report).setVisible(false);
            }

        }
        if (id == R.id.bug_report) {
            Intent contactIntent = new Intent(ProjectDetailActivity.this, ContactActivity.class);
            contactIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(contactIntent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(ProjectDetailActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        super.onBackPressed();
    }

    public void CreateBottomSheet(String extra) {
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);

        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, extra);
        shareIntent.setType("text/plain");

        intentPickerSheetView = new IntentPickerSheetView(ProjectDetailActivity.this, shareIntent, "Şununla paylaş...", new IntentPickerSheetView.OnIntentPickedListener() {
            @Override
            public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                bottomSheetLayout.dismissSheet();
                startActivity(activityInfo.getConcreteIntent(shareIntent));
            }
        });

        intentPickerSheetView.setFilter(new IntentPickerSheetView.Filter() {
            @Override
            public boolean include(IntentPickerSheetView.ActivityInfo info) {
                return !info.componentName.getPackageName().startsWith("com.android");
            }
        });

        intentPickerSheetView.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
            @Override
            public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
                return rhs.label.compareTo(lhs.label);
            }
        });

        bottomSheetLayout.showWithSheetView(intentPickerSheetView);
    }

    public void ShowApplicationDialog(final String address, final Context context) {
        final String getEmailAddress = address;

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.application_title);
        builder.setMessage(getString(R.string.application_msg) + "\n\n" + address);
        builder.setPositiveButton(R.string.copy_to_clipboard_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text", getEmailAddress);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, getString(R.string.copied), Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton(R.string.send_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + address));
                startActivity(mailIntent);
            }
        });
        builder.show();
    }

    private void getData() {
        String id = getProjectID;

        String url = Config.DATA_URL + id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(mContext, getString(R.string.noproject_openinternet), Toast.LENGTH_LONG).show();

                }
            }
        });

        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ProjectModel project = new ProjectModel();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = jsonArray.getJSONObject(0);

            project.setImage(collegeData.getString(Config.TAG_IMAGE_URL));
            project.setTitle(collegeData.getString(Config.TAG_TITLE));
            project.setSender(collegeData.getString(Config.TAG_SENDER));
            project.setContent(collegeData.getString(Config.TAG_CONTENT));
            project.setDate(collegeData.getString(Config.TAG_DATE));
            project.setLoc(collegeData.getString(Config.TAG_LOC));
            project.setType(collegeData.getString(Config.TAG_TYPE));
            project.setApp_type(collegeData.getString(Config.TAG_APP_TYPE));
            project.setApp_address(collegeData.getString(Config.TAG_APP_ADDRESS));
            project.setReflink(collegeData.getString(Config.TAG_REF_LINK));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        projectModels.add(project);
        mAdapter.notifyDataSetChanged();
    }
}

