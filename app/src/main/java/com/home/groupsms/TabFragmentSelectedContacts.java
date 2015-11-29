package com.home.groupsms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.groupsms.Adapter.SelectedContactsAdapter;

/**
 * Created by Administrator on 11/23/2015.
 */
public class TabFragmentSelectedContacts extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_selected_contacts, container, false);

        //MainActivity.SelectedContactsAdapter = new SelectedContactsAdapter(MainActivity.ListSelectedContacts);
        MainActivity.SelectedContactsAdapter = new SelectedContactsAdapter(MainActivity.HashtableSelectedContacts);

        MainActivity.RecyclerViewSelectedContacts = (RecyclerView) view.findViewById(R.id.recyclerViewSelectedContact);
        MainActivity.RecyclerViewSelectedContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        MainActivity.RecyclerViewSelectedContacts.setItemAnimator(new DefaultItemAnimator());
        MainActivity.RecyclerViewSelectedContacts.setAdapter(MainActivity.SelectedContactsAdapter);

        return view;
    }
}
