package com.home.groupsms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 11/23/2015.
 */
public class TabFragmentGroups extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_groups, container, false);

        RecyclerView recyclerView = null;
        GroupsAdapter adapter = null;

        adapter = new GroupsAdapter(MainActivity.ListGroups);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}
