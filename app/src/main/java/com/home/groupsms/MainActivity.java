package com.home.groupsms;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.home.groupsms.Adapter.ContactsAdapter;
import com.home.groupsms.Adapter.GroupsAdapter;
import com.home.groupsms.Adapter.PagerAdapter;
import com.home.groupsms.Adapter.SelectedContactsAdapter;
import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Group;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static ArrayList<Group> ListGroups;
    public static ArrayList<Contact> ListContacts;
    public static com.home.groupsms.Adapter.GroupsAdapter GroupsAdapter;
    public static com.home.groupsms.Adapter.ContactsAdapter ContactsAdapter;
    public static com.home.groupsms.Adapter.SelectedContactsAdapter SelectedContactsAdapter;
    public static Hashtable<String, Contact> HashtableSelectedContacts;
    public static RecyclerView RecyclerViewGroups;
    public static RecyclerView RecyclerViewContacts;
    public static RecyclerView RecyclerViewSelectedContacts;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private int mSelectedTab;

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_home);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupTabs() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Contacts"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Groups"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Selected"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                mSelectedTab = tab.getPosition();
                switch (mSelectedTab) {
                    case 0:
                        MainActivity.RecyclerViewContacts.setAdapter(MainActivity.ContactsAdapter);
                        break;
                    case 1:
                        MainActivity.RecyclerViewGroups.setAdapter(MainActivity.GroupsAdapter);
                        break;
                    case 2:
                        MainActivity.SelectedContactsAdapter = new SelectedContactsAdapter(MainActivity.HashtableSelectedContacts);
                        MainActivity.RecyclerViewSelectedContacts.setAdapter(MainActivity.SelectedContactsAdapter);
                        break;
                }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupToolbar();
        setupTabs();

        new ContactsLoadOperation().execute();
        new GroupsLoadOperation().execute();
        MainActivity.HashtableSelectedContacts = new Hashtable<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setOnQueryTextListener(this);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                //Toast.makeText(this, "compose", Toast.LENGTH_LONG).show();
                if (MainActivity.HashtableSelectedContacts.size() <= 0) {
                    Toast.makeText(this, "No recipient selected", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ComposeSMSActivity.class);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        switch (mSelectedTab) {
            case 0:
                final List<Contact> filteredListContacts = filterContacts(ListContacts, query);
                ContactsAdapter.animateTo(filteredListContacts);
                RecyclerViewContacts.scrollToPosition(0);
                break;

            case 1:
                final List<Group> filteredListGroups = filterGroups(ListGroups, query);
                GroupsAdapter.animateTo(filteredListGroups);
                RecyclerViewGroups.scrollToPosition(0);
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Contact> filterContacts(List<Contact> models, String query) {
        query = query.toLowerCase();

        final List<Contact> filteredModelList = new ArrayList<>();
        for (Contact model : models) {
            final String text = model.title.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private List<Group> filterGroups(List<Group> models, String query) {
        query = query.toLowerCase();

        final List<Group> filteredModelList = new ArrayList<>();
        for (Group model : models) {
            final String text = model.title.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private class ContactsLoadOperation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity.ListContacts = Contact.getContacts(getApplicationContext());
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

    private class GroupsLoadOperation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity.ListGroups = Group.getGroups(getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            MainActivity.GroupsAdapter = new GroupsAdapter(MainActivity.ListGroups);
            MainActivity.RecyclerViewGroups.setAdapter(MainActivity.GroupsAdapter);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}