package com.example.eric.foreigntravelguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Eric on 4/8/2015.
 */
public class ActivityResult extends ActivityForeignTravelGuide implements TabHost.OnTabChangeListener {
    private TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ActivityResult.TabInfo>();
    private TabInfo mLastTab = null;
    private int position = 0;
    private String selected = "";
    private String[] namesZA;
    private String[] namList;
    private String[] highAdvs;
    private String[] lowAdvs;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle flagFrag = new Bundle();


        position = 0;
        selected = "";
        final String[] latitude = getResources().getStringArray(R.array.latitude);
        final String[] longitude = getResources().getStringArray(R.array.longitude);


        //Initialise tab host
        this.initialiseTabHost(savedInstanceState);

        if (savedInstanceState != null)
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        //

        //Get Country Names
        List<String> nameList = new ArrayList<>();
        final List<String> highAdv = countryAdvisoryHighLow();
        final List<String> lowAdv = countryAdvisoryLowHigh();
        List<String> NamesZAList = new LinkedList<>();
        nameList = getCountryNames();
        namesZA = new String[nameList.size()];
        namList = new String[nameList.size()];
        namList = nameList.toArray(namList);
        //Get position of item selected
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (intent != null) {
            position = intent.getIntExtra("position", position);
            try {
                selected = extras.getString("selected");
            } catch (Exception e) {
                selected = "";
            }
            try {
                namesZA = extras.getStringArray("namesZA");
            } catch (Exception e) {
                namesZA = null;
            }

        }
        if (namesZA != null)
            NamesZAList = new LinkedList<>(Arrays.asList(namesZA));

        highAdvs = new String[highAdv.size()];
        lowAdvs = new String[lowAdv.size()];
        highAdvs = highAdv.toArray(highAdvs);
        lowAdvs = lowAdv.toArray(lowAdvs);

        //Frag 1 Extras
        flagFrag.putInt("position", position);
        flagFrag.putString("selected", selected);
        flagFrag.putStringArray("namesZA", namesZA);
        flagFrag.putStringArray("highAdv", highAdvs);
        flagFrag.putStringArray("lowAdv", lowAdvs);
        flagFrag.putStringArray("nameList", namList);


        //Set action bar title
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        TextView title = (TextView) findViewById(R.id.title);
        if (selected == null)
            title.setText(nameList.get(position));
        else if (selected.equals(nameList.get(position)))
            title.setText(nameList.get(position));
        else if (!NamesZAList.isEmpty()) {
            if (selected.equals(NamesZAList.get(position)))
                title.setText(NamesZAList.get(position));
        } else if (selected.equals(highAdv.get(position)))
            title.setText(highAdv.get(position));
        else if (selected.equals(lowAdv.get(position)))
            title.setText(lowAdv.get(position));


        //Get reference to activity fragment manager
        FragmentManager manager = getSupportFragmentManager();

        //Reference fragment transaction
        FragmentTransaction transaction = manager.beginTransaction();

        //Create frag objects
        FragmentFlag flag = new FragmentFlag();
        flag.setArguments(flagFrag);
        FragmentFood food = new FragmentFood();
        FragmentMap map = new FragmentMap();

        //Add Fragments to the transaction
        transaction.add(R.id.flagFrag, flag, "Flag");
        transaction.add(R.id.foodFrag, food, "Food");
        transaction.add(R.id.mapFrag, map, "Map");

        //Commit Transaction
        transaction.commit();

        //Set up Button
        Button mapButton = (Button) findViewById(R.id.mapButton);


        //Button On Click
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Map Intent

                final Intent mapIntent = new Intent(getApplicationContext(), ActivityMaps.class);
                mapIntent.putExtra("latitude", latitude);
                mapIntent.putExtra("longitude", longitude);
                mapIntent.putExtra("pos", position);
                mapIntent.putExtra("selected", selected);
                mapIntent.putExtra("namesZA", namesZA);
                mapIntent.putExtra("highAdv", highAdvs);
                mapIntent.putExtra("lowAdv", lowAdvs);
                mapIntent.putExtra("nameList", namList);
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
        ActivityResult.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Security").setIndicator("Security"), (tabInfo = new TabInfo("Security", FragmentTab4.class, args)));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
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


}
