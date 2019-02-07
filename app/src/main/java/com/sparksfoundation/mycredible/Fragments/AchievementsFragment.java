package com.sparksfoundation.mycredible.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sparksfoundation.mycredible.R;

public class AchievementsFragment extends Fragment {


    public AchievementsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }

    public void showToast(String msg, int length)
    {
        Toast.makeText(getContext(), msg, length).show();
    }

}
