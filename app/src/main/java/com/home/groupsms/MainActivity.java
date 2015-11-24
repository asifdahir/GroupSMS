package com.home.groupsms;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Group;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Group> ListGroups;
    public static ArrayList<Contact> ListContacts;

    private Toolbar mToolbar;
    private Menu mMenu;
    private int mSelectedTab;

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 11/23/2015  will be in asyntask
        ListGroups = Group.getGroups(this);
        ListContacts = Contact.getContacts(this);

        setupToolbar();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("Group"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                mSelectedTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu, menu);
        mMenu = menu;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            MenuItem searchItem = menu.findItem(R.id.search);
            SearchView search = (SearchView) searchItem.getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    filterItems(query);
                    return true;
                }
            });
        }

        return true;
    }

    private void filterItems(String query) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            String[] columns = new String[]{"_id", "text"};
            Object[] temp = new Object[]{0, "default"};

            if (mSelectedTab == 0) {

            } else if (mSelectedTab == 1) {

            } else {

            }

            MatrixCursor cursor = new MatrixCursor(columns);
            for (int i = 0; i < ListGroups.size(); i++) {

                temp[0] = i;
                temp[1] = ListGroups.get(i); //replaced s with i as s not used anywhere.

                cursor.addRow(temp);
            }

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView search = (SearchView) mMenu.findItem(R.id.search).getActionView();
            search.setSuggestionsAdapter(new SearchViewGroupAdapter(this, cursor, ListGroups));
        }
    }
}