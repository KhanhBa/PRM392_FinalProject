package com.example.prm392_fp_soccer_field.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.APIs.YardService;
import com.example.prm392_fp_soccer_field.Adapters.CarouselAdapter;
import com.example.prm392_fp_soccer_field.Adapters.SoccerFieldAdapter;
import com.example.prm392_fp_soccer_field.Adapters.YardListAdapter;
import com.example.prm392_fp_soccer_field.CallBack.OnYardClickListener;
import com.example.prm392_fp_soccer_field.Models.SoccerField;
import com.example.prm392_fp_soccer_field.Models.Yard;
import com.example.prm392_fp_soccer_field.R;
import com.example.prm392_fp_soccer_field.YardDetailActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap myMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private String address = "Lưu Hữu Phước Tân Lập, Đông Hoà, Dĩ An, Bình Dương";
    private List<Yard> yardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setupTopAppBar(view);
        setupCarousel(view);
        setupFieldList(view);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("MapsActivity", "Map Fragment not found");
        }
        searchLocation(address);
        return view;
    }

    private void searchLocation(String locationName) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(locationName, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error retrieving location", Toast.LENGTH_SHORT).show();
        }

        if (addressList != null && !addressList.isEmpty()) {
            Address selectedAddress = addressList.get(0);
            LatLng selectedLocation = new LatLng(selectedAddress.getLatitude(), selectedAddress.getLongitude());
            putRedMarkerAndMoveCamera(selectedLocation, selectedAddress.getAddressLine(0));
        } else {
            Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    if (myMap != null) {
                        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        putRedMarkerAndMoveCamera(currentLatLng, "My Location");
                    }
                }
            }
        });
    }

    private void setupTopAppBar(View view) {
        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            // Handle menu item clicks if needed
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
                        recyclerView.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Yard>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }

    private void putRedMarkerAndMoveCamera(LatLng location, String title) {
        if (myMap != null) {
            myMap.addMarker(new MarkerOptions().position(location).title(title));
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        getLastLocation();
    }

    private void setupYardList() {

    }
}
