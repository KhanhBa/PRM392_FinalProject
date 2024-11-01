package com.example.prm392_fp_soccer_field.Fragments;

import static android.util.Log.e;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.APIs.YardService;
import com.example.prm392_fp_soccer_field.Adapters.YardListAdapter;
import com.example.prm392_fp_soccer_field.CallBack.OnYardClickListener;
import com.example.prm392_fp_soccer_field.Models.Yard;
import com.example.prm392_fp_soccer_field.R;
import com.example.prm392_fp_soccer_field.YardDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YardListFragment extends Fragment {

    private RecyclerView yardListFragment;
    private TextView yard_summary;
    private List<Yard> yardList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_yard, container, false);

        yardListFragment = view.findViewById(R.id.yard_list);
        yard_summary = view.findViewById(R.id.yard_summary);

        setupYardList();

        return view;
    }

    private void setupYardList() {
        YardService apiService = RetrofitClient.getClient().create(YardService.class);


        Call<List<Yard>> call = apiService.getAllYards();
        call.enqueue(new Callback<List<Yard>>() {
            @Override
            public void onResponse(Call<List<Yard>> call, Response<List<Yard>> response) {
                if (response.isSuccessful()) {
                    List<Yard> yards = response.body();
                    if (yards != null) {
                        yardList = yards;
                        YardListAdapter adapter = new YardListAdapter(yardList, new OnYardClickListener() {
                            @Override
                            public void onYardClick(Yard yard) {
                                Intent intent = new Intent(getContext(), YardDetailActivity.class);
                                intent.putExtra("yard", yard.getId());
                                startActivity(intent);
                            }
                        });
                        yardListFragment.setAdapter(adapter);
                        yard_summary.setText("Có "+yards.size()+" sân");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Yard>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }
}