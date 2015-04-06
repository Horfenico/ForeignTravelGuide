package com.example.eric.foreigntravelguide;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Eric on 4/2/2015.
 */
public class CountryActivity extends MainActivity {
    List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        nameList = getCountryName();

        String[] countries = new String[nameList.size()];
        countries = nameList.toArray(countries);
        ListView lv = (ListView) findViewById(R.id.list);
        for(int i = 0; i < nameList.size();i++)
            Toast.makeText(this,countries[i],Toast.LENGTH_SHORT).show();
       // ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, countries);
        //lv.setAdapter(aa);

        //lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          //  public void onItemClick(AdapterView parent, View view, int position, long id) {

            //}
        //});


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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sortAZ) {
            return true;
        } else if (id == R.id.action_sortZA) {
            return true;
        } else if (id == R.id.action_highAdvisry) {
            return true;
        } else if (id == R.id.action_lowAdvisry) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
