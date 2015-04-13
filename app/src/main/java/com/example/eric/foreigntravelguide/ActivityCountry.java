package com.example.eric.foreigntravelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Eric on 4/2/2015.
 */
public class ActivityCountry extends ActivityForeignTravelGuide {
    private List<String> nameList;
    private String[] namesZA;

    private ArrayAdapter aa;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        //Set Action Bar
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Destinations");

        nameList = getCountryNames();

        lv = (ListView) findViewById(R.id.list);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);

        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                final Intent countryIntent = new Intent(getApplicationContext(), ActivityResult.class);
                countryIntent.putExtra("position", position);
                countryIntent.putExtra("selected", selected);
                countryIntent.putExtra("namesZA", namesZA);
                startActivity(countryIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_country, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_sortAZ: {
                nameList = getCountryNames();
                List nameOption = new LinkedList<String>(nameList);
                aa.clear();
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameOption);
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();
                return true;
            }
            case R.id.action_sortZA: {
                nameList = getCountryNames();
                String[] names = new String[nameList.size()];
                names = nameList.toArray(names);
                namesZA = new String[nameList.size()];
                List<String> namesZAList = new LinkedList<>();
                int c = 0;

                for (int i = names.length - 1; i > -1; i--) {
                    namesZA[c] = names[i];
                    c++;
                }
                if (namesZA.length != 0) {
                    namesZAList = new LinkedList<>(Arrays.asList(namesZA));
                    aa.clear();
                    aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, namesZAList);
                    lv.setAdapter(aa);
                    aa.notifyDataSetChanged();
                }
                return true;
            }
            case R.id.action_highAdvisry: {
                List<String> highAdvList;
                highAdvList = countryAdvisoryHighLow();
                highAdvList = new LinkedList<>(highAdvList);
                aa.clear();
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, highAdvList);
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();
                return true;
            }
            case R.id.action_lowAdvisry: {
                List<String> lowAdvList;
                lowAdvList = countryAdvisoryLowHigh();
                lowAdvList = new LinkedList<>(lowAdvList);
                aa.clear();
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, lowAdvList);
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}


