package com.home.groupsms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.groupsms.Adapter.GroupsAdapter;
import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Group;

import java.util.ArrayList;

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

        MainActivity.RecyclerViewGroups.addOnItemTouchListener(
                new RecyclerViewItemClickListener(getContext(), new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        GroupsAdapter.ViewHolder viewHolder = null;
                        TextView textView = null;

                        textView = (TextView) view.findViewById(R.id.text);
                        viewHolder = (GroupsAdapter.ViewHolder) textView.getTag();
                        Group g = viewHolder.group;
                        ArrayList<Contact> contacts = Contact.getContacts(getContext(), g.id);
                        for (Contact c : contacts) {
                            if (!MainActivity.HashtableSelectedContacts.containsKey(c.phone1)) {
                                Contact contact = new Contact(c.id, c.title, c.phone1, c.phone1Type);
                                MainActivity.HashtableSelectedContacts.put(c.phone1, contact);
                                //MainActivity.SelectedContactsAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                })
        );

        return view;
    }
}
