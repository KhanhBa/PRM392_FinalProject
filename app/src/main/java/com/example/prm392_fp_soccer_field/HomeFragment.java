package com.example.prm392_fp_soccer_field;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setupTopAppBar(view);
        setupCarousel(view);
        setupFieldList(view);

        return view;
    }

    private void setupTopAppBar(View view) {
        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            // xử lý sự kiện trên app bar
            return true;
        });
    }

    private void setupCarousel(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.carousel_recycler_view);

        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager();
        recyclerView.setLayoutManager(carouselLayoutManager);

        CarouselSnapHelper snapHelper = new CarouselSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        List<Integer> items = Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e);
        CarouselAdapter adapter = new CarouselAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    private void setupFieldList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.field_list_recycler_view);

        // Danh sách sân bóng
        List<SoccerField> fields = Arrays.asList(
                new SoccerField("Camp Nou", "Barcelona, Tây Ban Nha", "$100/hour", R.drawable.placeholder_field, 4.8f),
                new SoccerField("Wembley Stadium", "London, Anh", "$110/hour", R.drawable.placeholder_field, 4.7f),
                new SoccerField("Santiago Bernabeu", "Madrid, Tây Ban Nha", "$120/hour", R.drawable.placeholder_field, 4.9f),
                new SoccerField("San Siro", "Milan, Italia", "$155/hour", R.drawable.placeholder_field, 4.6f),
                new SoccerField("Signal Iduna Park", "Dortmund, Đức", "$175/hour", R.drawable.placeholder_field, 4.5f)
        );

        SoccerFieldAdapter adapter = new SoccerFieldAdapter(fields, field -> {
            // xử lý sự kiện detail (e.g., open detail view)
        });
        recyclerView.setAdapter(adapter);
    }
}