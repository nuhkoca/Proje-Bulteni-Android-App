package com.endroidteam.projebulteni.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.endroidteam.projebulteni.MainActivity;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.activities.ProjectDetailActivity;
import com.endroidteam.projebulteni.models.ProjectListModel;
import com.endroidteam.projebulteni.others.FontCache;


import java.util.List;

/**
 * Created by NuhKoca on 1.05.2016.
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private Context context;
    private List<ProjectListModel> projectListModels;

    public ProjectListAdapter(List<ProjectListModel> projectListModels, Context context) {
        this.projectListModels = projectListModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projects_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ProjectListModel projectListModel = projectListModels.get(position);

        holder.tvProjectID.setText(projectListModel.getId());
        holder.tvProjectTitle.setText(projectListModel.getTitle());
        holder.tvProjectSender.setText(projectListModel.getSender());
        holder.tvProjectDate.setText(projectListModel.getDate());


        holder.rlListProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, ProjectDetailActivity.class);
                detailIntent.putExtra("projectid", holder.tvProjectID.getText());
                context.startActivity(detailIntent);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProjectID, tvProjectTitle, tvProjectSender, tvProjectDate;
        private RelativeLayout rlListProject;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProjectID = (TextView) itemView.findViewById(R.id.tvProjectID);
            tvProjectSender = (TextView) itemView.findViewById(R.id.tvProjectSender);
            tvProjectTitle = (TextView) itemView.findViewById(R.id.tvProjectTitle);
            tvProjectDate = (TextView) itemView.findViewById(R.id.tvProjectDate);
            rlListProject = (RelativeLayout) itemView.findViewById(R.id.rlListProject);

            tvProjectSender.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
            tvProjectTitle.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM, itemView.getContext().getApplicationContext()));
            tvProjectDate.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
        }
    }
}
