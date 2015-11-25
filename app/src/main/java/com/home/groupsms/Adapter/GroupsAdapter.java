package com.home.groupsms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.groupsms.Model.Group;
import com.home.groupsms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/13/2015.
 */
public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private ArrayList<Group> mItems;

    public GroupsAdapter(ArrayList<Group> groups) {
        mItems = new ArrayList<>(groups);
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

    public void animateTo(List<Group> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Group> newModels) {
        for (int i = mItems.size() - 1; i >= 0; i--) {
            final Group model = mItems.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Group> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Group model = newModels.get(i);
            if (!mItems.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Group> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Group model = newModels.get(toPosition);
            final int fromPosition = mItems.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Group removeItem(int position) {
        final Group model = mItems.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Group model) {
        mItems.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Group model = mItems.remove(fromPosition);
        mItems.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textView = (TextView) itemLayoutView.findViewById(R.id.text);
        }
    }
}
