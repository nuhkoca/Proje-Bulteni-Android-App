package com.endroidteam.projebulteni.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.endroidteam.projebulteni.R;
import com.endroidteam.projebulteni.adapters.DividerItemDecoration;
import com.endroidteam.projebulteni.adapters.ProjectListAdapter;
import com.endroidteam.projebulteni.json.ListConfig;
import com.endroidteam.projebulteni.models.ProjectListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListProjectFragment extends Fragment {

    private List<ProjectListModel> projectListModels;

    private RecyclerView rvList;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private JSONObject json;

    private SharedPreferences.Editor editorProject;

    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_project, container, false);

        rvList = (RecyclerView) rootView.findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        rvList.setItemAnimator(new ScaleInAnimator());

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(mLinearLayoutManager);

        projectListModels = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        mAdapter = new ProjectListAdapter(projectListModels, getActivity());
        rvList.setAdapter(new ScaleInAnimationAdapter(mAdapter));

        rvList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        getData();

        return rootView;
    }

    private JsonArrayRequest getDataFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ListConfig.DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError) {
                    Toast.makeText(getActivity(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
                if (error == null) {
                    Toast.makeText(getActivity(), getString(R.string.volley_error), Toast.LENGTH_LONG).show();
                }
            }
        });

        return jsonArrayRequest;
    }

    private void parseData(JSONArray array) {
        if (array.length() > 0) {
            for (int i = array.length() - 1; i >= 0; i--) {
                ProjectListModel project = new ProjectListModel();
                json = null;

                editorProject = getActivity().getSharedPreferences("com.endroidteam.projebulteni", Context.MODE_PRIVATE).edit();
                editorProject.putBoolean("frag_state", false);
                editorProject.apply();

                try {
                    json = array.getJSONObject(i);

                    project.setId(json.getString(ListConfig.TAG_ID));
                    project.setTitle(json.getString(ListConfig.TAG_TITLE));
                    project.setSender(json.getString(ListConfig.TAG_SENDER));
                    project.setDate(json.getString(ListConfig.TAG_DATE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                projectListModels.add(project);
                mAdapter.notifyDataSetChanged();
            }
        } else {

            editorProject = getActivity().getSharedPreferences("com.endroidteam.projebulteni", Context.MODE_PRIVATE).edit();
            editorProject.putBoolean("frag_state", true);
            editorProject.apply();

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                    .beginTransaction();

            fragmentTransaction.replace(R.id.root_frame, new NoDataFragment())
                    .commit();
        }
    }

    private void getData() {
        requestQueue.add(getDataFromServer());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.refresh);
        menuItem.setVisible(true);
    }
}