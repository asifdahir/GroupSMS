package com.home.groupsms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Recipient;
import com.home.groupsms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/13/2015.
 */
public class SMSStatusAdapter extends RecyclerView.Adapter<SMSStatusAdapter.ViewHolder> {
    private ArrayList<Recipient> mItems;

    public SMSStatusAdapter(ArrayList<Recipient> recipients) {
        mItems = new ArrayList<>(recipients);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sms_status, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String value = String.format("%s\n%s (%s)",
                mItems.get(position).contact.title, mItems.get(position).contact.phone1, mItems.get(position).contact.phone1Type);
        viewHolder.textView.setText(value);
        viewHolder.recipient = mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void animateTo(List<Recipient> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Recipient> newModels) {
        for (int i = mItems.size() - 1; i >= 0; i--) {
            final Recipient model = mItems.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Recipient> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Recipient model = newModels.get(i);
            if (!mItems.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Recipient> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Recipient model = newModels.get(toPosition);
            final int fromPosition = mItems.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Recipient removeItem(int position) {
        final Recipient model = mItems.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Recipient model) {
        mItems.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Recipient model = mItems.remove(fromPosition);
        mItems.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Recipient recipient;
        public TextView textView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textView = (TextView) itemLayoutView.findViewById(R.id.text);
            textView.setTag(this);
        }
    }
}
