package com.example.eric.foreigntravelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eric on 4/2/2015.
 */
public class ActivitySearch extends ActivityForeignTravelGuide {
    List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Set Action Bar
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Search");

        nameList = getCountryNames();

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, nameList);
        AutoCompleteTextView countries = (AutoCompleteTextView) findViewById(R.id.search);

        countries.setAdapter(aa);
        countries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                final Intent countryIntent = new Intent(getApplicationContext(), ActivityResult.class);
                countryIntent.putExtra("pos", pos);
                startActivity(countryIntent);
            }
        });


    }
}
