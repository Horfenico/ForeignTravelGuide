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
public class FragmentTab2 extends Fragment {
    private String[] namesZA;
    private String[] nameList;
    private String[] highAdv;
    private String[] lowAdv;
    private String[] laws;
    private String[] lawsZA;
    private String[] lawsHigh;
    private String[] lawsLow;

    public FragmentTab2() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TypedArray flag = getResources().obtainTypedArray(R.array.laws);
        laws = new String[flag.length()];
        laws = getResources().getStringArray(R.array.laws);
        int pos = 0;
        String selected;
        Bundle bundle = this.getArguments();

        View v = inflater.inflate(R.layout.fragment_tab2frag, container, false);

        pos = bundle.getInt("position", pos);
        selected = bundle.getString("selected");
        namesZA = bundle.getStringArray("namesZA");
        nameList = bundle.getStringArray("nameList");
        highAdv = bundle.getStringArray("highAdv");
        lowAdv = bundle.getStringArray("lowAdv");
        if (namesZA != null && selected.equals(namesZA[pos])) {
            lawsZA = new String[namesZA.length];
            lawsZA();
            displayText(lawsZA, v, pos);
        } else if (selected.equals(highAdv[pos])) {
            lawsHigh = new String[highAdv.length];
            sortHighAdv();
            displayText(lawsHigh, v, pos);
        } else if (selected.equals(lowAdv[pos])) {
            lawsLow = new String[lowAdv.length];
            sortLowAdv();
            displayText(lawsLow, v, pos);
        } else {
            displayText(laws, v, pos);
        }
        return v;
    }

    private View displayText(String[] s, View v, int pos) {
        TextView textView = (TextView) v.findViewById(R.id.laws);
        textView.setText(s[pos]);
        return v;
    }

    private void lawsZA() {
        int c = 0;
        for (int i = laws.length - 1; i > -1; i--) {
            lawsZA[c] = laws[i];
            c++;
        }
    }

    private void sortHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    lawsHigh[i] = laws[j];
            }
        }
    }

    private void sortLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    lawsLow[i] = laws[j];
            }
        }
    }

}
