package com.home.groupsms;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Group;

/**
 * Created by Administrator on 11/23/2015.
 */
public class TabFragmentGroups extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_groups, container, false);

        MainActivity.RecyclerViewGroups = (RecyclerView) view.findViewById(R.id.recyclerViewGroup);
        MainActivity.RecyclerViewGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        MainActivity.RecyclerViewGroups.setItemAnimator(new DefaultItemAnimator());

        new DataLoadOperation().execute();

        return view;
    }

    private class DataLoadOperation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity.ListGroups = Group.getGroups(getContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            MainActivity.GroupsAdapter = new GroupsAdapter(MainActivity.ListGroups);
            MainActivity.RecyclerViewGroups.setAdapter(MainActivity.GroupsAdapter);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
