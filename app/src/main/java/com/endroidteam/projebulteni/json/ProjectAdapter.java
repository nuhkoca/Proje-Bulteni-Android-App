package com.endroidteam.projebulteni.json;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.activities.ProjectDetailActivity;
import com.endroidteam.projebulteni.others.FontCache;

import java.util.List;

/**
 * Created by NuhKoca on 24.04.2016.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    List<ProjectModel> projectModels;

    public ProjectAdapter(List<ProjectModel> projectModels, Context context) {
        super();

        this.projectModels = projectModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProjectAdapter.ViewHolder holder, int position) {
        final ProjectModel projectModel = projectModels.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(projectModel.getImage(), ImageLoader.getImageListener(holder.nivTopImage, R.drawable.placeholder, R.drawable.placeholder));

        holder.nivTopImage.setImageUrl(projectModel.getImage(), imageLoader);
        holder.tvTitle.setText(projectModel.getTitle());
        holder.tvSender.setText(projectModel.getSender());
        holder.tvContent.setText(projectModel.getContent());
        holder.tvLocation.setText(projectModel.getLoc());
        holder.tvDate.setText(projectModel.getDate());
        holder.tvType.setText(projectModel.getType());
        holder.tvAppType.setText(projectModel.getApp_type());
        holder.tvAppAddress.setText(projectModel.getApp_address());
        holder.tvRefLink.setText(projectModel.getReflink());

        holder.ivCollExp.setTag("up");

        holder.ivCollExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = holder.ivCollExp.getTag().toString();
                if (tag == "up") {
                    holder.tvContent.setVisibility(View.GONE);
                    holder.ivCollExp.setTag("down");
                    holder.ivCollExp.setImageResource(R.drawable.expand);
                }
                if (tag == "down") {
                    holder.tvContent.setVisibility(View.VISIBLE);
                    holder.ivCollExp.setTag("up");
                    holder.ivCollExp.setImageResource(R.drawable.collapse);
                }
            }
        });

        holder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProjectDetailActivity) context).CreateBottomSheet(projectModel.getReflink());
            }
        });

        holder.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.nivTopImage.setVisibility(View.VISIBLE);
                if (projectModel.getApp_type().contains("N")) {
                    ((ProjectDetailActivity) context).ShowApplicationDialog(projectModel.getApp_address(), context);
                }
                if (projectModel.getApp_type().contains("L")) {
                    //GotoURLs.setURLtoChrome(context, projectModel.getApp_address());

                    String url = projectModel.getApp_address();
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(url));
                    context.startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private NetworkImageView nivTopImage;
        private TextView tvTitle, tvSender, tvShare, tvApply, tvContent, tvLocation, tvDate, tvType, tvAppType, tvAppAddress, tvRefLink;
        private ImageView ivCollExp;

        public ViewHolder(View itemView) {
            super(itemView);

            nivTopImage = (NetworkImageView) itemView.findViewById(R.id.nivTopImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSender = (TextView) itemView.findViewById(R.id.tvSender);
            tvApply = (TextView) itemView.findViewById(R.id.tvApply);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvShare = (TextView) itemView.findViewById(R.id.tvShare);
            tvApply = (TextView) itemView.findViewById(R.id.tvApply);
            tvAppType = (TextView) itemView.findViewById(R.id.tvAppType);
            tvAppAddress = (TextView) itemView.findViewById(R.id.tvAppAddress);
            tvRefLink = (TextView) itemView.findViewById(R.id.tvRefLink);
            ivCollExp = (ImageView) itemView.findViewById(R.id.ivCollExp);

            tvShare.setTypeface(FontCache.get(FontCache.ROBOTO_BLACK, itemView.getContext().getApplicationContext()));
            tvApply.setTypeface(FontCache.get(FontCache.ROBOTO_BLACK, itemView.getContext().getApplicationContext()));
            tvContent.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
            tvTitle.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
            tvSender.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
            tvLocation.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
            tvDate.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
            tvType.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
        }
    }
}
