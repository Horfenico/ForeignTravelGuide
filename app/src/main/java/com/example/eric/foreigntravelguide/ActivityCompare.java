package com.example.eric.foreigntravelguide;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eric on 4/2/2015.
 */
public class ActivityCompare extends ActivityForeignTravelGuide {
    List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        //Set Action Bar
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Compare");


    }
}
