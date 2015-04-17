package com.example.eric.foreigntravelguide;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTab3 extends Fragment {

    private String[] namesZA;
    private String[] nameList;
    private String[] highAdv;
    private String[] lowAdv;
    private String[] climate;
    private String[] climateZA;
    private String[] climateHigh;
    private String[] climateLow;

    public FragmentTab3() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TypedArray flag = getResources().obtainTypedArray(R.array.climate);
        climate = new String[flag.length()];
        climate = getResources().getStringArray(R.array.climate);
        int pos = 0;
        String selected;
        Bundle bundle = this.getArguments();

        View v = inflater.inflate(R.layout.fragment_tab3frag, container, false);

        pos = bundle.getInt("position", pos);
        selected = bundle.getString("selected");
        namesZA = bundle.getStringArray("namesZA");
        nameList = bundle.getStringArray("nameList");
        highAdv = bundle.getStringArray("highAdv");
        lowAdv = bundle.getStringArray("lowAdv");
        if (namesZA != null) {
            if (selected.equals(namesZA[pos])) {
                climateZA = new String[namesZA.length];
                climateZA();
                displayText(climateZA, v, pos);
            }
        } else if (selected.equals(highAdv[pos])) {
            climateHigh = new String[highAdv.length];
            sortHighAdv();
            displayText(climateHigh, v, pos);
        } else if (selected.equals(lowAdv[pos])) {
            climateLow = new String[lowAdv.length];
            sortLowAdv();
            displayText(climateLow, v, pos);
        } else {
            displayText(climate, v, pos);
        }
        return v;
    }

    private View displayText(String[] s, View v, int pos) {
        TextView textView = (TextView) v.findViewById(R.id.climate);
        textView.setText(s[pos]);
        return v;
    }

    private void climateZA() {
        int c = 0;
        for (int i = climate.length - 1; i > -1; i--) {
            climateZA[c] = climate[i];
            c++;
        }
    }

    private void sortHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    climateHigh[i] = climate[j];
            }
        }
    }

    private void sortLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    climateLow[i] = climate[j];
            }
        }
    }


}
