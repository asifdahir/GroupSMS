package com.home.groupsms.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.home.groupsms.MainActivity;
import com.home.groupsms.Model.Contact;
import com.home.groupsms.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Administrator on 11/13/2015.
 */
public class SelectedContactsAdapter extends RecyclerView.Adapter<SelectedContactsAdapter.ViewHolder> {
    private ArrayList<Contact> mItems;

    public SelectedContactsAdapter(Hashtable<String, Contact> contacts) {
        mItems = new ArrayList<Contact>(contacts.values());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_contacts_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        String value = String.format("%s\n%s (%s)", mItems.get(position).title, mItems.get(position).phone1, mItems.get(position).phone1Type);
        viewHolder.textView.setText(value);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.HashtableSelectedContacts.remove(mItems.get(position).phone1);
                mItems.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mItems.size());

                ((MainActivity) view.getContext()).updateItemsCount(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public Button button;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            textView = (TextView) itemLayoutView.findViewById(R.id.text);
            button = (Button) itemLayoutView.findViewById(R.id.button);
        }
    }
}
