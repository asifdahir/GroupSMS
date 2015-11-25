package com.home.groupsms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.groupsms.Model.Contact;
import com.home.groupsms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/13/2015.
 */
public class SelectedContactsAdapter extends RecyclerView.Adapter<SelectedContactsAdapter.ViewHolder> {
    private ArrayList<Contact> mItems;

    public SelectedContactsAdapter(ArrayList<Contact> contacts) {

        mItems = new ArrayList<>(contacts);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_contacts_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String value = String.format("%s\n%s (%s)",
                mItems.get(position).title, mItems.get(position).phone1, mItems.get(position).phone1Type);
        viewHolder.textView.setText(value);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textView = (TextView) itemLayoutView.findViewById(R.id.text);
        }
    }
}
