package com.example.prm392_fp_soccer_field.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_fp_soccer_field.APIs.OrderService;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.APIs.ServiceService;
import com.example.prm392_fp_soccer_field.Adapters.ServiceAdapter;
import com.example.prm392_fp_soccer_field.Models.Order;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceFragment extends Fragment {
    private List<Service> services;
    private ServiceAdapter adapter;
    private RecyclerView rc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        rc = view.findViewById(R.id.service_list);
        setupServices();
        setupTopAppBar(view);
        return view;
    }

    private void setupTopAppBar(View view) {
        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            return true;
        });
    }

    private void setupServices() {
        ServiceService apiService = RetrofitClient.getClient().create(ServiceService.class);
        Call<List<Service>> call = apiService.getAllServices();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful()) {
                    services = response.body();
                    if (services != null) {
                        adapter = new ServiceAdapter(services);
                        rc.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }


}
