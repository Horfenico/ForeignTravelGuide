package com.example.eric.foreigntravelguide;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

/**
 * Created by Eric on 4/2/2015.
 */
public class SearchActivity extends MainActivity {
    List<String> nameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        nameList = getCountryName();

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, nameList);
        AutoCompleteTextView countries = (AutoCompleteTextView) findViewById(R.id.search);

        countries.setAdapter(aa);
        countries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {

            }
        });



    }
}
