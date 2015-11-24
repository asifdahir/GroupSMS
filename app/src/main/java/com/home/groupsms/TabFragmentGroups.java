package com.home.groupsms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

/**
 * Created by Administrator on 11/23/2015.
 */
public class TabFragmentGroups extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_groups, container, false);

        MainActivity.GroupsAdapter = new GroupsAdapter(MainActivity.ListGroups);

        MainActivity.RecyclerViewGroups = (RecyclerView) view.findViewById(R.id.recyclerViewGroup);
        MainActivity.RecyclerViewGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        MainActivity.RecyclerViewGroups.setAdapter(MainActivity.GroupsAdapter);
        MainActivity.RecyclerViewGroups.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}
