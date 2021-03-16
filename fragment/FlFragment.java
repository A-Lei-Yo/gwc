package com.bawei.dingdan.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.dingdan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlFragment extends Fragment {


    public FlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fl, container, false);
    }

}
