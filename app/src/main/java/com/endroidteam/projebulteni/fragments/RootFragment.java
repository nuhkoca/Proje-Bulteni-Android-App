package com.endroidteam.projebulteni.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.others.CheckNetworkConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class RootFragment extends Fragment {


    private FrameLayout flRootFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_root, container, false);

        flRootFrame = (FrameLayout) rootView.findViewById(R.id.flRootFrame);

        if (!CheckNetworkConnection.CheckConn(getActivity(), flRootFrame)) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                    .beginTransaction();

            fragmentTransaction.replace(R.id.root_frame, new NoConnectionFragment())
                    .commit();
        } else {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                    .beginTransaction();

            fragmentTransaction.replace(R.id.root_frame, new ListProjectFragment())
                    .commit();
        }

        return rootView;
    }
}
