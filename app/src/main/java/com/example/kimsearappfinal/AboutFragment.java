package com.example.kimsearappfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    public void onBackButtonClicked(View view) {
        // You can call getActivity() to get the hosting Activity
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}