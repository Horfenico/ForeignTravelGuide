package com.example.eric.foreigntravelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eric on 4/2/2015.
 */
public class ActivityCompare extends ActivityForeignTravelGuide {
    private List<String> nameList;
    private String firstCountry = "";
    private String secndCountry = "";
    private int firstCountryPos = 0;
    private int secndCountryPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);


        //Set Action Bar
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Compare");

        nameList = getCountryNames();
        //Set up AutoText1
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, nameList);
        AutoCompleteTextView country1 = (AutoCompleteTextView) findViewById(R.id.compareText1);

        country1.setAdapter(aa);
        //On Click
        country1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                //Assign selected country name and position to be used on button click
                firstCountry = selection;
                firstCountryPos = pos;
            }
        });

        //Set up 2nd AutoText
        ArrayAdapter<String> ab = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, nameList);
        AutoCompleteTextView country2 = (AutoCompleteTextView) findViewById(R.id.compareText2);

        country2.setAdapter(ab);
        //On Click
        country2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                //For button
                secndCountry = selection;
                secndCountryPos = pos;

            }
        });

        //Set up button
        Button compareButton = (Button) findViewById(R.id.compareButton);



        //Button on click
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set up Intent
                final Intent compareIntent = new Intent(getApplicationContext(), ActivityCompareResult1.class );
                compareIntent.putExtra("secondCountry", secndCountry);
                compareIntent.putExtra("secndCountryPos", secndCountryPos);
                compareIntent.putExtra("firstCountryPos", firstCountryPos);
                startActivity(compareIntent);
            }
        });



    }
}
