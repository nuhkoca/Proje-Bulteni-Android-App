package com.endroidteam.projebulteni.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.models.SettingsModel;
import com.endroidteam.projebulteni.others.FontCache;

import java.util.List;

/**
 * Created by NuhKoca on 30.04.2016.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private List<SettingsModel> settingsModels;

    public SettingsAdapter(List<SettingsModel> settingsModels) {
        this.settingsModels = settingsModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_list_row, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SettingsModel settingsModel = settingsModels.get(position);
        holder.tvSettingsTitle.setText(settingsModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return settingsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSettingsTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSettingsTitle = (TextView) itemView.findViewById(R.id.tvSettingsTitle);
            tvSettingsTitle.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, itemView.getContext().getApplicationContext()));
        }
    }
}
