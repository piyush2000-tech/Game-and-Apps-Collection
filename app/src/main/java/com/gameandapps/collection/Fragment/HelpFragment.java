package com.gameandapps.collection.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gameandapps.collection.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    View view;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
