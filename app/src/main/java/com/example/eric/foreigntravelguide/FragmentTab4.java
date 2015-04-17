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
public class FragmentTab4 extends Fragment {

    private String[] namesZA;
    private String[] nameList;
    private String[] highAdv;
    private String[] lowAdv;
    private String[] security;
    private String[] securityZA;
    private String[] securityHigh;
    private String[] securityLow;

    public FragmentTab4() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TypedArray flag = getResources().obtainTypedArray(R.array.security);
        security = new String[flag.length()];
        security = getResources().getStringArray(R.array.security);
        int pos = 0;
        String selected;
        Bundle bundle = this.getArguments();

        View v = inflater.inflate(R.layout.fragment_tab4, container, false);

        pos = bundle.getInt("position", pos);
        selected = bundle.getString("selected");
        namesZA = bundle.getStringArray("namesZA");
        nameList = bundle.getStringArray("nameList");
        highAdv = bundle.getStringArray("highAdv");
        lowAdv = bundle.getStringArray("lowAdv");
        if (namesZA != null && selected.equals(namesZA[pos])) {
            securityZA = new String[namesZA.length];
            securityZA();
            displayText(securityZA, v, pos);
        } else if (selected.equals(highAdv[pos])) {
            securityHigh = new String[highAdv.length];
            sortHighAdv();
            displayText(securityHigh, v, pos);
        } else if (selected.equals(lowAdv[pos])) {
            securityLow = new String[lowAdv.length];
            sortLowAdv();
            displayText(securityLow, v, pos);
        } else {
            displayText(security, v, pos);
        }
        return v;
    }

    private View displayText(String[] s, View v, int pos) {
        TextView textView = (TextView) v.findViewById(R.id.security);
        textView.setText(s[pos]);
        return v;
    }

    private void securityZA() {
        int c = 0;
        for (int i = security.length - 1; i > -1; i--) {
            securityZA[c] = security[i];
            c++;
        }
    }

    private void sortHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    securityHigh[i] = security[j];
            }
        }
    }

    private void sortLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    securityLow[i] = security[j];
            }
        }
    }

}
