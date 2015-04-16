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
 * Created by Eric on 4/9/2015.
 */
public class ActivityCompareResult1 extends ActivityCompare implements TabHost.OnTabChangeListener {
    private TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ActivityCompareResult1.TabInfo>();
    private TabInfo mLastTab = null;
    private String secndCountryName = "";
    private int secndCountryPos = 0;
    private int position = 0;
    private String selected = "";
    private String[] namList;
    private String[] highAdvs;
    private String[] lowAdvs;
    private String[] namesZA;



    private static void addTab(ActivityCompareResult1 activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compareresult1);

        Bundle flagFrag = new Bundle();
        Bundle foodFrag = new Bundle();
        Bundle tab = new Bundle();

        position = 0;
        selected = "";
        final String[] latitude = getResources().getStringArray(R.array.latitude);
        final String[] longitude = getResources().getStringArray(R.array.longitude);
        String second = "";

        //Get Country Names
        List<String> nameList = new ArrayList<>();
        final List<String> highAdv = countryAdvisoryHighLow();
        final List<String> lowAdv = countryAdvisoryLowHigh();
        nameList = getCountryNames();
        namList = new String[nameList.size()];
        namList = nameList.toArray(namList);
        //Get position of item selected
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            position = intent.getIntExtra("firstCountryPos", position);
            secndCountryPos = intent.getIntExtra("secndCountryPos", secndCountryPos);

            try {
                selected = extras.getString("selected");
                secndCountryName = extras.getString("secondCountry");
            } catch (Exception e) {
                selected = "";
                secndCountryName = "";
            }

        }


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
        FragmentFood food = new FragmentFood();
        FragmentMap map = new FragmentMap();
        flagFrag.putStringArray("nameList", namList);

        highAdvs = new String[highAdv.size()];
        lowAdvs = new String[lowAdv.size()];
        highAdvs = highAdv.toArray(highAdvs);
        lowAdvs = lowAdv.toArray(lowAdvs);

        //set tab args
        tab.putInt("position", position);
        tab.putString("selected", selected);
        tab.putStringArray("namesZA", namesZA);
        tab.putStringArray("highAdv", highAdvs);
        tab.putStringArray("lowAdv", lowAdvs);
        tab.putStringArray("nameList", namList);

        //Initialise tab host
        this.initialiseTabHost(tab);

        if (savedInstanceState != null)
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        //



        //Frag 1 Extras
        flagFrag.putInt("position", position);
        flagFrag.putString("selected", selected);
        flagFrag.putStringArray("nameList", namList);
        flagFrag.putStringArray("namesZA", namesZA);
        flagFrag.putStringArray("highAdv", highAdvs);
        flagFrag.putStringArray("lowAdv", lowAdvs);

        //Frag 2 Extras
        foodFrag.putInt("position", position);
        foodFrag.putString("selected", selected);
        foodFrag.putStringArray("namesZA", namesZA);
        foodFrag.putStringArray("highAdv", highAdvs);
        foodFrag.putStringArray("lowAdv", lowAdvs);
        foodFrag.putStringArray("nameList", namList);


        //Add Fragments to the transaction
        transaction.add(R.id.flagFrag, flag, "Flag");
        transaction.add(R.id.foodFrag, food, "Food");
        transaction.add(R.id.mapFrag, map, "Map");

        //Commit Transaction
        flag.setArguments(flagFrag);
        food.setArguments(foodFrag);
        transaction.commit();

        //Set up Button
        Button mapButton = (Button) findViewById(R.id.mapButton);
        Button cmp = (Button) findViewById(R.id.secndcountry);


        //Map Intent

        //Button On Click
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent mapIntent = new Intent(getApplicationContext(), ActivityMaps.class);
                mapIntent.putExtra("latitude", latitude);
                mapIntent.putExtra("longitude", longitude);
                mapIntent.putExtra("pos", position);
                mapIntent.putExtra("selected", selected);
                mapIntent.putExtra("namesZA", namesZA);
                mapIntent.putExtra("highAdv", highAdvs);
                mapIntent.putExtra("lowAdv", lowAdvs);

                startActivity(mapIntent);
            }
        });
        cmp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pos = secndCountryPos;
                //Set up Intent
                final Intent compareIntent = new Intent(getApplicationContext(), ActivityCompareResult2.class);
                compareIntent.putExtra("position", pos);
                compareIntent.putExtra("selected", secndCountryName);
                startActivity(compareIntent);
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
        ActivityCompareResult1.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Info").setIndicator("Info"), (tabInfo = new TabInfo("Info", FragmentTab1.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ActivityCompareResult1.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tourism").setIndicator("Tourism"), (tabInfo = new TabInfo("Tourism", FragmentTab2.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ActivityCompareResult1.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Climate").setIndicator("Climate"), (tabInfo = new TabInfo("Climate", FragmentTab3.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ActivityCompareResult1.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Security").setIndicator("Security"), (tabInfo = new TabInfo("Security", FragmentTab4.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        this.onTabChanged("Info");
        //
        mTabHost.setOnTabChangedListener(this);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_about);
        MenuItem item2 = menu.findItem(R.id.action_help);
        item.setVisible(false);
        item2.setVisible(false);
        return true;
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
}
