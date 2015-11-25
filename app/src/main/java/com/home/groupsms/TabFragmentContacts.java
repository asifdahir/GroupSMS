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
import android.widget.TextView;
import android.widget.Toast;

import com.home.groupsms.Adapter.ContactsAdapter;
import com.home.groupsms.Model.Contact;

/**
 * Created by Administrator on 11/23/2015.
 */
public class TabFragmentContacts extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_contacts, container, false);

        MainActivity.RecyclerViewContacts = (RecyclerView) view.findViewById(R.id.recyclerViewContact);
        MainActivity.RecyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        MainActivity.RecyclerViewContacts.setItemAnimator(new DefaultItemAnimator());

        MainActivity.RecyclerViewContacts.addOnItemTouchListener(
                new RecyclerViewItemClickListener(getContext(), new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView textView = (TextView) view.findViewById(R.id.text);
                        Contact contact = new Contact("", textView.getText().toString(), "", "");
                        MainActivity.ListSelectedContacts.add(contact);
                        //MainActivity.SelectedContactsAdapter.notifyDataSetChanged();
                    }
                })
        );

        return view;
    }
}
