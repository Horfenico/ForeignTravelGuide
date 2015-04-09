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

import java.util.List;

/**
 * Created by Eric on 4/2/2015.
 */
public class ActivityCountry extends ActivityForeignTravelGuide {
    List<String> nameList;

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

        ListView lv = (ListView) findViewById(R.id.list);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, nameList);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                final Intent countryIntent = new Intent(getApplicationContext(), ActivityResult.class);
                countryIntent.putExtra("pos", position);
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
                return true;
            }
            case R.id.action_sortZA: {
                return true;
            }
            case R.id.action_highAdvisry: {
                return true;
            }
            case R.id.action_lowAdvisry: {
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}


