package com.example.eric.foreigntravelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    List<String[]> list = new ArrayList<>();
    String[] countryName = new String[231];
    String[] countryID = new String[231];
    String[] countryURLslug = new String[231];
    int[] advisoryState = new int[231];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Read in CSV data
        list = readCSV(list);

        //Get Advisory State levels
        for (int i = 2; i < list.size(); i++)
            advisoryState[i] = Integer.parseInt(list.get(i)[8]);

        //Get Country Names
        for (int i = 2; i < list.size(); i++)
            countryName[i] = list.get(i)[6];

        //Get Country IDs
        for (int i = 2; i < list.size(); i++)
            countryID[i] = list.get(i)[5];

        //Get URL slugs
        for (int i = 2; i < list.size(); i++)
            countryURLslug[i] = list.get(i)[17];

        //Set up Buttons
        Button countryButton = (Button) findViewById(R.id.countryButton);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button compareButton = (Button) findViewById(R.id.compareButton);

        //Set up Activity Intents
        final Intent countryIntent = new Intent(this, CountryActivity.class);
        final Intent searchIntent = new Intent(this, SearchActivity.class);
        final Intent compareIntent = new Intent(this, CompareActivity.class);

        //Button on-click response events
        countryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(countryIntent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(searchIntent);
            }
        });
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(compareIntent);
            }
        });


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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        } else if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //A function that reads in the "result.csv" file
    public List<String[]> readCSV(List<String[]> li) {
        String next[];
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("result.csv")));
            for (; ; ) {
                next = reader.readNext();
                if (next != null)
                    li.add(next);
                else
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return li;
    }

    public List<String> getCountryName() {
        List<String> countryList = new ArrayList<>(Arrays.asList(countryName));
        return countryList;
    }
}
