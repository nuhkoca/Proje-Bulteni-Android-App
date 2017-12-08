package com.endroidteam.projebulteni.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.adapters.CrewAdapter;
import com.endroidteam.projebulteni.adapters.DividerItemDecoration;
import com.endroidteam.projebulteni.adapters.SimpleSectionedCrewRecyclerViewAdapter;
import com.endroidteam.projebulteni.models.CrewModel;
import com.endroidteam.projebulteni.others.FontCache;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class CrewActivity extends AppCompatActivity {

    private List<CrewModel> crewModels = new ArrayList<>();
    private RecyclerView rvCrew;
    private RecyclerView.Adapter mAdapter;
    private Toolbar toolbarCrew;
    private LinearLayoutManager linearLayoutManager;
    private TextView tvHeaderCrew;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew);

        toolbarCrew = (Toolbar) findViewById(R.id.toolbarCrew);
        setSupportActionBar(toolbarCrew);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvHeaderCrew = (TextView) findViewById(R.id.tvHeaderCrew);
        tvHeaderCrew.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, mContext));

        rvCrew = (RecyclerView) findViewById(R.id.rvCrew);
        if (rvCrew != null)
            rvCrew.setHasFixedSize(true);
        rvCrew.setItemAnimator(new ScaleInAnimator());

        linearLayoutManager = new LinearLayoutManager(mContext);
        rvCrew.setLayoutManager(linearLayoutManager);
        rvCrew.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        crewModels = new ArrayList<>();
        mAdapter = new CrewAdapter(crewModels);

        List<SimpleSectionedCrewRecyclerViewAdapter.Section> sections = new ArrayList<>();

        sections.add(new SimpleSectionedCrewRecyclerViewAdapter.Section(0, getString(R.string.crew_text_title)));

        SimpleSectionedCrewRecyclerViewAdapter.Section[] dummy = new SimpleSectionedCrewRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedCrewRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedCrewRecyclerViewAdapter(this, R.layout.crew_title, R.id.crew_text, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        rvCrew.setAdapter(mSectionedAdapter);

        rvCrew.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvCrew, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareCrewData();
    }

    private void prepareCrewData() {
        CrewModel model = new CrewModel(getString(R.string.malp), getString(R.string.kurucu));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.devname), getString(R.string.dev));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.isigirci), getString(R.string.gkor));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.yaslantas), getString(R.string.pyon));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.hdogan), getString(R.string.psor));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.ierbay), getString(R.string.puyggel));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.dozdemir), getString(R.string.smedya));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.egur), getString(R.string.smedekipüyesi));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.bdemircioglu), getString(R.string.kuriltan));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.adundar), getString(R.string.orgkor));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.oertan), getString(R.string.ilkor));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.tarpaci), getString(R.string.editor));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.gkarakaya), getString(R.string.icek));
        crewModels.add(model);

        model = new CrewModel(getString(R.string.aonder), getString(R.string.projuyg));
        crewModels.add(model);

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
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
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
    }
}
