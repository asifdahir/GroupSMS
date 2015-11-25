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

        new DataLoadOperation().execute();

        return view;
    }

    private class DataLoadOperation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity.ListContacts = Contact.getContacts(getContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            MainActivity.ContactsAdapter = new ContactsAdapter(MainActivity.ListContacts);
            MainActivity.RecyclerViewContacts.setAdapter(MainActivity.ContactsAdapter);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
