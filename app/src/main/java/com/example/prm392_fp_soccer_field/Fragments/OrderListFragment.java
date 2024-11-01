package com.example.prm392_fp_soccer_field.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.prm392_fp_soccer_field.R;
import com.google.android.material.appbar.MaterialToolbar;

public class OrderListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_order_activity, container, false);

        setupTopAppBar(view);
        return view;
    }

    private void setupTopAppBar(View view) {
        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            return true;
        });
    }


}
