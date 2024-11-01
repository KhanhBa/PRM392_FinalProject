package com.example.prm392_fp_soccer_field.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.R;
import com.example.prm392_fp_soccer_field.Session.UserSessionManager;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_screen, container, false);
        UserSessionManager sessionManager = new UserSessionManager(getContext());

        if (sessionManager.isLoggedIn()) {
            Customer loggedInUser = sessionManager.getUser();

            TextView txt_full_name = view.findViewById(R.id.txt_full_name);
            TextView txt_email = view.findViewById(R.id.txt_email);
            TextView txt_phone_number = view.findViewById(R.id.txt_phone_number);
            TextView txt_status = view.findViewById(R.id.txt_status);

            txt_full_name.setText(loggedInUser.getFirstName()+ " "+loggedInUser.getLastName());
            txt_email.setText(loggedInUser.getEmail());
            txt_phone_number.setText(loggedInUser.getPhoneNumber());
            String status = "Active";
            if(loggedInUser.isStatus()==false) status="Inactive";
            txt_status.setText(status);
        }
        return view;
    }



}
