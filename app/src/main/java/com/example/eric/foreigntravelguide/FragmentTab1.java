package com.example.eric.foreigntravelguide;


import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTab1 extends Fragment {
    private String[] namesZA;
    private String[] nameList;
    private String[] highAdv;
    private String[] lowAdv;
    private String[] advisory;
    private String[] advisoryZA;
    private String[] advisoryHigh;
    private String[] advisoryLow;

    public FragmentTab1(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TypedArray flag = getResources().obtainTypedArray(R.array.Adviseries);
        advisory = new String[flag.length()];
        advisory = getResources().getStringArray(R.array.Adviseries);
        int pos = 0;
        String selected;
        Bundle bundle = this.getArguments();

        View v = inflater.inflate(R.layout.fragment_tab1frag, container, false);

        pos = bundle.getInt("position", pos);
        selected = bundle.getString("selected");
        namesZA = bundle.getStringArray("namesZA");
        nameList = bundle.getStringArray("nameList");
        highAdv = bundle.getStringArray("highAdv");
        lowAdv = bundle.getStringArray("lowAdv");
        if (namesZA != null) {
            if (selected.equals(namesZA[pos])) {
                advisoryZA = new String[namesZA.length];
                advisoryZA();
                displayText(advisoryZA, v,pos);
            }
        } else if (selected.equals(highAdv[pos])) {
            advisoryHigh = new String[highAdv.length];
            sortHighAdv();
            displayText(advisoryHigh, v, pos);
        } else if (selected.equals(lowAdv[pos])) {
            advisoryLow = new String[lowAdv.length];
            sortLowAdv();
            displayText(advisoryLow, v, pos);
        } else {
            displayText(nameList, v, pos);
        }
        Log.d("Made it", "Made it.");
        Toast.makeText(getActivity().getApplicationContext(),"Here.",Toast.LENGTH_SHORT).show();
        return v;
    }
    private View displayText(String[] s, View v, int pos) {
        TextView textView = (TextView) v.findViewById(R.id.adviseries);
        textView.setText(s[pos]);
        return v;
    }

    private void advisoryZA() {
        int c = 0;
        for (int i = advisory.length - 1; i > -1; i--) {
            advisoryZA[c] = advisory[i];
            c++;
        }
    }

    private void sortHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    advisoryHigh[i] = advisory[j];
            }
        }
    }

    private void sortLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    advisoryLow[i] = advisory[j];
            }
        }
    }
}
