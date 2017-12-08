package com.endroidteam.projebulteni.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.FontCache;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoDataFragment extends Fragment {

    private TextView tvNoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_no_data, container, false);

        tvNoData = (TextView) rootView.findViewById(R.id.tvNoData);
        tvNoData.setTypeface(FontCache.get(FontCache.ROBOTO_REGULAR, getActivity()));

        return rootView;
    }
}
