package com.home.groupsms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.groupsms.Model.Group;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/13/2015.
 */
public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private ArrayList<Group> mItems;

    public GroupsAdapter(ArrayList<Group> groups) {
        mItems = groups;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.groups_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.textView.setText(mItems.get(position).title);
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
