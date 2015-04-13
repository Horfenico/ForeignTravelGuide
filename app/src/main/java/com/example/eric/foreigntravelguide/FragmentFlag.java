package com.example.eric.foreigntravelguide;


import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentFlag extends Fragment {
    private String[] namesZA;
    private String[] nameList;
    private String[] highAdv;
    private String[] lowAdv;
    private String[] flags;
    private String[] flagsZA;
    private String[] flagsHigh;
    private String[] flagsLow;


    public FragmentFlag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TypedArray flag = getResources().obtainTypedArray(R.array.flags);
        flags = new String[flag.length()];
        flags = getResources().getStringArray(R.array.flags);
        int pos = 0;
        String selected;
        Bundle bundle = this.getArguments();

        View v = inflater.inflate(R.layout.fragment_flag, container, false);

        pos = bundle.getInt("position", pos);
        selected = bundle.getString("selected");
        namesZA = bundle.getStringArray("namesZA");
        nameList = bundle.getStringArray("nameList");
        highAdv = bundle.getStringArray("highAdv");
        lowAdv = bundle.getStringArray("lowAdv");
        Log.d("selected", selected);
        if (namesZA != null) {
            if (selected.equals(namesZA[pos])) {
                flagsZA = new String[namesZA.length];
                flagsZA();
                String s = flagsZA[pos].replace(".png", "").trim();
                int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
                drawImage(id,v);
            }
        }
       else if (selected.equals(highAdv[pos])) {
            flagsHigh = new String[highAdv.length];
            sortHighAdv();
            String s = flagsHigh[pos].replace(".png", "").trim();
            int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
            drawImage(id, v);
        }
      else if (selected.equals(lowAdv[pos])) {
            flagsLow = new String[lowAdv.length];
            sortLowAdv();
            String s = flagsLow[pos].replace(".png", "").trim();
            int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
            drawImage(id, v);
        } else {
            String s = flags[pos].replace(".png", "").trim();
            int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
            Log.d("id", Integer.toString(id) + " " + s);
            drawImage(id, v);
        }
        return v;
    }

    private View drawImage(int id, View v) {
        ImageView imageview = (ImageView) v.findViewById(R.id.flagpic);
        Drawable image = getResources().getDrawable(id);
        imageview.setImageDrawable(image);
        return v;
    }

    private void flagsZA() {
        int c = 0;
        for (int i = flags.length - 1; i > -1; i--) {
            flagsZA[c] = flags[i];
            c++;
        }
    }

    private void sortHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    flagsHigh[i] = flags[j];
            }
        }
    }

    private void sortLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    flagsLow[i] = flags[j];
            }
        }
    }


}
