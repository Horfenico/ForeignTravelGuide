package com.example.eric.foreigntravelguide;


import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFood extends Fragment {
    private String[] namesZA;
    private String[] nameList;
    private String[] highAdv;
    private String[] lowAdv;
    private String[] food;
    private String[] foodZA;
    private String[] foodHigh;
    private String[] foodLow;


    public FragmentFood() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TypedArray flag = getResources().obtainTypedArray(R.array.food);
        food = new String[flag.length()];
        food = getResources().getStringArray(R.array.food);
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
        if (namesZA != null && selected.equals(namesZA[pos])) {
            foodZA = new String[namesZA.length];
            foodZA();
            String s = foodZA[pos].replace(".png", "").trim();
            int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
            drawImage(id, v);

        } else if (selected.equals(highAdv[pos])) {
            foodHigh = new String[highAdv.length];
            sortHighAdv();
            String s = foodHigh[pos].replace(".png", "").trim();
            int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
            drawImage(id, v);
        } else if (selected.equals(lowAdv[pos])) {
            foodLow = new String[lowAdv.length];
            sortLowAdv();
            String s = foodLow[pos].replace(".png", "").trim();
            int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
            drawImage(id, v);
        } else {
            String s = food[pos].replace(".png", "").trim();
            int id = getResources().getIdentifier(s, null, getActivity().getPackageName());
            Log.d("id", Integer.toString(id) + " " + s);
            drawImage(id, v);
        }
        return v;
    }

    private View drawImage(int id, View v) {
        Drawable image = getResources().getDrawable(id);
        v.setBackground(image);
        return v;
    }

    private void foodZA() {
        int c = 0;
        for (int i = food.length - 1; i > -1; i--) {
            foodZA[c] = food[i];
            c++;
        }
    }

    private void sortHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    foodHigh[i] = food[j];
            }
        }
    }

    private void sortLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    foodLow[i] = food[j];
            }
        }
    }


}
