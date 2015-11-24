package com.home.groupsms;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.groupsms.Model.Group;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/23/2015.
 */
public class SearchViewGroupAdapter extends CursorAdapter {

    private ArrayList<Group> mList;
    private TextView text;

    public SearchViewGroupAdapter(Context context, Cursor cursor, ArrayList<Group> mList) {
        super(context, cursor, false);
        this.mList = mList;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(mList.get(cursor.getPosition()).title);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.searchview_item, parent, false);
        text = (TextView) view.findViewById(R.id.item);

        return view;
    }

    @Override
    public Object getItem(int position) {
        if (position < mList.size()) {
            return mList.get(position).title;
        } else {
            return null;
        }
    }
}
