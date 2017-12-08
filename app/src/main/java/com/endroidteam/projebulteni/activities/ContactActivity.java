package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.endroidteam.projebulteni.MainActivity;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.CheckNetworkConnection;
import com.endroidteam.projebulteni.others.FontCache;
import com.endroidteam.projebulteni.retrofit.RegisterAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContactActivity extends AppCompatActivity {

    private Spinner spTitle;
    private Context mContext = this;
    private AppCompatEditText etEmail, etMessage;
    private TextInputLayout tilEmail, tilMessage;
    private Button btnSend;
    private RelativeLayout rlContact;
    private int heightDiff;
    public static final String ROOT_URL = "http://projebulteni.com/";
    private String selectedItem = "";
    private Toolbar toolbarContact;
    private TextView tvHeaderContact, tvFeedback;
    private ScrollView scrollView;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        scrollView = (ScrollView) findViewById(R.id.svContact);

        tvHeaderContact = (TextView) findViewById(R.id.tvHeaderContact);
        tvHeaderContact.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        toolbarContact = (Toolbar) findViewById(R.id.toolbarContact);
        setSupportActionBar(toolbarContact);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spTitle = (Spinner) findViewById(R.id.spTitle);

        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilMessage = (TextInputLayout) findViewById(R.id.tilMessage);
        etEmail = (AppCompatEditText) findViewById(R.id.etEmail);
        etMessage = (AppCompatEditText) findViewById(R.id.etMessage);

        etEmail.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));
        etMessage.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));

        tilMessage.setTypeface(FontCache.get(FontCache.ROBOTO_LIGHT, mContext));
        tilEmail.setTypeface(FontCache.get(FontCache.ROBOTO_LIGHT, mContext));

        tvFeedback = (TextView) findViewById(R.id.tvFeedback);
        tvFeedback.setTypeface(FontCache.get(FontCache.ROBOTO_LIGHT, mContext));

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        rlContact = (RelativeLayout) findViewById(R.id.rlContact);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseKeyboard();
                if (!validateConnection()) {
                    return;
                }
                submitForm();
            }
        });

        rlContact.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                heightDiff = rlContact.getRootView().getHeight() - rlContact.getWidth();
            }
        });

        rlContact.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CloseKeyboard();
                return false;
            }
        });

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.spinner_advice));
        titles.add(getString(R.string.spinner_compliant));
        titles.add(getString(R.string.spinner_thanks));
        titles.add(getString(R.string.spinner_help));
        titles.add(getString(R.string.spinner_other));

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext, titles);
        spTitle.setAdapter(customSpinnerAdapter);
        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.scrollTo(100, scrollView.getBottom());
                scrollView.fullScroll(View.FOCUS_DOWN);

                return false;
            }
        });
    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(mContext);
            txt.setPadding(16, 16, 16, 16);
            txt.setGravity(Gravity.CENTER);
            txt.setTextSize(16);
            txt.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(position));
            txt.setTextColor(ContextCompat.getColor(mContext, R.color.material_text_color));
            return txt;
        }

        @Override
        public View getDropDownView(int i, View convertView, ViewGroup parent) {
            TextView txt = new TextView(mContext);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, mContext));
            txt.setText(asr.get(i));
            txt.setTextColor(ContextCompat.getColor(mContext, R.color.material_text_color));
            return txt;
        }
    }

    private void CloseKeyboard() {
        if (heightDiff > 100) {
            View view = getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean IsValidEmail(String target) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(target).matches();
    }

    private void InsertMail() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        RegisterAPI registerAPI = restAdapter.create(RegisterAPI.class);

        registerAPI.insertUser(
                etEmail.getText().toString(),
                selectedItem,
                etMessage.getText().toString(),
                currentDateandTime,

                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader bufferedReader = null;

                        try {
                            bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(R.string.send_message_title);
                        builder.setMessage(R.string.send_message_body);
                        builder.setPositiveButton(R.string.ok_button, null);
                        builder.show();

                        etEmail.setText("");
                        etMessage.setText("");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(R.string.send_message_title_error);
                        builder.setMessage(R.string.send_message_body_error);
                        builder.setPositiveButton(R.string.ok_button, null);
                        builder.show();
                    }
                }
        );
    }

    private void submitForm() {
        if (!validateEmail()) {
            return;
        }
        if (!validateMessage()) {
            return;
        }

        InsertMail();
    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty() || !IsValidEmail(email)) {
            Snackbar mSnackbar = Snackbar.make(rlContact, R.string.err_msg_email, Snackbar.LENGTH_LONG)
                    .setAction(R.string.ok_button, null);
            mSnackbar.setActionTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

            mSnackbar.show();

            etEmail.requestFocus();
            return false;
        } else {
        }

        return true;
    }

    private boolean validateMessage() {
        if (etMessage.getText().length() == 0) {
            Snackbar mSnackbar = Snackbar.make(rlContact, R.string.err_msg_message, Snackbar.LENGTH_LONG)
                    .setAction(R.string.ok_button, null);
            mSnackbar.setActionTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

            mSnackbar.show();
            etMessage.requestFocus();
            return false;
        } else {

        }

        return true;
    }

    private boolean validateConnection() {
        if (!CheckNetworkConnection.CheckConn(mContext, rlContact)) {
            CheckNetworkConnection.CheckConn(mContext, rlContact);
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent mainIntent = new Intent(ContactActivity.this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(ContactActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);

        super.onBackPressed();
    }
}
