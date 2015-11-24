package com.home.groupsms;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Administrator on 11/23/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
        super(fragmentManager);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentContacts tabFragmentContacts = new TabFragmentContacts();
                return tabFragmentContacts;
            case 1:
                TabFragmentGroups tabFragmentGroups = new TabFragmentGroups();
                return tabFragmentGroups;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
