package com.example.prm392_fp_soccer_field.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.R;
import com.example.prm392_fp_soccer_field.Session.UserSessionManager;
import com.example.prm392_fp_soccer_field.SigninActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    Button btn_sign_out;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_screen, container, false);
        UserSessionManager sessionManager = new UserSessionManager(getContext());
        btn_sign_out = view.findViewById(R.id.btn_sign_out);
        mAuth = FirebaseAuth.getInstance();

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
        btn_sign_out.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            mAuth.signOut();
            Intent intent = new Intent(getActivity(), SigninActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            getActivity().finish();
        });
        return view;

    }



}
