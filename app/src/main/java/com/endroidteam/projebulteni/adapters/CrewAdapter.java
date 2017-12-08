package com.endroidteam.projebulteni.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.models.CrewModel;
import com.endroidteam.projebulteni.others.FontCache;

import java.util.List;

/**
 * Created by NuhKoca on 4.05.2016.
 */
public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> {

    private List<CrewModel> crewModels;

    public CrewAdapter(List<CrewModel> crewModels) {
        this.crewModels = crewModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crew_list, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CrewModel crewModel=crewModels.get(position);

        holder.tvCrewName.setText(crewModel.getCrewName());
        holder.tvCrewDuty.setText(crewModel.getCrewDuty());
    }

    @Override
    public int getItemCount() {
        return crewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCrewName, tvCrewDuty;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCrewName= (TextView) itemView.findViewById(R.id.tvCrewName);
            tvCrewDuty= (TextView) itemView.findViewById(R.id.tvCrewDuty);

            tvCrewName.setTypeface(FontCache.get(FontCache.ROBOTO_MEDIUM,itemView.getContext().getApplicationContext()));
            tvCrewDuty.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR,itemView.getContext().getApplicationContext()));
        }
    }
}
