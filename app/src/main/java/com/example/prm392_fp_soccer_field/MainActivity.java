package com.example.prm392_fp_soccer_field;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        int itemId = item.getItemId();

                        if (itemId == R.id.nav_home) {
                            selectedFragment = new HomeFragment();
                        } else if (itemId == R.id.nav_search) {
                            selectedFragment = new SearchFragment();
                        } else if (itemId == R.id.nav_profile) {
                            selectedFragment = new BookedFieldsFragment();
                        }

                        if (selectedFragment != null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, selectedFragment)
                                    .commit();
                        }
                        return true;
                    }
                });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}