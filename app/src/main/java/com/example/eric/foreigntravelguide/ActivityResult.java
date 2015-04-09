package com.example.eric.foreigntravelguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Created by Eric on 4/8/2015.
 */
public class ActivityResult extends ActivityForeignTravelGuide implements TabHost.OnTabChangeListener {
    private TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ActivityResult.TabInfo>();
    private TabInfo mLastTab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int position = 0;

        //Initialise tab host
        this.initialiseTabHost(savedInstanceState);

        if (savedInstanceState != null)
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        //

        //Get Country Names
        List<String> nameList = new ArrayList<>();
        nameList = getCountryNames();
        //Get position of item selected
        Intent intent = getIntent();
        if (null != intent)
            position = intent.getIntExtra("pos", position);

        //Set action bar title
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(nameList.get(position));

        //Get reference to activity fragment manager
        FragmentManager manager = getSupportFragmentManager();

        //Reference fragment transaction
        FragmentTransaction transaction = manager.beginTransaction();

        //Create frag objects
        FragmentFlag flag = new FragmentFlag();
        FragmentLand land = new FragmentLand();
        FragmentMap map = new FragmentMap();

        //Add Fragments to the transaction
        transaction.add(R.id.flagFrag, flag, "Flag");
        transaction.add(R.id.landFrag, land, "Land");
        transaction.add(R.id.mapFrag, map, "Map");

        //Commit Transaction
        transaction.commit();

        //Set up Button
        Button mapButton = (Button) findViewById(R.id.mapButton);

        //Map Intent
        final Intent mapIntent = new Intent(getApplicationContext(), ActivityMaps.class);

        //Button On Click
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mapIntent);
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); // save selected tab
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        ActivityResult.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Info").setIndicator("Info"), (tabInfo = new TabInfo("Info", FragmentTab1.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ActivityResult.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tourism").setIndicator("Tourism"), (tabInfo = new TabInfo("Tourism", FragmentTab2.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ActivityResult.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Climate").setIndicator("Climate"), (tabInfo = new TabInfo("Climate", FragmentTab3.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ActivityResult.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Security").setIndicator("Security"), (tabInfo = new TabInfo("Security", FragmentTab3.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        this.onTabChanged("Info");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    private static void addTab(ActivityResult activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabSpec.getTag();

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }

        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        TabInfo newTab = this.mapTabInfo.get(tag);
        if (mLastTab != newTab) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(this,
                            newTab.clss.getName(), newTab.args);
                    ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }

            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }
    }

    //Maintains info of a tabs construct
    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> classy, Bundle args) {
            this.tag = tag;
            this.clss = classy;
            this.args = args;
        }
    }

    //Return dummy view to TabHost
    class TabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }





    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_about);
        MenuItem item2= menu.findItem(R.id.action_help);
        item.setVisible(false);
        item2.setVisible(false);
        return true;
    }



}
