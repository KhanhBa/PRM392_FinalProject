package com.example.prm392_fp_soccer_field;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.prm392_fp_soccer_field.Fragments.BookedFieldsFragment;
import com.example.prm392_fp_soccer_field.Fragments.HomeFragment;
import com.example.prm392_fp_soccer_field.Fragments.ProfileFragment;
import com.example.prm392_fp_soccer_field.Fragments.ServiceFragment;
import com.example.prm392_fp_soccer_field.Fragments.YardListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                            selectedFragment = new YardListFragment();
                        } else if (itemId == R.id.nav_ordered) {
                            selectedFragment = new BookedFieldsFragment();
                        }
                        else if(itemId == R.id.nav_profile){
                            selectedFragment = new ProfileFragment();
                        }else if(itemId == R.id.nav_services){
                            selectedFragment = new ServiceFragment();
                        }
                        if (selectedFragment != null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, selectedFragment)
                                    .commit();
                        }
                        return true;
                    }
                });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}